# 实训项目

> 修改自西财树洞开源项目

## 部署

### 使用 docker-compose 一键部署

在 docker-compose.yml 中稍作配置，copy 到要部署的服务器上 `docker-compose up -d` 即可。

### 部署到本机

参考原仓库 （喜欢折腾是吧，docker一键部署不香？）

## 调试

若使用 docker-compose 部署，远程调试已经配置完成。使用 idea 的 `remote jvm debug` 连接服务器的 5005 端口即可。

## 基于原项目的修改

- 构建 docker 镜像并编写 docker-compose 配置供快速部署
- 添加了 学信网注册/学号登录 的鉴权接口
- 修了原项目一些杂七杂八的 bug