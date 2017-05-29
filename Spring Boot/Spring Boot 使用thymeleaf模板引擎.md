## Spring Boot 使用thymeleaf模板引擎

### 依赖包

	<!-- thymeleaf模板 -->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-thymeleaf</artifactId>
	</dependency>

### 默认配置项

	##############################################################
	# >>> Thymeleaf相关配置 <<<
	##############################################################
	# 模板存放的位置
	#spring.thymeleaf.prefix=classpath:/templates/
	
	# 后缀
	#spring.thymeleaf.suffix=.html
	
	# 模式
	#spring.thymeleaf.mode=HTML5
	
	# 编码方式
	#spring.thymeleaf.encoding=UTF-8
	
	# 文档内容类型
	#spring.thymeleaf.content-type=text/html
	
	# 是否开启缓存
	spring.thymeleaf.cache=false

### 编写模板

	<!DOCTYPE html>
	<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
	      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
	    <head>
	        <title>Hello World!</title>
	    </head>
	    <body>
	        <h1 th:inline="text">Hello.v.2 </h1>
	        <p th:text="${hello}"></p>
	    </body>
	</html>	

### 编写控制器

	@Controller
	@RequestMapping("/template")
	public class TemplateController {
	
		@RequestMapping("/thymeleaf")
		public String sayHello(Map<String, Object> map) {
			map.put("hello", "say hello from thymeleaf template");
	
			return "hello";
		}
	}

> thymeleaf和freemarker是可以共存的    
> thymeleaf是默认的模板引擎