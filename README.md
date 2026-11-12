# pethome

#### 介绍

宠物乐园

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
