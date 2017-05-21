## daemontools工具

Daemontools是一个包含了很多管理Unix服务的工具的软件包。其中最核心的工具是supervise，它的功能是监控一个指定的服务，当该服务进程消亡，则重新启动该进程。

svscan工具是为指定的工作目录(缺省是/service/目录)下的所有子目录中的每一个子目录都启动一个supervise进程，最多可以启动多达1000个supervise进程。  
Svscan每5秒钟检测一次子目录，若出现新的目录则为该目录启动supervise，若某个老的子目录对应的supervise退出，则重新启动它。

### 安装daemontools

	mkdir /home/wangdh/package  目录随意
	chmod 755 /home/wangdh/package 
	cd /home/wangdh/package 

	wget http://cr.yp.to/daemontools/daemontools-0.76.tar.gz
	tar zxvf daemontools-0.76.tar.gz
	cd ./admin/daemontools-0.76

	vim ./src/error.h 找到：extern int errno; 改成：#include <errno.h>
	./package/install   需要root，只能是在/home/wangdh/package/admin/daemontools-0.76这层目录执行该命令进行安装

> ps -ef | grep svscan 确认是否安装成功   
> cat /etc/inittab

### 使用supervise监控进程

supervise的执行命令是supervise Path ，其中Path 是指定路径，可以是相对路径，也可以是绝对路径。在Path路径下，必须有一个名称为run的脚本，supervise调用的就是这个脚本，并监控管理该脚本中运行的程序。   
supervise的一个重要的功能就是可以检测出run脚本中执行的程序是否正常工作，若发现其已经死掉，supervise将会重新执行run脚本，重新启动指定程序。

### 监控zookeeper

	mkdir -p /data/service/zookeeper/2181
	cd /data/service/zookeeper/2181

	vim run

	run的内容如下：

	#!/bin/bash
	exec 2>&1
	exec /usr/local/zookeeper/2181/bin/zkServer.sh start
	
	chmod 755 run

	执行命令：supervise /data/service/zookeeper/2181
	或 nohup supervise /data/service/zookeeper/2181 & （后台进程启动）



