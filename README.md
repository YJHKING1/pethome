# PetHome

PetHome 是一个基于 Spring Boot 的宠物乐园后端练习项目，用于学习和实践 Java Web 后端开发。

## 项目说明

本项目主要用于练习 Spring Boot、MyBatis、MySQL、Redis、邮件发送、文件上传、接口文档等后端开发常见功能。仓库中的配置文件已改为使用环境变量或示例占位值，真实账号、密码、授权码等敏感信息不应提交到仓库。

## 技术栈

- Java 8
- Spring Boot 2.7.1
- Spring Web
- MyBatis
- MySQL
- Redis / Jedis
- Lombok
- Swagger2
- FastDFS
- Spring Mail
- JWT
- Freemarker

## 配置说明

主配置文件：

```text
src/main/resources/application.properties
```

示例配置文件：

```text
src/main/resources/application-example.properties
```

本地运行前，请根据示例配置准备以下环境变量，或在本地自行创建不提交到 Git 的配置文件：

```text
DB_USER
DB_PWD
DB_URL
MAIL_HOST
MAIL_USER
MAIL_PWD
REDIS_HOST
REDIS_PORT
REDIS_PWD
TEMPLATE_DIR
HTML_DIR
```

## 启动方式

1. 创建 MySQL 数据库 `pethome`
2. 启动 Redis
3. 根据 `application-example.properties` 准备本地配置
4. 使用 IntelliJ IDEA 运行：

```text
org.yjhking.pethome.PethomeApplication
```

## 安全说明

请不要将以下内容提交到仓库：

- 数据库密码
- Redis 密码
- 邮箱授权码
- 云服务 AccessKey
- 本地绝对路径配置
- 生产环境配置文件

## 项目状态

该仓库为早期 Java 后端练习项目，主要用于学习记录和技能恢复参考。
