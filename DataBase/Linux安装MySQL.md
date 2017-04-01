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














