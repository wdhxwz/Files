## Docker镜像

Docker运行容器前需要本地存在对应的镜像，如果镜像不存在本地，Docker会从镜像仓库下载(默认是Docker Hub)。  
关于镜像的相关操作有：

	- 获取镜像
	- 查看本地镜像
	- 搜索远程仓库镜像
	- 删除标签和镜像文件
	- 创建镜像
	- 推送镜像

### 获取镜像

镜像是Docker运行容器的前提，使用下面命令可以获取镜像：

	docker pull name[:tag]

不显示指定tag，则默认会选择latest

### 查看镜像信息

	列出本地已有的镜像 : docker images
	获取镜像详细信息:docker inspect 镜像名称

### 搜索镜像

	docker search [options] image_name

	--automated : 仅显示自动创建的镜像
	--no-trunc ： 输出信息不截断
	-s 100： 指定仅显示评价为100以上的镜像

### 删除镜像

	docker rmi image ...

### 创建镜像

创建镜像有三种方法：基于已有镜像的容器创建；基于本地模板导入；基于Dockerfile创建

- 基于已有镜像的容器创建

该方法主要使用docker commit 命令，格式为：

	docker commit [options] container [repository[:tag]]

	-a : 作者信息
	-m : 提交信息
	-p : 提交时暂停容器运行

- 基于本地模板导入


### 保存和载入镜像

	保存镜像:docker save [options] file_name image
	载入镜像：docker load --input file_name / docker load < file_name


### 上传镜像

	docker push name[:tag]