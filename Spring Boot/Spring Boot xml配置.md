## Spring Boot xml配置

 Spring Boot理念就是零配置编程，但是如果需要使用XML的配置，建议仍旧从一个@Configuration类开始，也可以使用@ImportResouce注解加载XML配置文件。

	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans"
	    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	    xsi:schemaLocation="http://www.springframework.org/schema/beans 
	    http://www.springframework.org/schema/beans/spring-beans.xsd">
	   
	    <!-- 注入spring boot无法扫描到的bean -->
	    <bean id="helloService" class="com.spring.boot.demo.service.HelloService"></bean>
	 
	</beans>


	@SpringBootApplication
	@ServletComponentScan
	@ImportResource(locations = { "classpath:application-bean.xml" })
	public class App {
		/**
		 * 程序扫描的包默认是执行类所在包及其子包
		 */
		public static void main(String[] args) {
			SpringApplication.run(App.class, args);
		}
	}

### 其他说明

  ImportResouce有两种常用的引入方式：classpath和file：
	
	classpath路径：locations={"classpath:application-bean1.xml","classpath:application-bean2.xml"}

	file路径：
	locations = {"file:d:/test/application-bean1.xml"};