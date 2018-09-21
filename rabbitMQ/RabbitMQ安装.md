#### Windows下安装

- 下载安装包

	下载地址：

	>[http://www.rabbitmq.com/releases/rabbitmq-server/v3.6.6/rabbitmq-server-3.6.6.exe](http://www.rabbitmq.com/releases/rabbitmq-server/v3.6.6/rabbitmq-server-3.6.6.exe)
	
	>[https://github.com/rabbitmq/rabbitmq-server/releases/download/rabbitmq_v3_6_6/rabbitmq-server-3.6.6.exe](https://github.com/rabbitmq/rabbitmq-server/releases/download/rabbitmq_v3_6_6/rabbitmq-server-3.6.6.exe)

	备注：安装RabbitMQ之前，需要先安装[Erlang VM](http://www.erlang.org/downloads)，因为RabbitMQ是使用该语言进行编写的。

- 运行RabbitMQ服务

	在windows下的安装只需下一步，下一步。默认是自动启动的，可以通过开始菜单进行stop/reinstall/start RabbitMQ服务

#### linux下安装

- 安装包下载

	[RPM格式](https://github.com/rabbitmq/rabbitmq-server/releases/download/rabbitmq_v3_6_6/rabbitmq-server-3.6.6-1.el6.noarch.rpm)

- 安装RabbitMQ

	在安装RabbitMQ之前，需要先安装Erlang虚拟机
		
		1.开启EPEL(yum的一个软件源)
			su -c 'rpm -Uvh http://download.fedoraproject.org/pub/epel/6/i386/epel-release-6-8.noarch.rpm'
			su -c 'yum install foo'  
		2.开启Erlang仓储		
			wget -O /etc/yum.repos.d/epel-erlang.repo http://repos.fedorapeople.org/repos/peter/erlang/epel-erlang.repo  
		3.安装Erlang
			yum install erlang  
		4.安装rabbitmq-server
			rpm --import http://www.rabbitmq.com/rabbitmq-signing-key-public.asc  
			yum install rabbitmq-server-3.2.3-1.noarch.rpm(注：要下载下来)  
		5.安装RabbitMQ Web管理插件	
			rabbitmq-plugins enable rabbitmq_management  
			service rabbitmq-server restart  


- 学习资料

>[http://blog.csdn.net/mlks_2008/article/details/18988301](http://blog.csdn.net/mlks_2008/article/details/18988301)

>[http://jingyan.baidu.com/article/e4d08ffdb3ff090fd2f60d28.html](http://jingyan.baidu.com/article/e4d08ffdb3ff090fd2f60d28.html)

>[http://www.rabbitmq.com/install-rpm.html](http://www.rabbitmq.com/install-rpm.html)


### Ubuntu安装rabbitmq

由于rabbitMq需要erlang语言的支持，在安装rabbitMq之前需要安装erlang，执行命令：

sudo apt-get install erlang-nox 
  
安装rabbitMq命令：   
2.$ sudo apt-get update  
3.$ sudo apt-get install rabbitmq-server

启动、停止、重启、状态rabbitMq命令：   

启动：sudo rabbitmq-server start   
关闭： sudo rabbitmq-server stop   
重启： sudo rabbitmq-server restart   
查看状态：sudo rabbitmqctl status  
 

docker run -d --name rabbitmq --publish 15672:15672 rabbitmq:management



iptables -t nat -A  DOCKER -p tcp --dport 8001 -j DNAT --to-destination 172.17.0.19:8000


https://www.jb51.net/article/127630.htm