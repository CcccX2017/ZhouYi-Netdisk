# 项目简介

该项目是一款基于Spring Boot的网盘项目，命名为：舟意网盘。由毕业设计全新重构升级而来，原先的毕业设计是真实的实际项目，当时采用Spring、SpringMVC、Mybatis框架来完成，没有使用Maven进行包管理，故导致项目目录结构相对杂乱。于是利用空闲休息时间开始重新重构此项目，项目基于Spring Boot进行开发，使用Maven进行包管理并且分模块进行构建项目，如此有了舟意网盘。目的以重构项目为主，在移植完原先功能之后再陆续辅以新增一些实用功能。

# 项目模块介绍

工程父项目为ZhouYi-Netdisk，并通过继承方式继承Spring Boot。

【父项目下分8个公共子项目】：

- zhouyi-netdisk-api：整个项目暴露的接口的定义项目，按每个功能模块进行子包拆分。
- zhouyi-netdisk-common：是整个工程的配置核心，包括所有集成第三方框架的配置定义。除此之外还包括项目每个模块及整个项目的常量定义。
- zhouyi-netdisk-dao：是整个项目的数据交互层，存放Mapper文件以及对应的XML文件并按每个功能模块进行子包拆分。
- zhouyi-netdisk-model：项目中用到的Entity、Vo等定义的工程。
- zhouyi-netdisk-service：项目服务代码实现工程，存放服务接口以及实现类，按每个功能模块进行子包拆分。
- zhouyi-netdisk-utils：工程公用工具类项目，包含加密/解密、Date、JSON等工具类。
- zhouyi-netdisk-controller：工程Controller实现项目
- zhouyi-netdisk-web：工程前端页面以及SpringBoot启动类入口实现项目。前端页面采用jsp页面实现。

# 开发环境说明

- JDK 1.8
- Intellij Idea
- Tomcat 8.5
- Git
- Maven

# 内置功能

1. 个人信息管理：查看订单信息、查看个人信息、修改个人信息、修改密码
2. 文件资源管理：上传文件、下载文件、收藏文件、移动文件、复制文件、重命名文件、删除文件、在线预览
3. 空间扩容：扩容网盘可用空间
4. 文件夹管理：新建文件夹、移动文件夹、复制文件夹、重命名文件夹、删除文件夹
5. 分享管理：好友分享、公共分享、URL分享
6. 好友管理：添加好友、删除好友、好友备注
7. 持续更新中。。。



