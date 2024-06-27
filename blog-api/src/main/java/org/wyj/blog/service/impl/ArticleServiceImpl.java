package org.wyj.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wyj.blog.entity.dos.*;
import org.wyj.blog.entity.dto.ArticleArchiveDTO;
import org.wyj.blog.entity.dto.ArticleTagVO;
import org.wyj.blog.entity.param.PageParam;
import org.wyj.blog.entity.param.PublishArticleParam;
import org.wyj.blog.entity.vo.*;
import org.wyj.blog.mapper.*;
import org.wyj.blog.service.IArticleService;
import org.wyj.blog.service.ThreadService;
import org.wyj.blog.utils.DateUtil;
import org.wyj.blog.utils.UserThreadLocalUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ThreadService threadService;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public PageVO<ArticleVO> selectPage(PageParam pageParam) {
        Page<Article> page = new Page<>(pageParam.getPageNo(), pageParam.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getWeight, Article::getId);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();
        long total = articlePage.getTotal();
        int allPageNum = (int) (total / pageParam.getPageSize() + 1);

        ArrayList<ArticleVO> articleVOList = new ArrayList<>();
        for (Article article : records) {
            articleVOList.add(convertArticleDoToVo(article));
        }
        return new PageVO<>(allPageNum, articleVOList);
    }

    @Override
    public List<HotArticleVO> hotArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId, Article::getTitle, Article::getViewCounts);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);

        List<HotArticleVO> hotArticleVOList = new ArrayList<>();
        for (Article article : articles) {
            HotArticleVO hotArticleVO = new HotArticleVO();
            hotArticleVOList.add(hotArticleVO);
            BeanUtils.copyProperties(article, hotArticleVO);
        }
        return hotArticleVOList;
    }

    @Override
    public List<LatestArticleVO> newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId, Article::getTitle, Article::getCreateDate);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);

        List<LatestArticleVO> newArticleVOList = new ArrayList<>();
        for (Article article : articles) {
            LatestArticleVO newArticleVO = new LatestArticleVO();
            newArticleVOList.add(newArticleVO);
            BeanUtils.copyProperties(article, newArticleVO);
            newArticleVO.setCreateTime(DateUtil.convertTimestampToStr(article.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
        }
        return newArticleVOList;
    }

    @Override
    public List<ArticleArchiveDTO> listArchives() {
        return articleMapper.listArchives();
    }

    @Override
    public ArticleContentVO getArticleContent(Long articleId) {
        Article article = articleMapper.selectById(articleId);
        if (article == null) {
            return new ArticleContentVO();
        }
        threadService.updateViewCounts(articleMapper, article);
        return generateArticleContentVO(article);
    }

    @Override
    public long publish(PublishArticleParam publishParam) {
        SysUser sysUser = UserThreadLocalUtil.get();

        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setWeight(Article.ARTICLE_COMMON);
        article.setViewCounts(0);
        article.setTitle(publishParam.getTitle());
        article.setSummary(publishParam.getSummary());
        article.setCommentCounts(0);
        article.setCategoryId(article.getCategoryId());
        article.setCreateDate(new Date().getTime());
        article.setCategoryId(publishParam.getCategoryId());
        articleMapper.insert(article);

        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(publishParam.getContent());
        articleBody.setContentHtml(publishParam.getContentHtml());
        articleBodyMapper.insert(articleBody);

        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);

        List<String> tagNameList = publishParam.getTagNameList();
        List<Tag> tagList = tagMapper.selectList(new LambdaQueryWrapper<>());

        // 允许用户自定义标签
        for (String tagName : tagNameList) {
            boolean isContain = false;
            for (Tag tag : tagList) {
                if (tag.getTagName().equalsIgnoreCase(tagName)) {
                    isContain = true;
                    ArticleTag articleTag = new ArticleTag();
                    articleTag.setArticleId(article.getId());
                    articleTag.setTagId(tag.getId());
                    articleTagMapper.insert(articleTag);
                    break;
                }
            }
            if (!isContain) {
                Tag tag = new Tag();
                tag.setTagName(tagName);
                tag.setAvatar("");
                tagMapper.insert(tag);

                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(tag.getId());
                articleTagMapper.insert(articleTag);
            }
        }

        clearCache();

        return article.getId();
    }

    @Override
    public PageVO<ArticleVO> getArticleListByAuthorIdAndTagId(ArticleTagVO articleTagVO) {
        Integer pageSize = articleTagVO.getPageSize();
        int totalNum = articleMapper.selectCountByAuthorIdAndTagId(
                articleTagVO.getAuthorId(), articleTagVO.getTagId());

        int offset = (articleTagVO.getPageNo() - 1) * pageSize;
        List<Article> articleList = articleMapper.getArticleListByAuthorIdAndTagId(
                articleTagVO.getAuthorId(), articleTagVO.getTagId(),
                offset, pageSize);

        ArrayList<ArticleVO> articleVOList = new ArrayList<>();
        for (Article article : articleList) {
            articleVOList.add(convertArticleDoToVo(article));
        }

        int allPageNum;
        if (totalNum % pageSize == 0) {
            allPageNum = totalNum / pageSize;
        } else {
            allPageNum = totalNum / pageSize + 1;
        }
        return new PageVO<>(allPageNum, articleVOList);
    }

    private void clearCache() {
        rocketMQTemplate.convertAndSend("blog-api-articles", "clearArticleListCache");
    }

    private ArticleContentVO generateArticleContentVO(Article article) {
        ArticleContentVO articleContentVO = new ArticleContentVO();
        BeanUtils.copyProperties(article, articleContentVO);
        articleContentVO.setCreateDate(DateUtil.convertTimestampToStr(
                article.getCreateDate(), "yyyy-MM-dd HH:mm"));
        articleContentVO.setAuthor(sysUserMapper.findUserById(article.getAuthorId()).getNickname());

        List<Tag> tagDOList = tagMapper.findTagsByArticleId(article.getId());
        ArrayList<TagVO> tagVOList = new ArrayList<>();
        for (Tag tag : tagDOList) {
            TagVO tagVO = new TagVO();
            tagVOList.add(tagVO);
            BeanUtils.copyProperties(tag, tagVO);
        }
        articleContentVO.setTagList(tagVOList);

        ArticleBody articleBody = articleBodyMapper.selectById(article.getBodyId());
        ArticleBodyVO articleBodyVO = new ArticleBodyVO();
        articleBodyVO.setContent(articleBody.getContent());
        articleBodyVO.setContentHtml(articleBody.getContentHtml());
        articleContentVO.setBody(articleBodyVO);

        Category category = categoryMapper.selectById(article.getCategoryId());
        CategoryVO categoryVO = new CategoryVO();
        BeanUtils.copyProperties(category, categoryVO);
        articleContentVO.setCategory(categoryVO);
        return articleContentVO;
    }

    private ArticleVO convertArticleDoToVo(Article article) {
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(article, articleVO);
        String dateTime = new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm");
        articleVO.setCreateDate(dateTime);
        List<Tag> tagList = tagMapper.findTagsByArticleId(article.getId());
        articleVO.setAuthor(sysUserMapper.findUserById(article.getAuthorId()).getNickname());

        ArrayList<TagVO> tagVOList = new ArrayList<>();
        for (Tag tag : tagList) {
            TagVO tagVO = new TagVO();
            tagVOList.add(tagVO);
            tagVO.setId(tag.getId());
            tagVO.setTagName(tag.getTagName());
        }
        articleVO.setTagList(tagVOList);
        return articleVO;
    }
}
