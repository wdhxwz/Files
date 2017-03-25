## Docker升级到1.9.1

### 升级内核至3.1

	rpm -ivh http://www.elrepo.org/elrepo-release-6-5.el6.elrepo.noarch.rpm
	yum --enablerepo=elrepo-kernel install kernel-lt -y

	#修改grub.conf文件default值为0
	vim /etc/grub.conf
	...
	default=0
	timeout=5
	# 重启
	reboot

	#重启后查看版本：
	uname -r

### 安装docker1.9.1

	curl -sSL -O https://get.docker.com/builds/Linux/x86_64/docker-1.9.1 	
	chmod +x docker-1.9.1 
	sudo mv docker-1.9.1 /usr/local/bin/docker 
	cp /usr/local/bin/docker /usr/bin/docker
	service docker restart
	
	# 查看版本号
	docker version 








