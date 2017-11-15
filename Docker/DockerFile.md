## DockerFile

用于快速创建自定义的镜像

### 基本结构

Dockerfile由一行行命令语句组成，#号开头的为注释行。   
一般分为4部分：基础镜像信息、维护者信息、镜像操作指令、容器启动后执行命令。   
下面是一个例子：

	# This dockerfile uses the ubuntu image
	# Version 2
	# Edition 1
	# Author:wangdh
	
	# 第一行必须指定基于的基础镜像
	FROM ubuntu
	
	# 维护者信息
	MAINTAINER wangdh 1366678737@qq.com
	
	# 镜像的操作指令
	RUN echo "deb http://archive.ubuntu.com/ubuntu/ raring main universe" >> /etc/apt/sources.list
	RUN apt-get update && apt-get install -y nginx
	RUN echo "\ndaemon off;" >> /etc/nginx/nginx.conf
	
	# 容器启动时执行的指令
	CMD /usr/sbin/nginx


> 每运行一条RUN命令，镜像添加新的一层

### 指令

指令的一般格式：Instruction args，包含有：FROM、MAINTAINER、RUN等

1. *FROM指令*
		
		指定一个基础镜像，第一个指令必须为FROM指令
		格式为：FROM <image>或FROM <image>:<tag>

2. *MAINTAINER指令*

		指定维护者信息
		格式：MAINTAINER <name>

3. *RUN指令*

		指定要在基础镜像中运行的命令,每条RUN命令将在当前镜像基础上执行，并提交为新的镜像，当命令较长时，可以用 \ 来换行
		格式：RUN <command>
		
4. *CMD指令*

		指定启动容器时执行的命令，每个dockerfile只能有一条CMD指令，若指定多条，只执行最后一条，启动容器时指定了运行命令，则会覆盖CMD指定的命令。
		格式：
		CMD ["executable","param1","param2"] 使用exec执行
		CMD command param1 param2 在/bin/sh中执行，提供给需要交互的应用

5. *EXPOSE指令*

		指定容器暴露的端口号，容器启动时需要通过-P/-p参数
		格式：EXPOSE <port>[<port>...]

6. *ENV指令*

		指定环境变量，会被后续的RUN指令使用
		格式：ENV <key> <value>

7. *ADD指令*

		复制指定的<src>到容器的<dest>,其中<src>可以是dockerfile所在目录的一个相对路径(目录或文件);可以是URL，也可以是tar文件(自动解压)
		格式：ADD <src> <dest>

8. *COPY指令*

		复制本机的<src>到容器的<dest>，目标路径不存在时，会自动创建
		格式：COPY <src> <dest>

9. *ENTRYPOINT指令*

		配置容器启动后执行的命令，并且不可被docker run提供的参数覆盖，每个dockerfile只能有一条指令，若指定多条，只执行最后一条
		ENTRYPOINT ["executable","param1","param2"] 
		ENTRYPOINT command param1 param2 shell中执行

10. *VOLUME指令*

		创建一个可以从本地主机或其他容器挂载的挂载点
		格式:VOLUME ["/data"]

11. *USER指令*

		指定运行容器时的用户名，后续的RUN也会使用指定用户
		格式：USER <userId>

12. *WORKDIR指令*

		指令工作目录，后续的RUN、CMD、ENTRYPOINT会使用该目录
		格式：WORKDIR <path>

13. *ONBUILD指令*

		配置当所创建的镜像作为其他新创建进行的基础镜像时，所执行的指令
		格式：ONBUILD <指令>

### 创建镜像

编写好dockerfile时，通过docker build命令来创建镜像，格式为：  	
docker build [选项] 路径