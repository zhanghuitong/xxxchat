# xxxChat IM 设计

## 概述

服务端每次启动需要启动两个server
1. httpServer 处理业务逻辑的server http://localhost:10087
2. websocketServer 处理webSocket连接的server http://localhost:10086


## 接入流程
导入sql到数据库中
"# xxxchat" 
