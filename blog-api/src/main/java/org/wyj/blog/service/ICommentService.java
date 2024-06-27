package org.wyj.blog.service;

import org.wyj.blog.entity.param.CreateCommentParam;
import org.wyj.blog.entity.vo.CommentVO;

import java.util.List;

public interface ICommentService {
    List<CommentVO> getCommentsByArticleId(Long articleId);

    int create(CreateCommentParam commentParam);
}
