package org.wyj.blog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wyj.blog.entity.dos.Tag;
import org.wyj.blog.entity.dto.ArticleTagVO;
import org.wyj.blog.entity.vo.ArticleVO;
import org.wyj.blog.entity.vo.PageVO;
import org.wyj.blog.entity.vo.ResultVO;
import org.wyj.blog.entity.vo.TagVO;
import org.wyj.blog.service.IArticleService;
import org.wyj.blog.service.ITagService;
import org.wyj.blog.utils.JsonUtil;

import java.util.List;

/**
 * @author 武耀君
 * @date 2024/3/16
 *
 * 文章标签
 */
@RestController
@RequestMapping("/api/v1/tag")
public class TagController {
    private static final Logger LOG = LoggerFactory.getLogger(TagController.class);

    @Autowired
    private IArticleService articleService;

    @Autowired
    private ITagService tagService;

    // 标签列表
    @GetMapping("/list")
    public String list() {
        List<TagVO> tagVOList;
        try {
            tagVOList = tagService.list();
        } catch (Exception e) {
            LOG.error("Getting tag list failed.", e);
            return ResultVO.fail("查询tag列表失败").toJson();
        }
        return ResultVO.success(tagVOList).toJson();
    }

    // 标签详情
    @GetMapping("/detailList")
    public String detailList() {
        List<Tag> tagList;
        try {
            tagList = tagService.detailList();
        } catch (Exception e) {
            LOG.error("Getting tag detail failed.", e);
            return ResultVO.fail("查询tag列表失败").toJson();
        }
        return ResultVO.success(tagList).toJson();
    }

    @PostMapping("/getArticleListByAuthorIdAndTagId")
    public String getArticleListByAuthorIdAndTagId(@RequestBody String requestBody) {
        ArticleTagVO articleTagVO = JsonUtil.fromJson(requestBody, ArticleTagVO.class);
        PageVO<ArticleVO> pageVO;
        try {
            pageVO = articleService.getArticleListByAuthorIdAndTagId(articleTagVO);
        } catch (Exception e) {
            LOG.error("Getting article list failed.", e);
            return ResultVO.fail("读取文章列表出错.").toJson();
        }
        return ResultVO.success(pageVO).toJson();
    }
}
