### Mybatis配置

MyBatis 最关键的组成部分是 SqlSessionFactory，可以从中获取 SqlSession，并执行映射的 SQL 语句。

SqlSessionFactory 对象可以通过基于XML的配置信息或者 Java API 创建。

#### XML配置Mybatis

构建SqlSessionFactory最常见的方式是基于XML配置。下面mybatis的配置样例：

	<?xml version="1.0" encoding="utf-8"?>
	<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
	"http://mybatis.org/dtd/mybatis-3-config.dtd">
	<configuration>
		<!-- 定义属性，通过resource属性引入外部配置文件 -->
		<properties resource="application.properties">
			<property name="username" value="db_user" />
			<property name="password" value="verysecurepwd" />
		</properties>
		<！-- mybatis的全部配置 -->
		<settings>
			<setting name="cacheEnabled" value="true" />
		</settings>
		<!-- 给类型配置别名 -->
		<typeAliases>
			<typeAlias alias="Tutor" type="com.mybatis3.domain.Tutor" />
			<package name="com.mybatis3.domain" />
		</typeAliases>
		<!-- 配置数据类型处理器 -->
		<typeHandlers>
			<typeHandler handler="com.mybatis3.typehandlers. PhoneTypeHandler" />
			<package name="com.mybatis3.typehandlers" />
		</typeHandlers>
		<!-- 配置数据库环境 -->
		<environments default="development">
			<environment id="development">
				<transactionManager type="JDBC" />
				<dataSource type="POOLED">
					<property name="driver" value="${jdbc.driverClassName}" />
					<property name="url" value="${jdbc.url}" />
					<property name="username" value="${jdbc.username}" />
					<property name="password" value="${jdbc.password}" />
				</dataSource>
			</environment>
			<environment id="production">
				<transactionManager type="MANAGED" />
				<dataSource type="JNDI">
					<property name="data_source" value="java:comp/jdbc/MyBatisDemoDS" />
				</dataSource>
			</environment>
		</environments>
		<!-- 配置mapper xml文件 -->
		<mappers>
			<mapper resource="com/mybatis3/mappers/StudentMapper.xml" />32
			<mapper url="file:///D:/mybatisdemo/mappers/TutorMapper.xml" />
			<mapper class="com.mybatis3.mappers.TutorMapper" />
		</mappers>
	</configuration>

- environment配置节






































































































































