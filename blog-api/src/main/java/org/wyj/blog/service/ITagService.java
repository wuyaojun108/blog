package org.wyj.blog.service;

import org.wyj.blog.entity.dos.Tag;
import org.wyj.blog.entity.vo.TagVO;

import java.util.List;

public interface ITagService {
    List<Tag> findTagsByArticleId(Long articleId);

    List<TagVO> list();

    List<Tag> detailList();
}
