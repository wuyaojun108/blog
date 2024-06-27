package org.wyj.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.wyj.blog.entity.dos.Article;
import org.wyj.blog.entity.dto.ArticleArchiveDTO;

import java.util.List;

// @Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    List<ArticleArchiveDTO> listArchives();

    List<Article> getArticleListByAuthorIdAndTagId(@Param("authorId") Integer authorId,
                                                     @Param("tagId") Integer tagId,
                                                     @Param("offset") Integer offset,
                                                     @Param("pageSize") Integer pageSize);

    int selectCountByAuthorIdAndTagId(@Param("authorId") Integer authorId,
                                      @Param("tagId") Integer tagId);
}
