package org.wyj.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.wyj.blog.entity.dos.Tag;

import java.util.List;

//@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    List<Tag> findTagsByArticleId(Long articleId);
}
