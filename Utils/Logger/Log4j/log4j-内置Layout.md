## log4j-内置Layout

### PatternLayout布局

	org.apache.log4j.PatternLayout
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
	layout.ConversionPattern = [%t] [%-5p] %d{yyyy-MM-dd HH:mm:ss,SSS} [%l] [%m]%n

### HtmlLayout布局

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
	# html文件标题
	log4j.appender.file.layout.Title=HTML Layout Example
	# 记录本地信息
	log4j.appender.file.layout.LocationInfo=true

> 该布局适用于文件输出

### XmlLayout布局

	### 输出xml格式的日志 ###
	log4j.appender.xml = org.apache.log4j.FileAppender
	log4j.appender.xml.File = E://logs/logs.xml
	log4j.appender.xml.Encoding = UTF-8
	log4j.appender.xml.Append = true
	log4j.appender.xml.layout = org.apache.log4j.xml.XMLLayout

> 同样只适用于文件输出