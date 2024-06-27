package org.wyj.blog.entity.dto;

import org.wyj.blog.entity.param.PageParam;

public class ArticleTagVO extends PageParam {
    private Integer authorId;
    private Integer tagId;

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }
}
