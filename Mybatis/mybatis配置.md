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

Mybatis支持配置多个dataSource环境，可以将应用部署到不同的环境上，如开发环境，测试环境等。

通过将default的值设置为environment的id即可进行环境切换。

如果应用需要连接多个数据库，需要将每个数据库配置成独立的环境，并且为每个数据库创建一个SqlSessionFactory。

	/**
	 * 获取数据库访问工厂
	 * 
	 * @return SqlSessionFactory
	 */
	public static SqlSessionFactory getSqlSessionFactory(String configFile, String enviroment) {
		SqlSessionFactory _sqlSessionFactory = null;
		InputStream inputStream;
		if (configFile == null || configFile.length() == 0) {
			configFile = "mybatisConfig.xml";
		}
		try {
			inputStream = Resources.getResourceAsStream(configFile);
			_sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, enviroment);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return _sqlSessionFactory;
	}

创建SqlSessionFactory时，如果没有明确指定环境environment，则使用默认的environment进行创建。

- 数据源DataSource

dataSource元素用来配置数据库连接属性：数据库地址，驱动类，账号和密码。

	<dataSource type="POOLED">
		<property name="driver" value="${jdbc.driverClassName}" />
		<property name="url" value="${jdbc.url}" />
		<property name="username" value="${jdbc.userName}" />
		<property name="password" value="${jdbc.password}" />
	</dataSource>

dataSource有3种类型：UNPOOLED，POOLED，JNDI
































































































































