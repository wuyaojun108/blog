-- MySQL dump 10.13  Distrib 8.3.0, for macos14.2 (arm64)
--
-- Host: localhost    Database: blog
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ms_article`
--

DROP TABLE IF EXISTS `ms_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ms_article` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(64) DEFAULT NULL COMMENT '标题',
  `summary` varchar(255) DEFAULT NULL COMMENT '简介',
  `comment_counts` int DEFAULT NULL COMMENT '评论数量',
  `view_counts` int DEFAULT NULL COMMENT '浏览数量',
  `author_id` bigint DEFAULT NULL COMMENT '作者ID',
  `body_id` bigint DEFAULT NULL COMMENT '内容ID',
  `category_id` int DEFAULT NULL COMMENT '类别ID',
  `weight` int DEFAULT NULL COMMENT '是否置顶 1是置顶，0是不置顶',
  `create_date` bigint DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ms_article`
--

LOCK TABLES `ms_article` WRITE;
/*!40000 ALTER TABLE `ms_article` DISABLE KEYS */;
INSERT INTO `ms_article` VALUES (1,'springboot介绍以及入门案例','通过Spring Boot实现的服务，只需要依靠一个Java类，把它打包成jar，并通过`java -jar`命令就可以运行起来。这一切相较于传统Spring应用来说，已经变得非常的轻便、简单。',0,163,1,1,2,0,1621947720727),(2,'Vue.js 到底是什么','Vue (读音 /vjuː/，类似于 view) 是一套用于构建用户界面的渐进式框架。',5,128,1,2,3,0,1613947720727),(3,'Element相关','本节将介绍如何在项目中使用 Element。',3,52,1,3,5,0,1624030002164),(8,'springboot学习','springboot入门案例',0,4,1,8,2,0,1651237549341),(21,'aaaaaaaa','bbbbbbbbbb',0,0,1,21,2,0,1651557143449),(22,'测试','发布文章测试',0,0,1519679195276402693,22,NULL,0,NULL),(23,'测试','发布文章测试',0,1,1519679195276402693,23,NULL,0,NULL),(24,'测试','发布文章测试',0,2,1519679195276402693,24,1,0,1710669018239),(25,'测试','发布文章-测试自定义tag',0,0,1519679195276402693,25,1,0,1710684219152),(26,'测试','发布文章-测试自定义tag',0,0,1519679195276402693,26,1,0,1710684263196),(27,'测试','发布文章-测试自定义Logger',0,0,1519679195276402693,27,1,0,1711253022150),(28,'测试','发布文章-测试rocketMQ',0,0,1519679195276402693,28,1,0,1711807102330),(29,'测试','发布文章-测试rocketMQ2',0,0,1519679195276402693,29,1,0,1711807202342),(30,'测试','发布文章-测试rocketMQ2',0,0,1519679195276402693,30,1,0,1711807223877),(31,'测试','发布文章-测试rocketMQ3',0,0,1519679195276402693,31,1,0,1711807247431),(32,'测试','发布文章-测试rocketMQ4',0,0,1519679195276402693,32,1,0,1711807427667),(33,'测试','发布文章-测试rocketMQ5',0,0,1519679195276402693,33,1,0,1711807534601),(34,'测试','发布文章-测试rocketMQ6',0,0,1519679195276402693,34,1,0,1711807706729),(35,'测试','发布文章-测试rocketMQ7',0,0,1519679195276402693,35,1,0,1711807944671),(36,'测试','发布文章-测试rocketMQ7',0,0,1519679195276402693,36,1,0,1711808171116),(37,'测试','发布文章-测试rocketMQ7',0,0,1519679195276402693,37,1,0,1711808275121),(38,'测试','发布文章-测试rocketMQ7',0,0,1519679195276402693,38,1,0,1711808833979),(39,'测试','发布文章-测试rocketMQ7',0,0,1519679195276402693,39,1,0,1711808887030),(40,'测试','发布文章-测试rocketMQ7',0,0,1519679195276402693,40,1,0,1711893372613);
/*!40000 ALTER TABLE `ms_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ms_article_body`
--

DROP TABLE IF EXISTS `ms_article_body`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ms_article_body` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` longtext,
  `content_html` longtext,
  `article_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `article_id` (`article_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ms_article_body`
--

LOCK TABLES `ms_article_body` WRITE;
/*!40000 ALTER TABLE `ms_article_body` DISABLE KEYS */;
INSERT INTO `ms_article_body` VALUES (1,'自定义注解进行日志记录：第一步：自定义注解，注解中有两个字段，module和operation，分别代表是哪个模块在记录日志和正在进行什么操作。第二步：配置切面：配置切入点、配置增强方法，并且引用切入点','自定义注解进行日志记录：第一步：自定义注解，注解中有两个字段，module和operation，分别代表是哪个模块在记录日志和正在进行什么操作。第二步：配置切面：配置切入点、配置增强方法，并且引用切入点',1),(2,'spring中的拦截器：用于对请求进行预处理和后续处理，spring中有两种拦截器：HandlerInterceptor和MethodInterceptor，HnadlerInterceptor用于拦截请求，MethodInterceptor用户拦截方法，HandlerInterceptor先执行。拦截器和过滤器的区别：Filter是Servlet规范规定的，不属于spring框架，也是用于请求的拦截。Filter的实现方式是基于回调函数，Interceptor的实现方式是基于动态代理','spring中的拦截器：用于对请求进行预处理和后续处理，spring中有两种拦截器：HandlerInterceptor和MethodInterceptor，HnadlerInterceptor用于拦截请求，MethodInterceptor用户拦截方法，HandlerInterceptor先执行。拦截器和过滤器的区别：Filter是Servlet规范规定的，不属于spring框架，也是用于请求的拦截。Filter的实现方式是基于回调函数，Interceptor的实现方式是基于动态代理',2),(3,'方法如果被@Transactional注解修饰，在方法中就不可以捕获异常了，否则@Transactional注解获取不到异常，无法进行回滚。@Transactional 注解只能应用到 public 可见度的方法上。 如果应用在protected、private或者 package可见度的方法上，也不会报错，不过事务设置不会起作用。默认情况下，Spring会对unchecked异常进行事务回滚；如果是checked异常则不回滚。','方法如果被@Transactional注解修饰，在方法中就不可以捕获异常了，否则@Transactional注解获取不到异常，无法进行回滚。@Transactional 注解只能应用到 public 可见度的方法上。 如果应用在protected、private或者 package可见度的方法上，也不会报错，不过事务设置不会起作用。默认情况下，Spring会对unchecked异常进行事务回滚；如果是checked异常则不回滚。',3),(8,'springboot拦截器的使用',NULL,8),(9,'rocketmq入门',NULL,9),(10,'rocketmq入门',NULL,10),(11,'rocketmq入门',NULL,11),(12,'rocketmq入门',NULL,12),(13,'rocketmq入门',NULL,13),(14,'rocketmq入门',NULL,14),(15,'rocketmq入门',NULL,15),(16,'rocketmq入门',NULL,16),(17,'rocketmq入门',NULL,17),(18,'rocketmq入门',NULL,18),(19,'rocketmq入门2',NULL,19),(20,'rocketmq入门3',NULL,20),(21,'cccccccccccccc',NULL,21),(22,'发布测试','<b>发布测试</b>',22),(23,'发布测试','<b>发布测试</b>',23),(24,'发布测试','<b>发布测试</b>',24),(25,'发布测试','<b>发布测试</b>',25),(26,'发布测试','<b>发布测试</b>',26),(27,'发布测试-测试自定义Logger','<b>发布测试-测试自定义Logger</b>',27),(28,'发布测试-测试rocketMQ','<b>发布测试-测试rocketMQ</b>',28),(29,'发布测试-测试rocketMQ2','<b>发布测试-测试rocketMQ2</b>',29),(30,'发布测试-测试rocketMQ2','<b>发布测试-测试rocketMQ2</b>',30),(31,'发布测试-测试rocketMQ2','<b>发布测试-测试rocketMQ2</b>',31),(32,'发布测试-测试rocketMQ2','<b>发布测试-测试rocketMQ2</b>',32),(33,'发布测试-测试rocketMQ2','<b>发布测试-测试rocketMQ2</b>',33),(34,'发布测试-测试rocketMQ2','<b>发布测试-测试rocketMQ2</b>',34),(35,'发布测试-测试rocketMQ2','<b>发布测试-测试rocketMQ2</b>',35),(36,'发布测试-测试rocketMQ2','<b>发布测试-测试rocketMQ2</b>',36),(37,'发布测试-测试rocketMQ2','<b>发布测试-测试rocketMQ2</b>',37),(38,'发布测试-测试rocketMQ2','<b>发布测试-测试rocketMQ2</b>',38),(39,'发布测试-测试rocketMQ2','<b>发布测试-测试rocketMQ2</b>',39),(40,'发布测试-测试rocketMQ2','<b>发布测试-测试rocketMQ2</b>',40);
/*!40000 ALTER TABLE `ms_article_body` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ms_article_tag`
--

DROP TABLE IF EXISTS `ms_article_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ms_article_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `article_id` bigint DEFAULT NULL COMMENT '文章ID',
  `tag_id` bigint DEFAULT NULL COMMENT '标签ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ms_article_tag`
--

LOCK TABLES `ms_article_tag` WRITE;
/*!40000 ALTER TABLE `ms_article_tag` DISABLE KEYS */;
INSERT INTO `ms_article_tag` VALUES (1,1,1),(2,1,2),(3,1,3),(4,2,3),(5,2,4),(6,3,2),(7,3,3),(8,3,4),(9,8,1),(10,8,2),(11,8,3),(12,9,1),(13,10,1),(14,11,1),(15,12,1),(16,13,3),(17,14,1),(18,15,1),(19,16,1),(20,17,1),(21,18,1),(22,19,1),(23,20,3),(24,21,2),(25,21,4),(26,22,1),(27,22,2),(28,22,3),(29,23,1),(30,23,2),(31,23,3),(32,24,1),(33,24,2),(34,24,3),(35,25,2),(36,26,2),(37,26,5),(38,26,6),(39,27,2),(40,27,5),(41,27,6),(42,28,2),(43,28,5),(44,28,6),(45,29,2),(46,29,5),(47,29,6),(48,30,2),(49,30,5),(50,30,6),(51,31,2),(52,31,5),(53,31,6),(54,32,2),(55,32,5),(56,32,6),(57,33,2),(58,33,5),(59,33,6),(60,34,2),(61,34,5),(62,34,6),(63,35,2),(64,35,5),(65,35,6),(66,36,2),(67,36,5),(68,36,6),(69,37,2),(70,37,5),(71,37,6),(72,38,2),(73,38,5),(74,38,6),(75,39,2),(76,39,5),(77,39,6),(78,40,2),(79,40,5),(80,40,6);
/*!40000 ALTER TABLE `ms_article_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ms_category`
--

DROP TABLE IF EXISTS `ms_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ms_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) DEFAULT NULL COMMENT '分类图标',
  `category_name` varchar(255) DEFAULT NULL COMMENT '分类名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ms_category`
--

LOCK TABLES `ms_category` WRITE;
/*!40000 ALTER TABLE `ms_category` DISABLE KEYS */;
INSERT INTO `ms_category` VALUES (1,'/category/front.png','前端','前端是什么，大前端'),(2,'/category/back.png','后端','后端最牛叉'),(3,'/category/life.png','生活','生活趣事'),(4,'/category/database.png','数据库','没数据库，啥也不管用'),(5,'/category/language.png','编程语言','好多语言，该学哪个？');
/*!40000 ALTER TABLE `ms_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ms_comment`
--

DROP TABLE IF EXISTS `ms_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ms_comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `create_date` bigint DEFAULT NULL,
  `article_id` bigint DEFAULT NULL,
  `author_id` bigint DEFAULT NULL,
  `parent_id` int DEFAULT NULL,
  `to_uid` bigint DEFAULT NULL,
  `level` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ms_comment`
--

LOCK TABLES `ms_comment` WRITE;
/*!40000 ALTER TABLE `ms_comment` DISABLE KEYS */;
INSERT INTO `ms_comment` VALUES (1,'非常优秀',1651240062924,1,1,NULL,2,'1'),(2,'说得对',1651240072924,1,2,1,1,'2'),(3,NULL,1704074226531,1,1519679195276402693,NULL,NULL,'1'),(4,NULL,1710669088960,24,1519679195276402693,NULL,NULL,'1'),(5,NULL,1710669340123,24,1519679195276402693,NULL,NULL,'1'),(6,'测试',1710669416125,24,1519679195276402693,NULL,1,'1'),(7,'测试111',1710669468138,24,1519679195276402693,NULL,1,'1'),(8,'测试',1710669483211,24,1519679195276402693,7,1,'2'),(9,'测试111',1711871780430,24,1519679195276402693,NULL,1,'1'),(10,'测试',1711871791658,24,1519679195276402693,7,1,'2');
/*!40000 ALTER TABLE `ms_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ms_sys_user`
--

DROP TABLE IF EXISTS `ms_sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ms_sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account` varchar(64) DEFAULT NULL COMMENT '账户',
  `admin` bit(1) DEFAULT NULL COMMENT '是否管理员',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `create_date` bigint DEFAULT NULL COMMENT '注册时间',
  `deleted` bit(1) DEFAULT NULL COMMENT '是否删除',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `last_login` bigint DEFAULT NULL COMMENT '最后登录时间',
  `mobile_phone_number` varchar(20) DEFAULT NULL COMMENT '手机号',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `password` varchar(60) DEFAULT NULL COMMENT '密码',
  `salt` varchar(255) DEFAULT NULL COMMENT '加密盐',
  `status` varchar(255) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1741293167182880775 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ms_sys_user`
--

LOCK TABLES `ms_sys_user` WRITE;
/*!40000 ALTER TABLE `ms_sys_user` DISABLE KEYS */;
INSERT INTO `ms_sys_user` VALUES (1,'admin',_binary '','http://localhost:8080/static/img/logo.b3a48c0.png',20210622223122,_binary '\0','11',20210630223130,'12','管理员','08f2c9cdbb55ef39e38a159f2f2b426e','orgwjt@#$%','1'),(2,'user2',_binary '\0',NULL,1651196501236,_binary '\0','',1651196501236,NULL,'user2','08f2c9cdbb55ef39e38a159f2f2b426e','orgwjt@#$%',''),(3,'user1',_binary '\0',NULL,1651240013078,_binary '\0','',1651240013078,NULL,'王者荣耀','3b0bc4987d0f8b3bfae88ba755fb009c','orgwjt@#$%',''),(4,'user1',_binary '\0',NULL,1651240062924,_binary '\0','',1651240062924,NULL,'王者荣耀','3b0bc4987d0f8b3bfae88ba755fb009c','orgwjt@#$%',''),(1519679195276402693,'zhangsan',_binary '\0',NULL,1651240072924,_binary '\0',NULL,1651240092924,NULL,'枯枝败叶','3d8185db32fc114e9e96375331a3ce6e','3edc#EDC34%^','0'),(1741291837135589378,'lisi',_binary '\0',NULL,1703991300264,_binary '\0',NULL,NULL,NULL,'李四','3d8185db32fc114e9e96375331a3ce6e','3edc#EDC34%^','0'),(1741293095275732993,'wangwu',_binary '\0',NULL,1703991600245,_binary '\0',NULL,NULL,NULL,'李四','3d8185db32fc114e9e96375331a3ce6e','3edc#EDC34%^','0'),(1741293167182880770,'zhaoliu',_binary '\0',NULL,1703991617376,_binary '\0',NULL,NULL,NULL,'李四','3d8185db32fc114e9e96375331a3ce6e','3edc#EDC34%^','0'),(1741293167182880771,'田七',_binary '\0',NULL,1710553501387,_binary '\0',NULL,NULL,NULL,'李四','3d8185db32fc114e9e96375331a3ce6e','3edc#EDC34%^','0'),(1741293167182880772,'石敢当',_binary '\0',NULL,1710667215428,_binary '\0',NULL,NULL,NULL,'李四','3d8185db32fc114e9e96375331a3ce6e','3edc#EDC34%^','0'),(1741293167182880773,'石敢当1',_binary '\0',NULL,1711252344003,_binary '\0',NULL,NULL,NULL,'李四','3d8185db32fc114e9e96375331a3ce6e','3edc#EDC34%^','0'),(1741293167182880774,'石敢当2',_binary '\0',NULL,1711252498698,_binary '\0',NULL,NULL,NULL,'李四','3d8185db32fc114e9e96375331a3ce6e','3edc#EDC34%^','0');
/*!40000 ALTER TABLE `ms_sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ms_tag`
--

DROP TABLE IF EXISTS `ms_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ms_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) NOT NULL,
  `tag_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ms_tag`
--

LOCK TABLES `ms_tag` WRITE;
/*!40000 ALTER TABLE `ms_tag` DISABLE KEYS */;
INSERT INTO `ms_tag` VALUES (1,'/static/tag/java.png','springboot'),(2,'/static/tag/java.png','spring'),(3,'/static/tag/java.png','springmvc'),(4,'/static/tag/css.png','多线程'),(5,'','java'),(6,'','后端开发');
/*!40000 ALTER TABLE `ms_tag` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-27 20:36:23
