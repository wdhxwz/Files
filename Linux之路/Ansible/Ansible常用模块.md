## Ansible常用模块

### 1.setup模块

	用来查看远程主机的一些基本信息
	ansible 192.168.1.16 -m setup

### 2.ping模块

	用来测试远程主机的运行状态
	ansible 192.168.1.16 -m ping

### 3.file模块

	相关选项如下：
	force：需要在两种情况下强制创建软链接，一种是源文件不存在，但之后会建立的情况下；另一种是目标软链接已存在，需要先取消之前的软链，然后创建新的软链，有两个选项：yes|no
	group：定义文件/目录的属组
	mode：定义文件/目录的权限
	owner：定义文件/目录的属主
	path：必选项，定义文件/目录的路径
	recurse：递归设置文件的属性，只对目录有效
	src：被链接的源文件路径，只应用于state=link的情况
	dest：被链接到的路径，只应用于state=link的情况
	state：
	       directory：如果目录不存在，就创建目录
	       file：即使文件不存在，也不会被创建
	       link：创建软链接
	       hard：创建硬链接
	       touch：如果文件不存在，则会创建一个新的文件，如果文件或目录已存在，则更新其最后修改时间
	       absent：删除目录、文件或者取消链接文件
	
	示例：
	远程文件符号链接创建：ansible 192.168.1.16 -m file -a "src=/etc/resolv.conf dest=/tmp/resolv.conf state=link"
	远程文件信息查看：ansible 192.168.1.16 -m command -a "ls -al  /data/apps"
	远程文件符号链接删除： ansible 192.168.1.16 -m file -a "path=/tmp/resolv.conf state=absent"

### 4.copy模块

	复制文件到远程主机
	相关选项如下：
	backup：在覆盖之前，将源文件备份，备份文件包含时间信息。有两个选项：yes|no
	content：用于替代“src”，可以直接设定指定文件的值
	dest：必选项。要将源文件复制到的远程主机的绝对路径，如果源文件是一个目录，那么该路径也必须是个目录
	directory_mode：递归设定目录的权限，默认为系统默认权限
	force：如果目标主机包含该文件，但内容不同，如果设置为yes，则强制覆盖，如果为no，则只有当目标主机的目标位置不存在该文件时，才复制。默认为yes
	others：所有的file模块里的选项都可以在这里使用
	src：被复制到远程主机的本地文件，可以是绝对路径，也可以是相对路径。如果路径是一个目录，它将递归复制。在这种情况下，如果路径使用“/”来结尾，则只复制目录里的内容，如果没有使用“/”来结尾，则包含目录在内的整个内容全部复制，类似于rsync。

	示例：
	将本地文件“/etc/ansible/ansible.cfg”复制到远程服务器：ansible 192.168.1.16 -m copy -a "src=/etc/ansible/ansible.cfg dest=/tmp/ansible.cfg owner=root group=root mode=0644"
	远程文件信息查看：ansible 192.168.1.16 -m command -a "ls -al /tmp/ansible.cfg"

### 5.command模块

	在远程主机上执行命令
	相关选项如下：
	creates：一个文件名，当该文件存在，则该命令不执行
	free_form：要执行的linux指令
	chdir：在执行指令之前，先切换到该目录
	removes：一个文件名，当该文件不存在，则该选项不执行
	executable：切换shell来执行指令，该执行路径必须是一个绝对路径

	示例：
	ansible 192.168.1.16 -m command -a "uptime"

### 6.shell 模块

	切换到某个shell执行指定的指令，参数与command相同。
	与command不同的是，此模块可以支持命令管道，同时还有另一个模块也具备此功能：raw
	
	示例：
	先在本地创建一个SHELL脚本
	vim /tmp/rocketzhang_test.sh
	#!/bin/sh
	date +%F_%H:%M:%S
	
	chmod +x /tmp/rocketzhang_test.sh
	
	将创建的脚本文件分发到远程
	ansible storm_cluster -m copy -a "src=/tmp/rocketzhang_test.sh dest=/tmp/rocketzhang_test.sh owner=root group=root mode=0755"
	
	远程执行
	ansible storm_cluster -m shell -a "/tmp/rocketzhang_test.sh"

### 7.其他模块

	service：系统服务管理
	cron：计划任务管理
	yum：yum软件包安装管理
	synchronize：使用rsync同步文件
	user：系统用户管理
	group：系统用户组管理




















