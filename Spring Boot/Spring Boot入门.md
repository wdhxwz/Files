## Spring Root入门

本节将会循循渐进的介绍Spring Boot，通过创建第一个Spring Boot应用，来讨论其中核心的原则。

### Spring Boot介绍

Spring Boot使构建一个独立运行，产品级别的基于Spring框架的应用变得简单，大部分Spring Boot应用只需要非常少的配置就可以运行起来。

Spring Boot项目可以通过下面三种方式运行起来：

	- java -jar 包名
	- war 部署包
	- 命令行上运行Spring 脚本
	
Spring Boot包含的特性：

	创建可以独立运行的 Spring 应用。
    直接嵌入 Tomcat 或 Jetty 服务器，不需要部署 WAR 文件。
    提供推荐的基础 POM 文件来简化 Apache Maven 配置。
    尽可能的根据项目依赖来自动配置 Spring 框架。
    提供可以直接在生产环境中使用的功能，如性能指标、应用信息和应用健康检查。
    没有代码生成，也没有 XML 配置文件。

### 系统要求

Spring Boot 1.4.3.RELEASE版本需要的环境要求如下：

	Java 7及其以上
	Spring Framework 4.3.5.RELEASE及其以上
	Maven 3.2或Gradle（1.12或2.x）

下面的Servlet容器支持开箱即用，也可以将Spring Boot应用部署到任何兼容Servlet 3.0+的容器上。

|容器名称|Servlet版本|java版本|
|-|-|-|
|Tomcat 8|3.1|Java 7+|
|Tomcat 7|3.0|Java 6+|
|Jetty 9.3|3.1|Java 8+|
|Jetty 9.2|3.1|Java 7+|
|Jetty 8|3.0|Java 6+|
|Undertow 1.3|3.0|Java 7+|

### 安装Spring Boot




### 开发第一个Spring Boot应用



