## Log4j-内置Appender

### 输出日志到控制台

	### 输出信息到控制抬 ###
	log4j.appender.stdout = org.apache.log4j.ConsoleAppender
	log4j.appender.stdout.Target = System.out
	log4j.appender.stdout.Threshold = INFO
	log4j.appender.stdout.Encoding = UTF-8
	# 是否立即输出
	log4j.appender.stdout.immediateFlush = true
	# log4j.appender.stdout.Target = System.err
	#控制台输出需要配置layout属性
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

### 输出日志到文件

	# 输出日志到文件
	log4j.appender.file=org.apache.log4j.FileAppender
	# 配置文件路径
	log4j.appender.file.File=E://logs/htmlLayout.html
	# 日志记录级别
	log4j.appender.file.Threshold = DEBUG 
	# 日志的编码格式
	log4j.appender.stdout.Encoding = UTF-8
	# 是否追加文件内容
	log4j.appender.file.Append = true
	# 日志输出格式
	log4j.appender.file.layout=org.apache.log4j.HTMLLayout
	log4j.appender.file.layout.Title=HTML Layout Example
	log4j.appender.file.layout.LocationInfo=true

### 输出日志到按大小滚动文件

	# 输出日志到按大小滚动文件
	log4j.appender.rollingFile=org.apache.log4j.RollingFileAppender
	# 配置文件路径
	log4j.appender.rollingFile.File=E://logs/RollingFile.log
	# 日志记录级别
	log4j.appender.rollingFile.Threshold = DEBUG 
	# 日志文件的最大大小
	log4j.appender.rollingFile.MaxFileSize = 10KB
	# 日志文件最多备份数量
	log4j.appender.rollingFile.MaxBackupIndex = 100  
	# 日志的编码格式
	log4j.appender.rollingFile.Encoding = UTF-8
	# 是否追加文件内容
	log4j.appender.rollingFile.Append = true
	# 日志输出格式
	log4j.appender.rollingFile.layout=org.apache.log4j.PatternLayout
	log4j.appender.rollingFile.layout.ConversionPattern = [Tread:%t] [%-5p] %d{yyyy-MM-dd HH:mm:ss,SSS} [method]:%l [message]:%m%n

### 输出到按日期滚动的文件

	# 输出日志到按日期滚动文件
	log4j.appender.dailyRollingFile=org.apache.log4j.DailyRollingFileAppender
	# 配置文件路径
	log4j.appender.dailyRollingFile.File=E://logs/DailyRollingFile.log
	# 日志记录级别
	log4j.appender.dailyRollingFile.Threshold = DEBUG 
	# 日志文件的日期格式
	log4j.appender.dailyRollingFile.DatePattern = .yyyy-MM-dd
	# 日志的编码格式
	log4j.appender.dailyRollingFile.Encoding = UTF-8
	# 是否追加文件内容
	log4j.appender.dailyRollingFile.Append = true
	# 日志输出格式
	log4j.appender.dailyRollingFile.layout=org.apache.log4j.PatternLayout
	log4j.appender.dailyRollingFile.layout.ConversionPattern = [Tread:%t] [%-5p] %d{yyyy-MM-dd HH:mm:ss,SSS} [method]:%l [message]:%m%n

### 输出日志到数据库

	# 输出到数据库
	log4j.appender.DB=org.apache.log4j.jdbc.JDBCAppender
	# 数据库连接地址
	log4j.appender.DB.URL=jdbc:sqlserver://127.0.0.1:1433;databasename=MybatisDB;
	# 数据库驱动类
	log4j.appender.DB.driver=com.microsoft.sqlserver.jdbc.SQLServerDriver
	# 数据库用户名
	log4j.appender.DB.user=sa
	# 日志记录级别
	log4j.appender.DB.Threshold = DEBUG 
	# 数据库密码
	log4j.appender.DB.password=1q2w#E$R
	# 数据库执行语句
	log4j.appender.DB.sql=INSERT INTO tb_logs VALUES('%t','%d{yyyy-MM-dd HH:mm:ss.SSS}','%l','%p','%m')
	# 日志格式
	log4j.appender.DB.layout=org.apache.log4j.PatternLayout

### 输出日志到远程服务器

	# 输出到远程服务器
	log4j.appender.socket=org.apache.log4j.net.SocketAppender
	# 远程服务器地址，域名或IP
	log4j.appender.socket.RemoteHost=localhost
	# 远程服务器端口号
	log4j.appender.socket.Port=com.microsoft.sqlserver.jdbc.SQLServerDriver
	# 远程服务器超时重连时间间隔
	log4j.appender.socket.ReconnectionDelay=30000
	# 本地信息
	log4j.appender.socket.LocationInfo = true 
	# 日志格式
	log4j.appender.socket.layout=org.apache.log4j.PatternLayout

### 输出日志到邮件

	# 输出到邮件
	log4j.appender.mail=org.apache.log4j.net.SMTPAppender
	# 缓存文件大小，单位K，达到时发生邮件
	log4j.appender.mail.BufferSize=512
	# 发生邮件的服务器
	log4j.appender.mail.SMTPHost=smtp.qq.com
	# 邮件的标题
	log4j.appender.mail.Subject=
	# From邮箱的用户名
	log4j.appender.mail.SMTPUsername=
	# From邮箱的密码
	log4j.appender.mail.SMTPPassword = 
	# 发生人地址 
	log4j.appender.mail.From = 1366678737@qq.com 
	# 接收人地址
	log4j.appender.mail.To = 1366678737@qq.com 
	# 日志格式
	log4j.appender.mail.layout=org.apache.log4j.PatternLayout
	log4j.appender.mail.layout.ConversionPattern = [%t] [%-5p] %d{yyyy-MM-dd HH:mm:ss,SSS} [%l] [%m]%n

> SMTPAppender的默认级别是ERROR