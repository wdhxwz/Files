## Nginx配置

安装完成后，在安装目录下有配置目录conf，过滤掉xxx.default配置后留下的配置文件：tree | grep -v default

	.
	├── 1
	├── fastcgi.conf
	├── fastcgi_params
	├── koi-utf
	├── koi-win
	├── mime.types
	├── nginx.conf
	├── scgi_params
	├── uwsgi_params
	└── win-utf

除了nginx.conf文件外，其他配置文件，一般只需要使用默认即可

### nginx.conf配置文件

去掉注释后的配置内容：

	worker_processes  1;
	events {
	    worker_connections  1024;
	}
	http {
	    include       mime.types;
	    default_type  application/octet-stream;
	    sendfile        on;
	    server {
	        listen       80;
	        server_name  localhost;
	        location / {
	            root   html;
	            index  index.html index.htm;
	        }
	        error_page   500 502 503 504  /50x.html;
	        location = /50x.html {
	            root   html;
	        }
	    }
	}

- worker_processes ：工作进程的数量，一般设置为cpu的核数
- worker_connections ： 每个工作进程的最大连接数
- server{} ： 定义了虚拟主机
	- listen ： 监听端口号
	- server_name ： 监听的域名
	-  location{} ： 匹配的 URI 进行配置
		- root ：对应uri的资源查找路径
		- index：首页index文件的名称，可以配置多个，以空格分开


### 配置文件结构
	......
	events{
		......
	}
				
	http{
		......
		server{
			......
		}
				
		server{
			......
		}
		......
	}
				



### mime.types



















