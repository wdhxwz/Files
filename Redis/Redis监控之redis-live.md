## Redis监控之redis-live

一个用来监控redis实例，分析查询语句并且有web界面的监控工具，python编写。可以同时监控多个redis实例，做集中监控。

### 环境依赖

	1. python 2.7 及以上
	2. sqlite3模块
	2. python软件管理器：pip
	3. tornado 
	4. redis.py
	5. python-dateutil
	6. argparse(python版本小于2.7)


###  安装sqlite3

	wget http://www.sqlite.org/sqlite-amalgamation-3.6.20.tar.gz
	tar zxvf  sqlite-amalgamation-3.6.20.tar.gz
	cd  sqlite-3.6.20
	./configure –prefix=/usr/local/lib/sqlite3
	make
	make install  

### 升级python到2.7.8

	wget http://python.org/ftp/python/2.7.8/Python-2.7.8.tgz
	tar xvf Python-2.7.8.tar.bz2 
	cd Python-2.7.8
	vim setup.py
	
	在下面这段的下一行添加’/usr/local/lib/sqlite3/include’

	sqlite_inc_paths = [ ‘/usr/include’,
                             ‘/usr/include/sqlite’,
                             ‘/usr/include/sqlite3′,
                             ‘/usr/local/include’,
                             ‘/usr/local/include/sqlite’,
                             ‘/usr/local/include/sqlite3′,
                             ‘/usr/local/lib/sqlite3/include’,


	./configure --prefix=/usr/local/python27
	make
	make install

	mv /usr/bin/python /usr/bin/python_old
	ln -s /usr/local/python27/bin/python /usr/bin/
	vim /usr/bin/yum  // #!/usr/bin/python   --> #!/usr/bin/python_old

### 安装python软件管理包：pip

	wget https://bootstrap.pypa.io/get-pip.py
	python get-pip.py

### 安装redis-live依赖

	pip install tornado
	pip install redis
	pip install python-dateutil
	pip install argparse

### 下载redis-live,配置，启动

	wget https://codeload.github.com/nkrode/RedisLive/legacy.zip/master
	mv master redis-live.zip
	unzip redis-live.zip
	cd nkrode-RedisLive-6debcb4/src
	
	mv redis-live.conf.example redis-live.conf

	配置文件内容如下：

	{
		// 需要监控的redis服务器列表
		"RedisServers":
		[ 
			{
	  			"server": "154.17.59.99",
	  			"port" : 6379
			},		
			{
	  			"server": "localhost",
	  			"port" : 6380,
	  			"password" : "some-password"
			}		
		],
	
		// 监控数据存储的类型：redis或sqlite
		"DataStoreType" : "redis",
	
		// 监控数据存储的redis服务器
		"RedisStatsServer":
		{
			"server" : "ec2-184-72-166-144.compute-1.amazonaws.com",
			"port" : 6385
		},
		
		// 监控数据存储的路径
		"SqliteStatsStore" :
		{
			"path":  "to your sql lite file"
		}
	}

	###启动监控服务，每30秒监控一次
	./redis-monitor.py --duration=30 

	###再次开启一个终端，启动web服务
	./redis-live.py
	
	###访问
	http://ip:8888/index.html


效果图

![](http://i.imgur.com/KwotTrO.png)


> 参考资料：http://www.nkrode.com/article/real-time-dashboard-for-redis  
> http://blog.csdn.net/olanlanxiari/article/details/48086917