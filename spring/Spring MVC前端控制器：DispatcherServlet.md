## 前端控制器：DispatcherServlet

DispatcherServlet是Spring MVC的核心，负责接收Http请求并协调Spring MVC各个组件以完成请求处理。  
了解Spring MVC框架的工作机制，需要回答下面三个问题：

1. DispatcherServlet如何捕获特定http请求，交由Spring MVC框架处理？
2. 位于web层的Spring容器(WebApplicationContext)如何与位于业务层的Spring容器(ApplicationContext)建立关联，使得web层的Bean可以调用业务层的Bean？
3. 如何初始化Spring MVC的各个组件，并且装配到DispatcherServlet中？

### DispatcherServlet配置

DispatcherServlet本质上是一个Servlet，所以可以在web.xml文件中配置处理的url模式，如：

	<!-- 配置Spring配置文件路径 ①-->
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath*:spring-root.xml</param-value>
	</context-param>
	
	<!-- spring mvc 分发器 ②-->
	<servlet>
		<servlet-name>spring-mvc</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet
		</servlet-class>
		<load-on-startup>1</load-on-startup>
		<!-- 配置文件，默認是：/WEB-INF/xxx-servlet.xml -->
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>classpath*:spring-mvc.xml</param-value>
		</init-param>
	</servlet>

	<!-- Spring MVC转发规则 ③-->
	<servlet-mapping>
		<servlet-name>spring-mvc</servlet-name>
		<url-pattern>*.do</url-pattern>
	</servlet-mapping>


- 	在①处，通过配置contextConfigLocation参数，指定业务层Spring容器的配置文件(多个用逗号分隔).
- 	在②处，配置DispatcherServlet，并指定web层Spring容器的配置文件(多个用逗号分隔).
- 	在③处配置DispatcherServlet匹配的url规则，此处所有.do结尾的url请求都会被捕获到。

> Remark：①：Spring容器可以设置父子关系，以实现解耦；子容器可以访问父容器的Bean，而父容器不能访问子容器的Bean；在这里web层的Spring容器作为业务层Spring容器的子容器。②：web.xml可以配置多个DispatcherServlet，以处理不同的请求。


#### DispatcherServlet配置参数

DispatcherServlet遵循“约定大于配置”的原则，默认情况下可以不需要额外配置。下面是常用的配置参数：

|参数名称|	说明|	默认值|
|:|:|:|
|namespace|	DispatcherServlet 对应的命名空间，用来构造Spring配置文件的路径	|<servlet-name>-servlet
|contextConfigLocation	|DispatcherServlet上下文对应的Spring配置文件有多个，配置该属性，用逗号分隔	||
|publishContext	|DispatcherServlet根据该属性觉得是否将WebApplicationContext发布到ServletContext的属性列表中，以便调用者可以访问到	|True|






















