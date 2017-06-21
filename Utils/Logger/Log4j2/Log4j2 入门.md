## Log4j2 入门

### 自动配置

配置时按下面顺序加载配置文件：

	* 1. log4j.configurationFile 系统变量
	* 2. classpath路径下：
	* log4j2-test.properties -> log4j2-test.yaml（log4j2-test.yml）->  
	* log4j2-test.json(log4j2-test.jsn) -> log4j2-test.xml -> 
	* log4j2.properties -> log4j2.yaml(log4j2.yml) -> 
	* log4j2.json(log4j2.jsn) -> log4j2.xml

### xml配置文件格式

	<?xml version="1.0" encoding="UTF-8"?>
	<Configuration>
		<Appenders>
			
		</Appenders>
		<Loggers>
			<logger name="" level=""/>
			<root level="">
				<appender-ref ref=""/>
			</root>
		</Loggers>
	</Configuration>


