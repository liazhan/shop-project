# 一、概述
目前技术栈如下：

* SpringBoot：2.2.6.RELEASE  
* SpringCloud：Hoxton.SR3  
* Swagger：1.7.0.RELEASE  
* JPA：2.2.6.RELEASE  
* Docker：19.03.8  
* Mysql：8.0.19  
* Redis：5.0.8  
* RabbitMQ：rabbitmq:3-management  
* kafka版本：2.5  
* zookeeper版本：3.6.1  
* elasticsearch版本：7.6.2  
* canal：1.1.4  
* VUE：2.9.6  


<br>

从技术栈可以看出，本项目采用前后端分离的架构，SpringBoot+SpringCloud+Vue在目前非常流行；

其中还涉及到Nexus3搭建maven私服，GitLab搭建代码管理私服，微信公众号消息接口开发，腾讯云短信接口开发，七牛云作为文件服务器，Bean类采用DTO模型。

 

前端项目github地址：https://github.com/liazhan/shop-project-web

配置文件github地址：https://github.com/liazhan/shop-project-config

<br>

目前项目还在持续编写当中，后续还会继续更新...

<br>


# 二、搭建过程
[微服务电商实战(一)电商项目概述](https://blog.csdn.net/daziyuanazhen/article/details/106032001)

[微服务电商实战(二)项目架构初步搭建](https://blog.csdn.net/daziyuanazhen/article/details/105354433)

[微服务电商实战(三)gateway统一管理swagger](https://blog.csdn.net/daziyuanazhen/article/details/105434442)

[微服务电商实战(四)maven私服与gitlab代码管理](https://blog.csdn.net/daziyuanazhen/article/details/105455889)

[微服务电商实战(五)配置中心](https://blog.csdn.net/daziyuanazhen/article/details/105494267)

[微服务电商实战(六)统一响应体](https://blog.csdn.net/daziyuanazhen/article/details/105512961)

[微服务电商实战(七)公众号消息接口开发](https://blog.csdn.net/daziyuanazhen/article/details/105527941)

[微服务电商实战(八)短信接口开发](https://blog.csdn.net/daziyuanazhen/article/details/105558967)

[微服务电商实战(九)注册接口(dto+jpa+mysql+redis)](https://blog.csdn.net/daziyuanazhen/article/details/105637562)

[微服务电商实战(十)登陆接口](https://blog.csdn.net/daziyuanazhen/article/details/105796287)

[微服务电商实战(十一)搭建vue项目对接注册登陆接口，解决跨域问题，使用七牛云实现头像上传](https://blog.csdn.net/daziyuanazhen/article/details/105913186)  

[微服务电商实战(十二)搭建商品服务搜索引擎](https://blog.csdn.net/daziyuanazhen/article/details/106056422)

<br>

# 三、运行方式

1、环境准备
如有以下环境则忽略，推荐用[docker安装](https://blog.csdn.net/daziyuanazhen/article/details/105144511)  
[docker安装mysql](https://blog.csdn.net/daziyuanazhen/article/details/105531164)  
[docker安装redis](https://blog.csdn.net/daziyuanazhen/article/details/105529587)  
[docker安装RabbitMQ](https://blog.csdn.net/daziyuanazhen/article/details/105491300)  
[docker安装elasticsearch](https://blog.csdn.net/daziyuanazhen/article/details/106017708)

<br>
ok,接着需要  

[配置数据库](https://github.com/liazhan/shop-project-sql)  

[新增es数据](https://blog.csdn.net/daziyuanazhen/article/details/106056422)  
<br>
之后在github上创建一个仓库,用来放项目的配置文件,可以参考复制[我的配置文件仓库](https://github.com/liazhan/shop-project-config),然后对里边后缀名为dev的配置文件进行更改.主要是mysql、redis、rabbitmq、公众号等配置的更改。不清楚的话可以查看搭建过程博客记录。  
<br>
创建微信公众号订阅号，用来开发公众号消息接口，其中需要用到内网穿透，具体可以查看[微服务电商实战(七)公众号消息接口开发](https://blog.csdn.net/daziyuanazhen/article/details/105527941)  
<br>
2、后端项目  
首先使用git克隆下来  
git clone https://github.com/liazhan/shop-project.git  
<br>
接着用idea打开后端项目,修改config服务的application.yml文件中的配置文件仓库地址和rabbit配置、member服务MemberConst常量类配置和weixin服务的SendSms类的腾讯云短信配置。  
<br>
ok,接下来就可以运行我们的项目了。  
首先运行config服务，运行完成会发现报了个找不到eureka的错误，不用管它  
接着运行eureka服务，运行完成后config就会注册到eureka中，不再报错  
最后依次运行gateway、weixin、member、product服务  
运行完成后可以打开swagger界面来查看测试接口等，访问http://127.0.0.1/swagger-ui.html 可以查看所有服务的接口文档  
<br>
3、前端项目
https://github.com/liazhan/shop-project-web









