## Common Logging概述

common-logging是apache提供的一个通用的日志接口。

common-logging会通过动态查找的机制，在程序运行时自动找出真正使用的日志库。

common-logging内部有一个Simple logger的简单实现，但是功能很弱。所以使用common-logging，通常都是配合着log4j来使用。使用它的好处就是，代码依赖于common-logging而非log4j， 避免了和具体的日志方案直接耦合，必要时，可以更改日志实现的第三方库。

### 动态查找原理

Log 是一个接口声明。LogFactory 的内部会去装载具体的日志系统，并获得实现该Log 接口的实现类。LogFactory 内部装载日志系统的流程如下：

- 首先，寻找org.apache.commons.logging.LogFactory 属性配置。
- 否则，利用JDK1.3 开始提供的service 发现机制，会扫描classpah 下的META-INF/services/org.apache.commons.logging.LogFactory文件，若找到则装载里面的配置，使用里面的配置。
- 否则，从Classpath 里寻找commons-logging.properties ，找到则根据里面的配置加载。
- 否则，使用默认的配置：如果能找到Log4j 则默认使用log4j 实现，如果没有则使用JDK14Logger 实现，再没有则使用commons-logging 内部提供的SimpleLog 实现。


从上述加载流程来看，只要引入了log4j 并在classpath 配置了log4j.xml ，则commons-logging 就会使log4j 使用正常，而代码里不需要依赖任何log4j 的代码。

### maven坐标
		<common.logging.version>1.2</common.logging.version>
		<!-- Common Logging -->
		<dependency>
		    <groupId>commons-logging</groupId>
		    <artifactId>commons-logging</artifactId>
		    <version>${common.logging.version}</version>
		</dependency>

### 代码示例

	private static Log log = LogFactory.getLog(CommonLoggingTest.class);

	@SuppressWarnings("null")
	public static void main(String[] args) {
		log.trace("trace");
		log.debug("debug");
		log.info("info");
		log.warn("warn");
		log.error("error");
		log.fatal("fatal");
		
		try{
			String string =null;
			string.length();
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
		}
	}

### 配置文件：commons-logging.properties

	# 配置使用哪种日志系统，可配置的值有：
	# org.apache.commons.logging.impl.Jdk14Logger　使用JDK1.4。
	# org.apache.commons.logging.impl.Log4JLogger　使用Log4J。
	# org.apache.commons.logging.impl.LogKitLogger　使用 avalon-Logkit。
	# org.apache.commons.logging.impl.SimpleLog　common-logging自带日志实现类。它实现了Log接口，把日志消息都输出到系统错误流System.err 中。 
	# org.apache.commons.logging.impl.NoOpLog　common-logging自带日志实现类。它实现了Log接口。 其输出日志的方法中不进行任何操作。
	org.apache.commons.logging.Log = org.apache.commons.logging.impl.NoOpLog

	# 配置日志记录器工厂
	org.apache.commons.logging.LogFactory = org.apache.commons.logging.impl.LogFactoryImpl