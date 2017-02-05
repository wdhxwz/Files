### 具体的核心开发步骤

1. 在web.xml中配置DispatcherServlet，拦截请求到Spring MVC
2. 配置HandlerMapping，将请求映射到Controller
3. 配置HandlerAdapter，支持多种类型的Controller
4. 配置ViewResolver，进行视图的解析
5. 配置Controller，进行功能处理

### Spring MVC的优势

1. 角色划分清晰：前端控制器(DispatcherServlet)，请求到控制器映射(HandlerMapping)，控制器适配(HandlerAdapter)，视图解析器(ViewResolver)，控制器(Controller)，验证器(Validator)，命令对象(Command)，表单对象(Form Object)
2. 与Spring框架无缝集成
3. 功能强大的数据验证，格式化，绑定机制
4. 强大的JSP标签库，使JSP编写更容易

### Hello World 入门

1.配置前端控制器：DispatcherServlet

	<!-- spring mvc 分发器 -->
	<servlet>
		<servlet-name>spring-mvc</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<load-on-startup>1</load-on-startup>
		<!-- 配置文件，默認是：/WEB-INF/xxx-servlet.xml -->
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>classpath*:spring-mvc.xml</param-value>
		</init-param>
	</servlet>

	<servlet-mapping>
		<servlet-name>spring-mvc</servlet-name>
		<url-pattern>*.do</url-pattern>
	</servlet-mapping>

2. 配置HandlerMapping：启动控制器扫描


		<!-- 扫描 controller -->
		<context:component-scan base-package="com.wangdh.learner.springmvc.controller" />

3. 配置视图解析器

		<bean class="org.springframework.web.servlet.view.UrlBasedViewResolver"
			id="internalResourceViewResolver">
			<!-- 前缀 -->
			<property name="prefix" value="/WEB-INF/views/" />
			<!-- 后缀 -->
			<property name="suffix" value=".jsp" />
			<property name="viewClass"
				value="org.springframework.web.servlet.view.JstlView" />
		</bean>

### 详细的配置模板

- web.xml配置

		?xml version="1.0" encoding="UTF-8"?>
		<web-app version="2.5" xmlns="http://java.sun.com/xml/ns/j2ee"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance  http://www.springmodules.org/schema/cache/springmodules-cache.xsd http://www.springmodules.org/schema/cache/springmodules-ehcache.xsd"
			xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee      
		    http://java.sun.com/xml/ns/j2ee/web-app_2_5.xsd          
		     ">
			<display-name>Spring MVC</display-name>
			<!-- 配置Spring配置文件路径 -->
			<context-param>
				<param-name>contextConfigLocation</param-name>
				<param-value>classpath*:spring-root.xml</param-value>
			</context-param>
		
			<!-- 编码过滤器 -->
			<filter>
				<filter-name>Encoding</filter-name>
				<filter-class>
					org.springframework.web.filter.CharacterEncodingFilter
				</filter-class>
				<init-param>
					<param-name>encoding</param-name>
					<param-value>UTF-8</param-value>
				</init-param>
				<init-param>
					<param-name>forceEncoding</param-name>
					<param-value>true</param-value>
				</init-param>
			</filter>
			<filter-mapping>
				<filter-name>Encoding</filter-name>
				<url-pattern>/*</url-pattern>
			</filter-mapping>
		
			<!-- spring mvc 分发器 -->
			<servlet>
				<servlet-name>spring-mvc</servlet-name>
				<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
				<load-on-startup>1</load-on-startup>
				<!-- 配置文件，默認是：/WEB-INF/xxx-servlet.xml -->
				<init-param>
					<param-name>contextConfigLocation</param-name>
					<param-value>classpath*:spring-mvc.xml</param-value>
				</init-param>
			</servlet>
		
			<servlet-mapping>
				<servlet-name>spring-mvc</servlet-name>
				<url-pattern>*.do</url-pattern>
			</servlet-mapping>
		
			<session-config>
				<session-timeout>120</session-timeout>
			</session-config>
			<welcome-file-list>
				<welcome-file>/home/index.do</welcome-file>
			</welcome-file-list>
		</web-app>

- spring-mvc.xml配置

		<?xml version="1.0" encoding="UTF-8"?>
		<beans xmlns="http://www.springframework.org/schema/beans"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
			xmlns:mvc="http://www.springframework.org/schema/mvc"
			xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.1.xsd
		        http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc-4.1.xsd">
			<!-- 扫描 controller -->
			<context:component-scan base-package="com.wangdh.learner.springmvc.controller" />
			<context:component-scan base-package="com.wangdh.learner.springmvc.service" />
			<!-- 靜態資源不攔截 -->
			<mvc:resources location="/image/" mapping="/image/**" />
			<mvc:resources location="/css/" mapping="/css/**" />
			<mvc:resources location="/js/" mapping="/js/**" />
			<!-- 添加注解支持 -->
			<mvc:annotation-driven>
				<!-- 编码转换 -->
				<mvc:message-converters>
					<bean class="org.springframework.http.converter.StringHttpMessageConverter">
						<property name="supportedMediaTypes">
							<list>
								<value>text/plain;charset=UTF-8</value>
								<value>text/html;charset=UTF-8</value>
								<value>application/json;charset=UTF-8</value>
							</list>
						</property>
					</bean>
					<bean
						class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
						<property name="prettyPrint" value="true" />
					</bean>
				</mvc:message-converters>
			</mvc:annotation-driven>
			<bean class="org.springframework.web.servlet.view.UrlBasedViewResolver"
				id="internalResourceViewResolver">
				<!-- 前缀 -->
				<property name="prefix" value="/WEB-INF/views/" />
				<!-- 后缀 -->
				<property name="suffix" value=".jsp" />
				<property name="viewClass"
					value="org.springframework.web.servlet.view.JstlView" />
			</bean>
		
			<!-- 文件上传 -->
			<bean id="multipartResolver"
				class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
				<!-- 解析request的编码 ，Default is ISO-8859-1 -->
				<property name="defaultEncoding" value="UTF-8" />
				<!-- 设置最大允许的大小(字节)。-1表示没有限制(默认) 1024*1024*10=10MB -->
				<property name="maxUploadSize" value="1048576000" />
				<!--被允许的最大的内存的大小，Default is 10240 bytes -->
				<property name="maxInMemorySize" value="20480" />
				<!-- 一个类似懒加载的属性.可以定义该属性.让解析文件的时候再抛异常,然后Controller中定义异常处理的方法 -->
				<property name="resolveLazily" value="true" />
			</bean>
		</beans> 