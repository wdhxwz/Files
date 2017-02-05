## Log4j概述

Log4j是Apache的一个开源项目。有三个主要组成部分：

- 日志记录器：Logger，负责记录日志
- 输出地：Appender，负责输出日志到指定地方
- 日志格式化器：Layout，负责以什么格式输出日志，输出哪些信息(时间，类名，方法，所在行数等)

### 日志记录器：Logger

日志记录器Logger便是Java代码中的Logger，如：
	
	/**
	 * Log4j日志记录器
	 */
	private static Logger logger = Logger.getLogger(Log4jTest.class);

Logger是有名字的，名字就是Logger.getLogger()方法的参数。

- Logger是单例模式：相同名字的Logger只会有一个实例
- 命名规则：一般以类名作为Logger的名称
- Log4j有一个根记录器rootLogger，是所有Logger的父亲

### Log4j配置：记录器配置

log4j.logger.[loggerName] 配置Logger，需要指定日志级别和Appender  
log4j.appender.[appenderName] 配置Appender  
log4j.rootLogger 配置根记录器，任何记录器都会继承根记录器的配置
log4j.category.[categoryName] 配置类别，只作用于该类别下的记录器，相当于Java的package

	### 配置Logger ###
	log4j.logger.com.wangdh.learner.logger.Log4jTest = DEBUG,stdout
	
	### 输出信息到控制抬 ###
	log4j.appender.stdout = org.apache.log4j.ConsoleAppender
	log4j.appender.stdout.Target = System.out
	log4j.appender.stdout.layout = org.apache.log4j.PatternLayout
	
	# %c 输出类名(完全限定名)
	# %c{1} 只输出类名
	# %d{yyyy-MM-dd HH:mm:ss,SSS} 输出日志记录时间
	# %l 输出日志调用的位置(哪个类的哪行代码)
	# %L 输出日志调用的代码行
	# %m 输出日志的消息
	# %M 输出日志的调用方法名(非完全限定)
	# %p 输出日志的记录级别：DEBUG,INFO等
	# %-5p 输出长度小于20,则右边用空格填充
	# %5p 输出长度小于20,则左边用空格填充
	# %n 换行符
	# %r 显示从程序启动时到记录该条日志时已经经过的毫秒数
	# %t 输出产生该日志事件的线程名
	# %% 显示一个百分号
	log4j.appender.stdout.layout.ConversionPattern = [Tread:%t] [%-5p] %d{yyyy-MM-dd HH:mm:ss,SSS} [method]:%l [message]:%m%n

> 配置了根Logger又配置了指定名称的Logger，则在使用指定名称的Logger时，根Logger也会被执行(相同的配置会执行两遍)

### 日志输出地：Appender

Appender表示日志要输出到什么地方，常用的输出地有：控制台，文件，数据库，远程服务器等。  
log4j内置的输出地有： 
 
	org.apache.log4j.ConsoleAppender:输出到控制台
	org.apache.log4j.FileAppender:输出到文件
	org.apache.log4j.DailyRollingFileAppender:输出到按日期滚动文件
	org.apache.log4j.RollingFileAppender:输出到按大小滚动文件
	org.apache.log4j.jdbc.JDBCAppender:输出到JDBC数据库
	org.apache.log4j.net.SocketAppender:输出到远程服务器
	org.apache.log4j.net.SMTPAppender : 输出到邮件
	org.apache.log4j.nt.NTEventLogAppender:输出到Windows NT系统日志


### 日志格式化器：Layout

日志格式化器Layout负责格式化日志信息。  
log4j内置的常用的Layout有：

	org.apache.log4j.HTMLLayout:将日志格式化成HTML代码
	org.apache.log4j.PatternLayout:最常用的格式化器，可自定义输出信息
	org.apache.log4j.xml.XMLLayout:将日志格式化成xml文件

### 日志记录级别：Log Level

log4j支持的日志记录级别定义在：org.apache.log4j.Level类型，具体的类别有，从上往下级别依次增高：

	ALL：所有级别，包括自定义级别
	TRACE：比DEBUG更低的信息事件
	DEBUG：应用程序调试
	INFO：应用程序运行情况
	WARN：具有潜在危害的情况
	ERROR：错误事件可能仍然允许应用程序继续运行
	FATAL：非常严重的错误事件，这可能导致应用程序中止
	OFF：最高等级，为了关闭日志记录

> 高级别会屏蔽低级别的日志记录

