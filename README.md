# 简介

博客项目，提供了登录注册、针对博客的增删改查功能


使用的技术：springboot、redis、rocketMQ、mysql、jwt


基本功能：提供了登录注册和针对博客的增删改查功能。


主要技术点：

- 使用jwt生成token，然后把token和用户信息存储到redis中，实现登录功能
- 使用ThreadLocal来保存当前请求中用户的登录信息，处理完请求后删除用户信息
- 使用redis缓存文章列表，然后使用rocketMQ来处理缓存一致性问题。如果用户发布文章，发送一条消息到rocketMQ，客户端接受消息，然后删除redis中的所有缓存。

