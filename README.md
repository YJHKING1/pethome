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

## 运行时凭据

在仓库外生成新的 RSA 密钥对。用以下命令生成 2048 位密钥，并导出应用读取的 DER 文件。

```sh
umask 077
openssl genpkey -algorithm RSA -pkeyopt rsa_keygen_bits:2048 -out jwt-private.pem
openssl pkcs8 -topk8 -nocrypt -in jwt-private.pem -outform DER -out jwt-private.der
openssl pkey -in jwt-private.pem -pubout -outform DER -out jwt-public.der
```

将文件挂载到应用可读的目录，在所有实例设置 `PETHOME_JWT_PRIVATE_KEY_FILE` 和 `PETHOME_JWT_PUBLIC_KEY_FILE`。切换后现有登录会话需重新登录。

数据库使用 `DB_URL`、`DB_USER` 和 `DB_PWD`。Redis 使用 `REDIS_HOST`、`REDIS_PORT`、`REDIS_DATABASE` 和 `REDIS_PWD`，邮件使用 `MAIL_HOST`、`MAIL_USER` 和 `MAIL_PWD`。微信登录读取 `PETHOME_WECHAT_APP_ID` 和 `PETHOME_WECHAT_APP_SECRET`，百度内容审核读取 `BAIDU_AI_API_KEY` 和 `BAIDU_AI_SECRET_KEY`。

店铺 Excel 导入和导出需要后台管理员分别具备 `shop:import` 和 `shop:export`。部署后在角色管理中授予对应权限，并同时更新管理前端。

## 后台管理权限

用户、员工、角色、权限和菜单管理接口分别使用 `user`、`employee`、`role`、`permission` 和 `menu` 权限前缀。每类的 `:list` 用于查询，`:save` 用于新增和修改，`:delete` 用于单条和批量删除。

升级时先在受信的数据库管理会话中核对 `t_permission` 的权限编号和 `t_role_permission` 的管理员角色关联，再切换流量。首次启用这些权限时需要为受信管理员建立上述关联，普通后台角色仅分配其工作所需的操作。编辑员工需要查询角色列表，编辑角色需要查询权限列表和菜单树。

登录账号和微信绑定记录通过登录、注册、绑定流程维护，原通用 `/logininfo` 和 `/wxwxuser` 接口已移除。

员工与用户接口仅在写入时接收新密码。编辑资料时省略密码或留空会保留存量密码。通过后台管理接口新增的账号可暂不设置密码，设置前无法使用密码登录。
