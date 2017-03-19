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
	build
	commit : 基于容器创建镜像，docker commit -m "" -a "" container image[:tag]
	cp
	create ： 创建容器，docker create [options] image_name
	diff
	events
	exec : 进入容器并执行命令，docker exec 容器名称/容器id command
	export : 到处容器，docker export 容器名称/容器id > file_path
	history
	images : 查看本地镜像，docker images
	import : 导入镜像，cat file_path | sudo docker import - image_name:tag
	info : 查看docker信息，docker info
	inspect : 获取镜像/容器的更多信息，docker inspect 镜像/容器名称/容器id
	kill
	load : 载入镜像，docker load --input file
	login
	logout
	logs
	pause
	port
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
	stats
	stop : 终止容器，docker stop 容器名称/容器ID，默认10秒后关闭
	unpause
	version
	wait


### 镜像相关




### 容器相关