## Docker 命令

### 启动Docker

	service docker start
	service docker restart
	service docker stop

### Docker命令模式

Docker命令位于/usr/bin/下，命令模式如下：

	docker [options] command [arg...]

目前Docker支持如下命令

	attach : 进入容器，docker attach 容器名称/容器id
	build : 基于DockerFile文件创建镜像，docker build [-t tag -f filename] path
	commit : 基于容器创建镜像，docker commit -m "" -a "" container image[:tag]
	cp ： 在容器和宿主系统之间进行文件复制
	create ： 创建容器，docker create [options] image_name
	diff ： 检查一个文件系统的修改
	events ： 从服务端获取实时的事件
	exec : 进入容器并执行命令，docker exec 容器名称/容器id command
	export : 到出容器为一个tar包，docker export 容器名称/容器id > file_path
	history ： 显示一个镜像的历史
	images : 查看本地镜像，docker images
	import : 导入镜像，cat file_path | sudo docker import - image_name:tag
	info : 查看docker信息，docker info
	inspect : 获取镜像/容器的更多信息，docker inspect 镜像/容器名称/容器id
	kill ： 关闭一个运行中的容器
	load : 载入镜像，docker load --input file
	login ： 注册或登录一个docker的仓库服务器
	logout ： 从docker仓库服务器登出
	logs ： 获取容器的logs信息
	pause ： 暂停一个容器的所有进行
	port ： 
	ps ： 查看docker容器进程，docker ps -a
	pull : 下载镜像，格式为：docker pull [IP：PORT/]name[:tag],不显示执行tag时，为lastest
	push : 上传镜像，docker push image
	rename : 重命名容器，docker rename old_name new_name
	restart ： 重启容器，docker restart 容器名称/容器id
	rm ： 删除容器，docker rm 容器名称/容器id
	rmi ： 删除镜像，docker rmi [-f] image
	run ： 创建并运行容器，docker run [仓库地址:端口号]镜像名称[:tag]
	save : 保存镜像，docker save [options] file_name image
	search ： 查找镜像，docker search tomcat
	start : 启动容器
	stop : 终止容器，docker stop 容器名称/容器ID，默认10秒后关闭
	unpause ： 将容器的所有进程从暂停状态恢复过来
	version ： 输出docker的版本信息
	wait ： 阻塞直到一个容器终止


### 镜像相关




### 容器相关