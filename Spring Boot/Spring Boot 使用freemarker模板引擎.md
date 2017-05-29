## Spring Boot 使用freemarker模板引擎

### 依赖项

	<!-- freemarker模板依赖 -->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-freemarker</artifactId>
	</dependency>

### 默认配置项

	########################################################
	# >>> FreeMarker相关配置 <<<
	########################################################
	spring.freemarker.allow-request-override=false
	spring.freemarker.cache=false
	spring.freemarker.check-template-location=true
	spring.freemarker.charset=UTF-8
	spring.freemarker.content-type=text/html
	spring.freemarker.expose-request-attributes=false
	spring.freemarker.expose-session-attributes=false
	spring.freemarker.expose-spring-macro-helpers=false
	#spring.freemarker.prefix=classpath:/templates/
	#spring.freemarker.suffix=.ftl
	#spring.freemarker.request-context-attribute=
	#spring.freemarker.settings.*=
	#spring.freemarker.template-loader-path=classpath:/templates/#comma-separatedlist
	#spring.freemarker.view-names= #whitelistofviewnamesthatcanberesolved

### 编写模板

	<!DOCTYPE html>
	<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
	      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
	    <head>
	        <title>Hello World!</title>
	    </head>
	    <body>
	        <h1>Hello.v.3</h1>
	        <p>${hello}</p>
	    </body>
	</html>

### 编写控制器层

	@RequestMapping("/helloFtl")
    public String helloFtl(Map<String,Object> map){
       map.put("hello","from TemplateController.helloFtl");
       
       return "helloFtl";
    }


> thymeleaf和freemarker是可以共存的





