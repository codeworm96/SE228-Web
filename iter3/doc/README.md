# 书店 ~ 迭代三

## 简介

本书店采用 Java 开发，使用 Spring MVC 作为 MVC 框架，Spring 作为 IoC 框架，Hibernate 作为 ORM 框架，
并使用 Spring Security 实现权限验证功能。

数据库方面主要数据存入 MySQL 数据库，通过 Hibernate 进行 ORM, 统计功能实现为存储过程, 通过 JDBC 直接调用,
用户部分个人信息，头像及书籍封面图片存入 MongoDB, 通过其 Java driver 进行操作。

迭代三要求的所有功能均已实现。

## 配置指南

本书店数据库定义位于 `src/sql/init.sql` 请先以此建立数据库。
在 `src/main/resources/hibernate.cfg` 和 `src/main/resources/spring-mvc.xml` 中需根据自己配置,
修改 MySQL 配置, 在 `applicationContext.xml` 中对 MongoDB 进行配置。
