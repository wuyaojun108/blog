package org.wyj.blog.service.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Slf4j
@RocketMQMessageListener(topic = "blog-api-articles", consumerGroup = "blog-api-g1")
public class rocketMQConsumerListener implements RocketMQListener<String> {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void onMessage(String message) {
        if (message != null && message.equalsIgnoreCase("clearArticleListCache")) {
            String pattern = "*ArticleController::list::*";
            Set<String> keys = redisTemplate.keys(pattern);
            // 清除符合条件的缓存
            if(keys != null) {
                for (String key : keys) {
                    Boolean delete = redisTemplate.delete(key);
                    if (Boolean.TRUE.equals(delete)) {
                        log.info("更新缓存，删除键：" + key);
                    }
                }
            }
        }
    }
}
