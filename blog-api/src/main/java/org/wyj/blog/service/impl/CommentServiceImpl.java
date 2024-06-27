package org.wyj.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wyj.blog.mapper.CommentMapper;
import org.wyj.blog.mapper.SysUserMapper;
import org.wyj.blog.entity.dos.Comment;
import org.wyj.blog.entity.dos.SysUser;
import org.wyj.blog.entity.param.CreateCommentParam;
import org.wyj.blog.entity.vo.CommentVO;
import org.wyj.blog.entity.vo.UserVO;
import org.wyj.blog.service.ICommentService;
import org.wyj.blog.utils.DateUtil;
import org.wyj.blog.utils.UserThreadLocalUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<CommentVO> getCommentsByArticleId(Long articleId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, articleId);
        queryWrapper.eq(Comment::getLevel, 1);
        List<Comment> commentList = commentMapper.selectList(queryWrapper);

        ArrayList<CommentVO> commentVOList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentVOList.add(convertCommentDoToVo(comment));
        }
        return commentVOList;
    }

    @Override
    public int create(CreateCommentParam commentParam) {
        SysUser sysUser = UserThreadLocalUtil.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setContent(commentParam.getContent());
        comment.setAuthorId(sysUser.getId());
        comment.setCreateDate(System.currentTimeMillis());
        Integer parentId = commentParam.getParentId();
        if (parentId == null) {
            comment.setLevel("1");
        } else {
            comment.setLevel("2");
        }
        comment.setParentId(parentId);
        comment.setToUid(commentParam.getToUserId());
        commentMapper.insert(comment);
        return comment.getId();
    }

    private CommentVO convertCommentDoToVo(Comment comment) {
        CommentVO commentVO = new CommentVO();
        BeanUtils.copyProperties(comment, commentVO);

        commentVO.setId(comment.getId().longValue());
        commentVO.setCreateDate(DateUtil.convertTimestampToStr(
                comment.getCreateDate(), "yyyy-MM-dd HH:mm"));
        SysUser sysUser = sysUserMapper.findUserById(comment.getAuthorId());
        UserVO userVO = new UserVO();
        userVO.setId(sysUser.getId());
        userVO.setAvatar(sysUser.getAvatar());
        userVO.setNickname(sysUser.getNickname());
        commentVO.setAuthor(userVO);

        if ("1".equals(comment.getLevel())) {
            Integer commentId = comment.getId();
            List<CommentVO> children = findCommentsByParentId(commentId);
            commentVO.setChildren(children);
        }
        return commentVO;
    }

    private List<CommentVO> findCommentsByParentId(Integer commentId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, commentId);
        List<Comment> commentList = commentMapper.selectList(queryWrapper);

        ArrayList<CommentVO> commentVOList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentVOList.add(convertCommentDoToVo(comment));
        }
        return commentVOList;
    }

}
