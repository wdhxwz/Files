## Spring Boot 集成Jsp

Spring Boot支持一下类型的模板：

	1,FreeMarker
	2，Groovy
	3，Thymeleaf （Spring 官网使用这个）
	4，Velocity
	5，JSP （貌似Spring Boot官方不推荐)

### 添加依赖包

	<!-- jsp支持 -->
	<dependency>
		<groupId>javax.servlet</groupId>
		<artifactId>javax.servlet-api</artifactId>
	</dependency>
	<dependency>
		<groupId>javax.servlet</groupId>
		<artifactId>jstl</artifactId>
	</dependency>

	<!-- tomcat 的支持. -->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-tomcat</artifactId>
	</dependency>
	<dependency>
		<groupId>org.apache.tomcat.embed</groupId>
		<artifactId>tomcat-embed-jasper</artifactId>
	</dependency>

### 配置：application.properties

	########################################################
	# >>> 支持jsp相关配置 <<<
	########################################################
	# 页面默认前缀目录
	spring.mvc.view.prefix=/WEB-INF/jsp/
	# 响应页面默认后缀
	spring.mvc.view.suffix=.jsp

### 页面存放

在 src/main 下面创建 webapp/WEB-INF/jsp 目录用来存放我们的jsp页面(Spring Mvc一致的结构)

不能出现其他模板的依赖













