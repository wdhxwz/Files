## Disconf安装部署

### 环境配置

	Disconf运行需要如下软件：mysql，Nginx，Tomcat，zookeeper，redis

### 源码下载

	如果在服务器上进行源码编译部署，则还需要配置maven和git环境；
	
	mkdir /home/wangdh/disconf
	cd /home/wangdh/disconf
	git clone https://github.com/knightliao/disconf.git	


### 环境变量配置

	ONLINE_CONFIG_PATH=/data/apps/disconf/source
	WAR_ROOT_PATH=/data/apps/disconf/war
	export ONLINE_CONFIG_PATH
	export WAR_ROOT_PATH

> ONLINE_CONFIG_PATH 配置文件的路径
> WAR_ROOT_PATH 源码编译后war包的存放路径


### 修改配置

	将源码下的disconf-web/profile/rd目录的配置文件拷贝一份到ONLINE_CONFIG_PATH指定的目录下，并修改相应配置文件：
	application.properties：
	zoo.properties：
	jdbc-mysql.properties：
	redis-config.properties：
	log4j.properties：
	logback.xml：
	

### 生成war包

	进入源码的disconf-web目录，执行项目命令：
	sh deploy/deploy.sh


### 初始化数据库

	根据disconf-web下的sql文件夹下的README.md来初始化

### 配置tomcat

	在tomcat的server.xml中加入
	<Context path="" docBase="/data/apps/disconf/war"></Context>

### nginx配置

	在http这个标记对里面加上如下配置（/etc/nginx/nginx.conf）：
	upstream disconf.com {
	    server 127.0.0.1:8080;
	}
	 
	
	server {
	    listen 80;
	    server_name localhost;
	    access_log /data/logs/nginx/disconf-access.log;
	    error_log /data/logs/nginx/disconf-error.log;
	 
	
	    location / {
	        root /data/apps/disconf/war/html;
	        if ($query_string) {
	            expires max;
	        }
	    }
	 
	
	    location ~ ^/(api|export) {
	        proxy_pass_header Server;
	        proxy_set_header Host $http_host;
	        proxy_redirect off;
	        proxy_set_header X-Real-IP $remote_addr;
	        proxy_set_header X-Scheme $scheme;
	        proxy_pass http://disconf.com;
	    }
	}
	upstream disconf 配置中的端口必须与tomcat启动的端口一致
	server中listen是启动该disconf前端的端口
	里面需要修改access_log和error_log指定log的位置
	disconf使用前后端分离的部署方式，前端配置在nginx是那个，后端api部署在tomcat中

### 启动

	分别启动tomcat和nginx，打开浏览器：http://localhost:8991/
	使用用户名admin和密码admin进入系统
	至此disconf的web操作服务搭建完成




