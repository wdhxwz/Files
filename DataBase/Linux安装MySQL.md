## Linux安装MySQL

- 查看是否已安装mysql

		yum list installed | grep mysql

- 若已经安装了，使用下面命令进行卸载

		yum -y remove mysql-libs.x86_64

- 查看yum库上的mysql版本信息

		yum list | grep mysql 或 yum -y list mysql*


- 使用yum安装mysql数据库

		yum -y install mysql-server mysql mysql-devel


- 兴建mysql用户组和用户

		useradd mysql
		passwd mysql
		groupadd mysql


- 更改目录权限

		chgrp -R mysql /var/lib/mysql
		chmod -R 770 /var/lib/mysql

- 启动MySQL服务端

		service mysqld start 


### Centos7 安装mysql

CentOS 7的yum源中貌似没有正常安装mysql时的mysql-sever文件，需要去官网上下载
 	
	# wget http://dev.mysql.com/get/mysql-community-release-el7-5.noarch.rpm
	# rpm -ivh mysql-community-release-el7-5.noarch.rpm
	# yum install mysql-community-server

成功安装之后重启mysql服务
 	
	# service mysqld restart

初次安装mysql是root账户是没有密码的
设置密码的方法
 	
	# mysql -u root
	mysql> set password for ‘root’@‘localhost’ = password('mypasswd');
	mysql> exit
搞定！












