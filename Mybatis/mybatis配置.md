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

### mybatis XML配置元素及顺序

Maybatis的Xml配置文件包含一些设置和属性，用于增强Mybatis的动作，配置文件的层次结构如下：

	configuration	
		|-- properties
			|-- property
		|-- settings
			|-- setting
		|-- typeAliases
			|-- typeAlias
		|-- typeHandlers
			|-- typeHandler
		|-- objectFactory
		|-- plugins
			|-- plugin
		|-- environments
			|-- environment
				|-- transactionManager
				|-- dataSource
		|-- mappers
			|-- mapper


上面的配置元素可以缺失，但是顺序只能按照上面的位置排列

### Mybatis配置元素介绍

根据上面列举的配置元素顺序来介绍每个配置元素

- 1、属性：properties配置

该配置用于读取java属性文件的配置内容，如：

		<!-- 定义属性，通过resource属性引入外部配置文件 -->
		<properties resource="jdbc.properties">
			<property name="username" value="db_user" />
			<property name="password" value="verysecurepwd" />
		</properties>

在需要使用到配置的地方，通过${属性名}可以获取对应的配置值。如果一个属性项在多个地方出现，mybatis将会按照下面的顺序加载：
	
	属性文件中的配置项(property配置不会覆盖属性文件配置)
	类路径或url资源读取的属性项
	方法体内的参数值

- 2、设置：settings配置

修改mybatis操作运行过程细节的重要配置，下面是一些常用的配置：

