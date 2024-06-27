package org.wyj.blog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wyj.blog.entity.param.CreateCommentParam;
import org.wyj.blog.entity.vo.CommentVO;
import org.wyj.blog.entity.vo.ResultVO;
import org.wyj.blog.service.ICommentService;
import org.wyj.blog.utils.JsonUtil;

import java.util.List;

/**
 * @author 武耀君
 * @date 2024/3/16
 *
 * 文章的评论
 */
@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    private static final Logger LOG = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private ICommentService commentService;

    // 某篇文章的评论列表
    @GetMapping("/article/{id}")
    public String articleCommentList(@PathVariable("id") Long articleId) {
        List<CommentVO> commentVOList;
        try {
            commentVOList = commentService.getCommentsByArticleId(articleId);
        } catch (Exception e) {
            LOG.error("Getting comments failed", e);
            return ResultVO.fail("获取评论失败").toJson();
        }
        return ResultVO.success(commentVOList).toJson();
    }

    // 创建评论
    @PostMapping("/create")
    public String create(@RequestBody String requestBody) {
        CreateCommentParam commentParam = JsonUtil.fromJson(requestBody, CreateCommentParam.class);
        int result;
        try {
            result = commentService.create(commentParam);
        } catch (Exception e) {
            LOG.error("Creating comment failed", e);
            return ResultVO.fail("创建评论失败").toJson();
        }
        return ResultVO.success(result).toJson();
    }
}
