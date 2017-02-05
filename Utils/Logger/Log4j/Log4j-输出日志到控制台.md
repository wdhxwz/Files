## Log4j-输出日志到控制台

	### 输出信息到控制抬 ###
	log4j.appender.stdout = org.apache.log4j.ConsoleAppender
	log4j.appender.stdout.Target = System.out
	log4j.appender.stdout.Threshold = INFO
	log4j.appender.stdout.Encoding = UTF-8
	# 是否立即输出
	log4j.appender.stdout.immediateFlush = true
	# log4j.appender.stdout.Target = System.err
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

控制台输出需要配置layout属性