## 配置Ansible运行环境

Ansible配置文件以ini格式存储配置数据，几乎所有的配置项都能通过playbook或环境变量来重新赋值，配置文件的查找顺序如下：

	1. ANSIBLE_CONFIG：环境变量指向的配置文件
	2. ./ansible.cfg:当前目录下的ansible.cfg配置文件
	3. ~/.ansible.cfg:当前用户home目录下的.ansible.cfg
	4. /etc/ansible/ansible.cfg

### ansible.cfg常用配置项

	1. inventory：资源清单文件的文件，资源清单是一个ansible需要连接管理的主机列表，默认配置为:/etc/ansible/hosts
	2. library:ansible模块存放目录，默认是:/usr/share/ansible
	3. forks:配置ansible最多能有多少个进程同时工作，默认是5个
	4. sudo_user:设置默认执行命令的用户，默认为root
	5. remote_port:指定连接被管接点的端口号，默认22
	6. host_key_checking:设置是否检查ssh主机的密钥
	7. timeout：设置ssh连接的超时间隔，单位秒
	8. log_path:ansible默认不记录日志，如果需要输出日志，配置改字段，日志文件路径

更多配置信息，参照默认配置文件/etc/ansible/ansible.cfg

### 免密登录配置

	ssh-keygen // 生成密码
	ssh-copy-id -i /root/.ssh/id_rsa.pub root@192.168.1.20 // 将公钥复制到待管理节点192.168.1.20上，并且授权用户是root
	ssh root@192.168.1.20 // 测试ssh连接

### 身手小试

	1.测试主机连通性
	ansible 192.168.1.20 -m ping

	2.在远程主机执行命令
	ansible 192.168.1.20 -m shell -a '/bin/echo hello ansible'

	3.命令格式
	ansible [主机]|[组] -m [模块] -a [动作]
	