## Docker配置SSH服务

	yum -y install openssh-server.x86_64
	ssh-keygen -t dsa -f /etc/ssh/ssh_host_rsa_key
	ssh-keygen -t dsa -f /etc/ssh/ssh_host_ecdsa_key
	ssh-keygen -t dsa -f /etc/ssh/ssh_host_ed25519_key
	/usr/sbin/sshd -D &
	

	yum -y install net-tools.x86_64
	netstat -tunlp

	RUN sed -ri 's/session    required     pam_loginuid.so/#session    required pam_loginuid.so/g' /etc/pam.d/sshd



	#设置继承镜像
	FROM ubuntu:14.04
	#提供一些作者的信息
	MAINTAINER docker_user (user@docker.com)
	#下面开始运行更新命令
	RUN apt-get update
	#安装ssh服务
	RUN apt-get install -y openssh-server
	RUN mkdir -p /var/run/sshd
	RUN mkdir -p /root/.ssh
	#取消pam限制
	RUN sed -ri 's/session    required     pam_loginuid.so/#session    required pam_loginuid.so/g' /etc/pam.d/sshd
	#复制配置文件到相应位置,并赋予脚本可执行权限
	ADD authorized_keys /root/.ssh/authorized_keys
	ADD run.sh /run.sh
	RUN chmod 755 /run.sh
	#开放端口
	EXPOSE 22
	#设置自启动命令
	CMD ["/run.sh"]
	
	
	
> http://www.cnblogs.com/wade-luffy/p/6548829.html	