|设置参数|描述|有效值|默认值|
|-|-|-|-|
|cacheEnabled|该配置影响的所有映射器中配置的缓存的全局开关|true，false|true|
|lazyLoadingEnabled|延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。 特定关联关系中可通过设置fetchType属性来覆盖该项的开关状态|	true，false|false|
|aggressiveLazyLoading|When enabled, any method call will load all the lazy properties of the object. Otherwise, each property is loaded on demand (see also lazyLoadTriggerMethods)|true ，false|	false (true in ≤3.4.1)|
|multipleResultSetsEnabled|是否允许单一语句返回多结果集（需要兼容驱动）|true， false|true|
|useColumnLabel|使用列标签代替列名。不同的驱动在这方面会有不同的表现， 具体可参考相关驱动文档或通过测试这两种不同的模式来观察所用驱动的结果。|true ， false|true|
|useGeneratedKeys|允许 JDBC 支持自动生成主键，需要驱动兼容。 如果设置为 true 则这个设置强制使用自动生成主键，尽管一些驱动不能兼容但仍可正常工作（比如 Derby）。|	true ， false|False
|autoMappingBehavior|指定 MyBatis 应如何自动映射列到字段或属性。 NONE 表示取消自动映射；PARTIAL 只会自动映射没有定义嵌套结果集映射的结果集。 FULL 会自动映射任意复杂的结果集（无论是否嵌套）。|	NONE, PARTIAL, FULL |	PARTIAL|
|autoMappingUnknownColumnBehavior|Specify the behavior when detects an unknown column (or unknown property type) of automatic mapping target.NONE: Do nothingWARNING: Output warning log (The log level of 'org.apache.ibatis.session.AutoMappingUnknownColumnBehavior' must be set to WARN)FAILING: Fail mapping (Throw SqlSessionException)|NONE, WARNING, FAILING，NONE|
|defaultExecutorType|配置默认的执行器。SIMPLE 就是普通的执行器；REUSE 执行器会重用预处理语句（prepared statements）； BATCH 执行器将重用语句并执行批量更新。|SIMPLE， REUSE， BATCH|SIMPLE
|defaultStatementTimeout|设置超时时间，它决定驱动等待数据库响应的秒数。|	任意正整数|	Not Set (null)|
|defaultFetchSize|	为驱动的结果集获取数量（fetchSize）设置一个提示值。此参数只可以在查询设置中被覆盖。	|任意正整数|Not Set (null)|
|safeRowBoundsEnabled	|允许在嵌套语句中使用分页（RowBounds）。 If allow, set the false.	|true ， false	|False|
|safeResultHandlerEnabled	|允许在嵌套语句中使用分页（ResultHandler）。 If allow, set the false.	|true ， false|	True|
|mapUnderscoreToCamelCase	|是否开启自动驼峰命名规则（camel case）映射，即从经典数据库列名 A_COLUMN 到经典 Java 属性名 aColumn 的类似映射。|true ， false|False|
|localCacheScope	|MyBatis 利用本地缓存机制（Local Cache）防止循环引用（circular references）和加速重复嵌套查询。 默认值为 SESSION，这种情况下会缓存一个会话中执行的所有查询。 若设置值为 STATEMENT，本地会话仅用在语句执行上，对相同 SqlSession 的不同调用将不会共享数据。|	SESSION ， STATEMENT|	SESSION|
|jdbcTypeForNull|	当没有为参数提供特定的 JDBC 类型时，为空值指定 JDBC 类型。 某些驱动需要指定列的 JDBC 类型，多数情况直接用一般类型即可，比如 NULL、VARCHAR 或 OTHER。	|JdbcType enumeration. |Most common are: NULL, VARCHAR and OTHER	OTHER|
|lazyLoadTriggerMethods|	指定哪个对象的方法触发一次延迟加载。	|A method name list separated by commas	|equals,clone,hashCode,toString|
|defaultScriptingLanguage	|指定动态 SQL 生成的默认语言。|	A type alias or fully qualified class name.	|org.apache.ibatis.scripting.xmltags.XMLLanguageDriver|
|callSettersOnNulls	|指定当结果集中值为 null 的时候是否调用映射对象的 setter（map 对象时为 put）方法，这对于有 Map.keySet() 依赖或 null 值初始化的时候是有用的。注意基本类型（int、boolean等）是不能设置成 null 的。|	true ， false|	false|
|returnInstanceForEmptyRow	|MyBatis, by default, returns null when all the columns of a returned row are NULL. When this setting is enabled, MyBatis returns an empty instance instead. Note that it is also applied to nested results (i.e. collectioin and association). Since: 3.4.2	|true ， false|false|
|logPrefix	|指定 MyBatis 增加到日志名称的前缀。|	Any String|	Not set|
|logImpl	|指定 MyBatis 所用日志的具体实现，未指定时将自动查找。|	SLF4J，LOG4J ， LOG4J2 ，JDK_LOGGING， COMMONS_LOGGING， STDOUT_LOGGING ， NO_LOGGING	|Not set|
|proxyFactory|指定 Mybatis 创建具有延迟加载能力的对象所用到的代理工具。|CGLIB， JAVASSIST|JAVASSIST (MyBatis 3.3 or above)|
|vfsImpl	|指定VFS的实现|	自定义VFS的实现的类全限定名，以逗号分隔。|	Not set|
|useActualParamName	|允许使用方法签名中的名称作为语句参数名称。 为了使用该特性，你的工程必须采用Java 8编译，并且加上-parameters选项。（从3.4.1开始）|	true ， false	|true|

- 3、类型别名：typeAliases

用于为java类型设置一个较短的名字，以消除完全限定名的冗余.
	
	<typeAliases>
		<!--这里给实体类取别名，方便在mapper配置文件中使用 -->
		<typeAlias alias="Film" type="com.wangdh.mybatis.models.Film" /> 
		<package name="com.mybatis.model" />
	</typeAliases>

也可以指定一个包名，mybatis会自动扫描需要的Java bean，这种情况下，当该bean没有注解时，会使用首字母小写的非限定名作为别名。

- 4、类型句柄：typeHandlers

当Mybatis对PreparedStatement设置一个参数或者从ResultSet返回一个值得时候，类型句柄被用来将值转化为相匹配的java类型，mybatis内置的类型句柄位于org.apache.ibatis.type包下。

	<typeHandlers>
		<typeHandler javaType="" jdbcType="" handler=""/>
	</typeHandlers>





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
































































































































