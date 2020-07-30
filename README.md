# 记账软件后端

使用SpringBoot搭建的网站后端

## 拉取镜像

> docker pull registry.cn-shanghai.aliyuncs.com/mrbook/accounting-backend:[镜像版本号]

## 启动

> docker run --name accounting-backend --network my-net -d registry.cn-shanghai.aliyuncs.com/mrbook/accounting-backend
>:[镜像版本号]

## 注意

1. 项目中实体类的id为String类型，表示MongoDB中的id，uid为int类型，表示为自己生成的id号