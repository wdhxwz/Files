## Docker安装

### 前置条件

Docker要求的条件如下：

- 运行64位CPU架构的计算机
- 运行Linux3.8或更高版本内核(一些老版本2.6.x或其后的内核也能运行Docker，但运行结果会有很大不同)
- 内核必须支持一种适合的存储驱动，如：Device Manager，AUFS，vfs，btrfs
- 内核必须支持并开启cgroup和命名空间功能

### CentOS安装Docker

Docker支持CentOS 6及以后版本。  
对于CentOS 6，使用下面命令安装：

	sudo yum install -y http://mirrors.yun-idc.com/epel/6/i386/epel-release-6-8.noarch.rpm
	sudo yum install -y docker-io	

对于CentOS 7，使用下面命令安装：

	sudo yum install -y docker


### Ubuntu中安装Docker

	sudo apt-get update
	sudo apt-get install docker-ce // 安装最新版本
	apt-cache madison docker-ce // 列出可用版本
	
			root@db-server:/usr/local/docker# apt-cache madison docker-ce
			docker-ce | 17.09.0~ce-0~ubuntu | https://download.docker.com/linux/ubuntu/ trusty/stable amd64 Packages
			docker-ce | 17.06.2~ce-0~ubuntu | https://download.docker.com/linux/ubuntu/ trusty/stable amd64 Packages
			docker-ce | 17.06.1~ce-0~ubuntu | https://download.docker.com/linux/ubuntu/ trusty/stable amd64 Packages
			docker-ce | 17.06.0~ce-0~ubuntu | https://download.docker.com/linux/ubuntu/ trusty/stable amd64 Packages
			docker-ce | 17.03.2~ce-0~ubuntu-trusty | https://download.docker.com/linux/ubuntu/ trusty/stable amd64 Packages
			docker-ce | 17.03.1~ce-0~ubuntu-trusty | https://download.docker.com/linux/ubuntu/ trusty/stable amd64 Packages
			docker-ce | 17.03.0~ce-0~ubuntu-trusty | https://download.docker.com/linux/ubuntu/ trusty/stable amd64 Packages

	sudo apt-get install docker-ce=<VERSION> // 安装指定版本
	
	sudo apt-get purge docker-ce // 移除docker
	sudo rm -rf /var/lib/docker  // 删除docker相关数据
	sudo apt-get remove docker docker-engine docker.io // 卸载docker

### Windows下安装




### 各系统配置文件路径

	Ubuntu/Debian : /etc/default/docker
	Upstart系统 : /etc/init/docker.conf
	RedHad/Redora/Centos : /etc/sysconfig/docker
	Systemd : /usr/lib/systemd/system/docker.service


### 杂项

	可执行文件路径：/usr/bin/docker   
	配置文件路径：/etc/sysconfig/docker   
	163镜像中心：https://c.163.com/hub#/m/repository/?repoId=2968  
	DockerPool社区的镜像源:dl.dockerpool.com
	docker数据存储路径：/var/lib/docker  
	修改仓库位置： other_args="--insecure-registry hub.c.163.com -H unix:///var/run/docker.sock -H 0.0.0.0:43988"  
	加速服务： curl -sSL https://get.daocloud.io/daotools/set_mirror.sh | sh -s http://fb4e85e4.m.daocloud.io

### 搭建WordPress

docker run --name db --env MYSQL_ROOT_PASSWORD=example -d mariadb
docker run --name MyWordPress --link db:mysql -p 8081:80 -d wordpress

