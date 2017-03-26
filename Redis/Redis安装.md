## Redis安装

	============== 安装 ===============
	1、下载：wget http://download.redis.io/releases/redis-3.2.4.tar.gz
	2、安装到目录：/usr/software/redis
	3、将下载的包复制到/usr/software/
	4、解压：tar -zxvf redis-3.2.4.tar.gz
	5、建立软连接：ln -s 源目录 redis
	6、安装：make PREFIX=/usr/software/redis install
	
	============== 将redis作为服务=========
	1、cp /usr/software/redis/utils/redis_init_script /etc/rc.d/init.d/redis
	2、vim /etc/rc.d/init.d/redis
	在前面添加：
	# chkconfig:   2345 90 10
	
	# description:  Redis is a persistent key-value database
	
	修改   
	EXEC=/usr/software/redis/bin/redis-server
	CLIEXEC=/usr/software/redis/bin/redis-cli
	
	$EXEC $CONF 后加&
	
	3、mkdir /etc/redis
	4、cp /usr/software/redis/redis.config /etc/redis/6379.conf
	5、chkconfig --add redis
	6、service redis start
	7、vim /etc/profile
	8、export PATH=$PATH:/usr/software/redis/bin
	
	=========测试===========
	redis-cli 
	auth superman
	ping