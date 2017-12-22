## Docker 命令

### 启动Docker

	service docker start
	service docker restart
	service docker stop

### Docker命令模式

Docker命令位于/usr/bin/下，命令模式如下：

	docker [options] command [arg...]
	docker command --help // 查看命令的使用

目前Docker支持如下命令

	attach : 进入容器，docker attach 容器名称/容器id
	build : 基于DockerFile文件创建镜像，docker build [-t tag -f filename] path
	commit : 基于容器创建镜像，docker commit -m "" -a "" container image[:tag]
	cp ： 在容器和宿主系统之间进行文件复制
	create ： 创建容器，docker create [options] image_name
	diff ： 检查一个文件系统的修改
	events ： 从服务端获取实时的事件
	exec : 进入容器并执行命令，docker exec 容器名称/容器id command
	export : 导出容器为一个tar包，docker export 容器名称/容器id > file_path
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








### 命令参数详解

- run命令

		根据镜像创建并运行容器
		格式：docker run [Options] Image [Command] [args...]
		常用的Options：
		-a：关联标准输入输出
		-c：cpu share
		-d：后台运行容器
		-h：容器的hostname
		-i：保持标准输入开启
		-l：设置容器的标签数据
		-m：内存限制，单位byte
		-p：映射容器端口到主机端口
		-P：绑定容器暴露的端口到随机端口
		--rm：退出容器后自动删除容器
		-t：进入容器并开启终端
		-u：指定用户
		-v：挂载数据卷
		--volumes-from：指定需要挂载数据卷的容器
		-w：容器内的工作目录
		--name：指定容器的名字
	
- build命令

		根据Dockerfile创建镜像
		格式：docker build [Options] PATH | URL |  
		PATH是构建时的上下文路径 
		常用的Options：
		-f：dockerfile的文件名称，默认是Dockerfile
		-t：构建镜像的标签，name:tag
		
- exec命令

		进入容器并执行命令
		格式：docker exec [Options] 	container command
		常用的Options：
		-i：开启交互，保持标准输入
		-t：开启终端