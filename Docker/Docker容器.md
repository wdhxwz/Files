## Docker容器

容器是镜像的一个运行实例，是可读写的。  
镜像自身是自渎的，容器从镜像启动的时候，Docker会在镜像的最上层创建一个可写层，镜像自身将保持不变。   
涉及到的容器相关操作有:

	-  创建容器
	-  启动容器
	-  终止容器
	-  进行容器内操作
	-  删除容器
	-  导入导出容器来进行容器迁移 

### 创建容器

	根据镜像新建容器：docker create [options] image_name

### 启动容器

	用create来创建的容器处于停止状态，可以使用docker start来启动：
	docker start contains_name/contains_id

### 新建并启动容器

启动容器有两种方式，一种是基于镜像新建一个容器并启动；另外一种是将终止状态的容器重新启动。 

	docker run 等价于 docker create + docker start

当使用docker run命令时，Docker在后台做了如下操作：

	- 检查本地是否存在指定镜像，不存在则从公有仓库下载
	- 利用镜像创建并启动一个容器
	- 分配一个文件系统，并在只读的镜像层外面挂载一层可读写层
	- 从宿主机配置的网桥接口中桥接一个虚拟接口到容器中去
	- 从地址池配置一个IP地址给容器
	- 执行用户执行的应用程序
	- 执行完毕后容器被终止

### 终止容器

	终止容器：docker stop contain_name/contain_id
	将一个运行着的容器终止后再启动：docker restart contain_name/contain_id

### 进入容器

使用-d参数启动后，容器会在后台运行，此时无法看到容器的信息，可以使用下面3中方式进入容器：

- attach命令

		docker attach 容器名称/容器id

>使用attach命令有时不方便，当多个窗口同时attach到同一个容器时，所有窗口都会同步显示，当某个窗口因命令阻塞时，其他窗口无法执行操作。


- exec命令

1.3版本起，提供了一个exec工具，可以直接在容器内运行命令，如：

	docker exec -it centos /bin/bash

- nsenter工具

### 删除容器

	docker rm [options] 容器1 容器2 ...

可用的选项有：

	-f : 强制终止并删除一个运作中的容器
	-l : 删除容器的连接，但保留容器
	-v : 删除容器挂载的数据卷

### 导出容器

导出一个已经创建的容器到一个文件，不管此时容器是否处于运行状态：

	docker export container > 路径

将导出的容器文件迁移到另一台机子，然后使用导入命令实现容器迁移

### 导入容器

导出的文件可以使用docker import命令导入，成为镜像：

	cat 文件名 | sudo docker import - my/centos:v1.0.1

该命令执行完后，返回镜像的id




 

### 常用的启动选项

	-d : 以后台进行启动容器
	-i : 让容器的标注输入保持打开 
	-t : 分配一个伪终端

