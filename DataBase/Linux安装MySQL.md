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
	mysql> set password for 'root'@'%' = password('1q2w#E$R');
	mysql> exit
搞定！

### 相关配置

- 目录
 
		1、数据库目录  
		/var/lib/mysql/
	
		2、配置文件
		/usr/share/mysql（mysql.server命令及配置文件）
	
		3、相关命令
		/usr/bin(mysqladmin mysqldump等命令)
	
		4、启动脚本
		/etc/rc.d/init.d/（启动脚本文件mysql的目录）

- 修改登录密码

		MySQL默认没有密码
		usr/bin/mysqladmin -u root password 'new-password'
		格式：mysqladmin -u用户名 -p旧密码 password 新密码

- 启动与停止
	
		MySQL安装完成后启动文件mysql在/etc/init.d目录下，在需要启动时运行下面命令即可
		启动：
			/etc/init.d/mysql start
		停止：
			/usr/bin/mysqladmin -u root -p shutdown
		重新启动：
			sudo /etc/init.d/mysql restart 
		自动启动：
			察看mysql是否在自动启动列表中 /sbin/chkconfig --list
			把MySQL添加到你系统的启动服务组里面去 /sbin/chkconfig --add mysql
			把MySQL从启动服务组里面删除 /sbin/chkconfig --del mysql

- 配置

		将/usr/share/mysql/my-medium.cnf复制到/etc/my.cnf，以后修改my.cnf文件来修改mysql的全局设置
		将my.cnf文件中的innodb_flush_log_at_trx_commit设成0来优化
		[mysqld]后添加添加lower_case_table_names设成1来不区分表名的大小写

- 设置字符集
		
		MySQL的默认编码是Latin1，不支持中文，要支持需要把数据库的默认编码修改为gbk或者utf8。
	
		1、中止MySQL服务（bin/mysqladmin -u root shutdown）
		2、在/etc/下找到my.cnf，如果没有就把MySQL的安装目录下的support-files目录下的my-medium.cnf复制到/etc/下并改名为my.cnf即可 
		3、打开my.cnf以后，在[client]和[mysqld]下面均加上default-character-set=utf8，保存并关闭
		4、启动MySQL服务（bin/mysqld_safe &）
	
		查询字符集：show variables like '%set%';	

- 增加MySQL用户
	
		格式：grant select on 数据库.* to 用户名@登录主机 identified by "密码"
			grant select,insert,update,delete on *.* to user_1@'%' Identified by '123';
			grant all on *.* to user_1@'localhost' Identified by '123';
	
- 远程访问

		　　其一：
		
		　　GRANT ALL PRIVILEGES ON *.* TO xoops_root@'%' IDENTIFIED BY '654321';
		
		　　允许xoops_root用户可以从任意机器上登入MySQL。
		
		　　其二：
		
		　　编辑 /etc/mysql/my.cnf
		
		　　>skip-networking => # skip-networking
		
		　　这样就可以允许其他机器访问MySQL了。
		   grant all on *.* to 'root'@'ip' identified by 'password'; 


- 备份与恢复
	
		备份
		进入到库目录，cd /val/lib/mysql
			mysqldump -u root -p --opt aaa > back_aaa
		恢复
			mysql -u root -p ccc < back_aaa
	

- 参考资料

		http://www.linuxidc.com/Linux/2013-01/78716p3.htm









