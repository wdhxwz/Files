## Nginx配置例子

	  # 配置后端服务器组
	  upstream proxy_svrs{
	      server 192.168.1.16:8082;
	  }
	
	  upstream gcloudApi{
	      server 192.168.1.16:8084;
	  }
	
	  upstream gcloudBiz{
	      server 192.168.1.16:8081;
	  }
	
	  map $http_upgrade $connection_upgrade {
	    default upgrade;
	    '' close;
	  }
	
	
	  server{
	       listen 80;
	       server_name  192.168.1.20;
	       location /GCloudWeb
	           {
	              # 使用服务器组的名称
	              proxy_pass http://proxy_svrs;
	              proxy_http_version 1.1;
	              proxy_set_header Upgrade $http_upgrade;
	              proxy_set_header Connection $connection_upgrade;
	              proxy_read_timeout 3000s;
	              proxy_set_header Host $host;
	              proxy_set_header X-Real-IP $remote_addr;
	              proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
	           }
	       location /GCloudAPI
	           {
	              # 使用服务器组的名称
	              proxy_pass http://gcloudApi;
	              proxy_set_header Host $host;
	              proxy_set_header X-Real-IP $remote_addr;
	              proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
	           }
	
	        location /GCloudBiz
	           {
	              # 使用服务器组的名称
	              proxy_pass http://gcloudBiz/; #后面的斜杠不能少，作用是不往后端传递/GCloudBiz 这个路径
	              proxy_set_header Host $host;
	              proxy_set_header X-Real-IP $remote_addr;
	              proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
	           }
	  }
