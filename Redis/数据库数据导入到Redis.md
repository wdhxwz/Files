## 数据库数据导入到Redis

通过管道传输pipe将数据库数据批量导入Redis；自Redis 2.6以上版本起，Redis支持快速大批量导入数据，即官网的Redis Mass Insertion,即Pipe传输，通过将要导入的命令转换为Resp格式，然后通过MySQL的concat()来整理出最终导入的命令集合，以达到快速导入的目的。 


### 数据库表与Redis数据结构的映射

每行数据库记录，存成redis的hash结构，每个列是hash的field，hash的key为=>表名:主键值；


### Redis协议

	*<args><cr><lf> 参数个数
	$<len><cr><lf> 第一个参数长度
	<arg0><cr><lf> 第一个参数
	$<len><cr><lf> 第一个参数值的长度
	<val0><cr><lf> 第一个参数值
	... ...

### 实战

表结构

	CREATE TABLE `udp_command` (
	  `COMMAND_ID` int(5) NOT NULL AUTO_INCREMENT,
	  `MESSAGE_TYPE` int(2) DEFAULT NULL,
	  `COMMAND` int(2) DEFAULT NULL,
	  `DESCRIPTION` varchar(100) DEFAULT NULL,
	  `HANDLER_NAME` varchar(50) DEFAULT NULL COMMENT '命令处理类在spring容器中的id',
	  PRIMARY KEY (`COMMAND_ID`)
	) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

对应的redis协议命令：

	SELECT CONCAT(
	   "*12\r\n",
	   '$',LENGTH(redis_cmd),'\r\n',redis_cmd,'\r\n',
	   '$',LENGTH(redis_key),'\r\n',redis_key,'\r\n',
	   '$',LENGTH(hkey1),'\r\n',hkey1,'\r\n','$',LENGTH(hval1),'\r\n',hval1,'\r\n',
	   '$',LENGTH(hkey2),'\r\n',hkey2,'\r\n','$',LENGTH(hval2),'\r\n',hval2,'\r\n',
	   '$',LENGTH(hkey3),'\r\n',hkey3,'\r\n','$',LENGTH(hval3),'\r\n',hval3,'\r\n',
	   '$',LENGTH(hkey4),'\r\n',hkey4,'\r\n','$',LENGTH(hval4),'\r\n',hval4,'\r\n',
	   '$',LENGTH(hkey5),'\r\n',hkey5,'\r\n','$',LENGTH(hval5),'\r\n',hval5,'\r'
	)FROM(
	   SELECT 'HMSET' AS redis_cmd,
	   concat_ws(':','udp_command', COMMAND_ID) AS redis_key,
	   'id' AS hkey1, COMMAND_ID AS hval1,
	   'message_type' AS hkey2, MESSAGE_TYPE AS hval2,
	   'command' AS hkey3, COMMAND AS hval3,
	   'description' AS hkey4, DESCRIPTION AS hval4,
	   'handler_name' AS hkey5, HANDLER_NAME AS hval5
	   From udp_command
	)AS t

这里的长度12 = 2 * (列数(5) + key)

将上面的SQL脚本保存成tableName_to_redis.sql,然后执行下面语句：

	mysql -h 10.10.3.218 -uroot -p123456 -Dtest_db --skip-column-names --raw </usr/local/redis.sql |redis-cli --pipe
	
	–raw: 使mysql不转换字段值中的换行符。
	–skip-column-names: 使mysql输出的每行中不包含列名。


### 自动生成redis命令的小程序

