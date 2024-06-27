package org.wyj.blog.service;

import org.wyj.blog.entity.dto.ArticleArchiveDTO;
import org.wyj.blog.entity.dto.ArticleTagVO;
import org.wyj.blog.entity.param.PageParam;
import org.wyj.blog.entity.param.PublishArticleParam;
import org.wyj.blog.entity.vo.*;

import java.util.List;

public interface IArticleService {
    PageVO<ArticleVO> selectPage(PageParam pageParam);

    List<HotArticleVO> hotArticles(int limit);

    List<LatestArticleVO> newArticles(int limit);

    List<ArticleArchiveDTO> listArchives();

    ArticleContentVO getArticleContent(Long articleId);

    long publish(PublishArticleParam publishParam);

    PageVO<ArticleVO> getArticleListByAuthorIdAndTagId(ArticleTagVO articleTagVO);
}
