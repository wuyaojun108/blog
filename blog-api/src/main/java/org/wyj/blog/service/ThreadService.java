package org.wyj.blog.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.wyj.blog.mapper.ArticleMapper;
import org.wyj.blog.entity.dos.Article;

@Component
public class ThreadService {

    @Async
    public void updateViewCounts(ArticleMapper articleMapper, Article article) {
        Integer viewCounts = article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCounts + 1);
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId, article.getId());
        updateWrapper.eq(Article::getViewCounts, article.getViewCounts());
        articleMapper.update(articleUpdate, updateWrapper);
    }
}
