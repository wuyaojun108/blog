package org.wyj.blog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wyj.blog.aop.cache.Cache;
import org.wyj.blog.aop.log.LogAnnotation;
import org.wyj.blog.entity.dto.ArticleArchiveDTO;
import org.wyj.blog.entity.param.PageParam;
import org.wyj.blog.entity.param.PublishArticleParam;
import org.wyj.blog.entity.vo.*;
import org.wyj.blog.service.IArticleService;
import org.wyj.blog.utils.JsonUtil;

import java.util.List;

/**
 * @author 武耀君
 * @date 2024/3/16
 *
 * 文章
 */
@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {
    private static final Logger LOG = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private IArticleService articleService;

    // 文章列表
    @PostMapping("/list")
    @LogAnnotation(module = "文章", operation = "获取文章列表")
    @Cache(name = "文章列表")
    public String list(@RequestBody String requestBody) {
        PageParam pageParam = JsonUtil.fromJson(requestBody, PageParam.class);
        PageVO<ArticleVO> pageVO;
        try {
            pageVO = articleService.selectPage(pageParam);
        } catch (Exception e) {
            LOG.error("Getting article list failed.", e);
            return ResultVO.fail("读取文章列表出错.").toJson();
        }
        return ResultVO.success(pageVO).toJson();
    }

    // 最热文章，根据浏览次数排名，获取前5篇文章
    @GetMapping("/hotArticles")
    @LogAnnotation(module = "文章", operation = "获取最热文章")
    public String hotArticles() {
        int limit = 5;
        List<HotArticleVO> hotArticleVOList;
        try {
            hotArticleVOList = articleService.hotArticles(limit);
        } catch (Exception e) {
            LOG.error("Getting hottest articles failed", e);
            return ResultVO.fail("查询最热文章失败").toJson();
        }
        return ResultVO.success(hotArticleVOList).toJson();
    }

    // 最新文章，根据创建日期排名，获取前5篇文章
    @GetMapping("/newArticles")
    @LogAnnotation(module = "文章", operation = "获取最新文章")
    public String newArticles() {
        int limit = 5;
        List<LatestArticleVO> latestArticleVOList;
        try {
            latestArticleVOList = articleService.newArticles(limit);
        } catch (Exception e) {
            LOG.error("Getting new articles failed", e);
            return ResultVO.fail("查询最新文章失败").toJson();
        }
        return ResultVO.success(latestArticleVOList).toJson();
    }

    // 文章归档，根据创建日期，将文章分类到不同的年和月中，返回某年月下有几篇文章
    @GetMapping("/listArchives")
    public String listArchives() {
        List<ArticleArchiveDTO> articleArchiveDTOList;
        try {
            articleArchiveDTOList = articleService.listArchives();
        } catch (Exception e) {
            LOG.error("Getting archives failed", e);
            return ResultVO.fail("查询文章归档失败").toJson();
        }
        return ResultVO.success(articleArchiveDTOList).toJson();
    }

    // 查看某篇文章，查看文章内容
    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") Long articleId) {
        ArticleContentVO articleContentVO;
        try {
            articleContentVO = articleService.getArticleContent(articleId);
        } catch (Exception e) {
            LOG.error("Getting article content failed", e);
            return ResultVO.fail("获取文章内容失败").toJson();
        }
        return ResultVO.success(articleContentVO).toJson();
    }

    // 发布文章，允许用户在发布文章时自定义标签，同时用户还可以查看某个标签下的所有文章
    @PostMapping("/publish")
    public String publish(@RequestBody String requestBody) {
        PublishArticleParam publishParam = JsonUtil.fromJson(requestBody, PublishArticleParam.class);
        long result;
        try {
            result = articleService.publish(publishParam);
        } catch (Exception e) {
            LOG.error("Publishing article failed", e);
            return ResultVO.fail("发布文章失败").toJson();
        }
        return ResultVO.success(result).toJson();
    }
}
