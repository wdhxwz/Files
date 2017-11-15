## Docker网络配置

Docker提供映射容器端口到宿主主机，容器互联来为容器提供网络服务。

### 端口映射

启动容器时，如果不指定对应参数，容器外部是无法通过网络来访问容器内的网络应用和服务的。

在创建容器的时候，通过-P或-p参数来指定端口映射；   
使用-P标记时，Docker随机映射一个49000到49900的端口至容器内部开发的端口；   
-p 的端口映射规则为： ip:hostPort:containerPort  
当没有指定IP时，会绑定本地所有接口的所有地址；  
没有指定hostPort时，绑定任意端口到指定容器端口

### 查看端口映射

使用下面命令可以查看容器端口的映射情况：

	docker port container [containerPort]
	当没有指定containerPort时，会列出所有的端口映射

### 容器互联

容器互联在源容器和接收容器之间创建一个隧道，接收容器可以看到源容器指定的信息。   
通过在创建容器的时候使用 --link name:alias来指定源容器，其中name是容器的名称，alias是别名，通过--name参数可以指定容器的名称而不是默认随机生成。   

例如：有一个db容器，一个web容器，web容器创建时指定db容器的互联，此时db容器作为源容器，web容器作为接受容器


	docker run -it  --name webapp --link hungry_northcutt:mysql -P training/webapp:latest bash








