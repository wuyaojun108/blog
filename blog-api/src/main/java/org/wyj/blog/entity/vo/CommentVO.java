package org.wyj.blog.entity.vo;

import java.util.List;

public class CommentVO {
    private Long id;
    private UserVO author;
    private String content;
    private List<CommentVO> children;
    private String createDate;
    private Integer level;
    private UserVO toUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserVO getAuthor() {
        return author;
    }

    public void setAuthor(UserVO author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<CommentVO> getChildren() {
        return children;
    }

    public void setChildren(List<CommentVO> children) {
        this.children = children;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public UserVO getToUser() {
        return toUser;
    }

    public void setToUser(UserVO toUser) {
        this.toUser = toUser;
    }
}
