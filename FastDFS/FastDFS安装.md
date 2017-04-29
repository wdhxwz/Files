## FastDFS安装

### 软件准备

- FastDFS_v5.05.tar.gz

		https://codeload.github.com/happyfish100/fastdfs/tar.gz/V5.05

- libfastcommon-1.0.7.tar.gz

		https://codeload.github.com/happyfish100/libfastcommon/tar.gz/V1.0.7
	

- fastdfs-nginx-module_v1.16.tar.gz (nginx模块)

		https://codeload.github.com/happyfish100/fastdfs-nginx-module/zip/master


### 依赖包安装

	 yum install -y libevent 


### 安装libfastcommon-1.0.7

	tar zxvf libfastcommon-1.0.7.tar.gz
	cd libfastcommon-1.0.7
	./make.sh
	./make.sh install

 	ln -s /usr/lib64/libfastcommon.so /usr/local/lib/libfastcommon.so 
	ln -s /usr/lib64/libfastcommon.so /usr/lib/libfastcommon.so 
	

### 安装fastdfs-5.05

	tar zxvf FastDFS_v5.05.tar.gz
	cd fastdfs-5.05
	./make.sh
	./make.sh install

 	ln -s /usr/lib64/libfdfsclient.so /usr/local/lib/libfdfsclient.so 
	ln -s /usr/lib64/libfdfsclient.so /usr/lib/libfdfsclient.so 

> /usr/bin 存放有编译出来的文件   
> /etc/fdfs 存放有配置文件

### FastDFS可用的命令(/usr/bin)

	fdfs_appender_test
	fdfs_appender_test1
	fdfs_append_file
	fdfs_crc32
	fdfs_delete_file
	fdfs_download_file
	fdfs_file_info
	fdfs_monitor
	fdfs_storaged
	fdfs_test
	fdfs_test1
	fdfs_trackerd
	fdfs_upload_appender
	fdfs_upload_file
	restart.sh
	stop.sh


### 配置 tracker 服务

	cd /etc/fdfs/
	mv tracker.conf.sample tracker.conf
	vim tracker.conf

> 修改 base_path=/data/fastdfs/tracker/data-and-log   


### 启动tracker服务

	/usr/bin/fdfs_trackerd /etc/fdfs/tracker.conf 
	/usr/bin/fdfs_trackerd /etc/fdfs/tracker.conf restart
	ps aux | grep tracker 

### 存储节点部署

一般 storage 服务我们会单独装一台机子。

如果 storage 单独安装的话，那上面安装的步骤都要在走一遍，只是到了编辑配置文件的时候，编辑的是 storage.conf 而已

### 配置存储服务

	cd /etc/fdfs/
	mv storage.conf.sample storage.conf
	vim storage.conf

> base_path=/data/fastdfs/tracker/data-and-log  
> store_path0=/data/fastdfs/storage/images-data0  
> tracker_server=192.168.77.132:22122    
> http.server_port=9999

### 启动存储服务

	/usr/bin/fdfs_storaged /etc/fdfs/storage.conf 
	/usr/bin/fdfs_storaged /etc/fdfs/storage.conf restart
	ps aux | grep storage 

### 测试是否部署成功

利用自带的 client 进行测试  

	cp /etc/fdfs/client.conf.sample /etc/fdfs/client.conf    
	vim /etc/fdfs/client.conf  -- 修改tracker的一些配置
	/usr/bin/fdfs_test /etc/fdfs/client.conf upload /home/wangdh/aaa.png     

	[root@localhost fdfs]# /usr/bin/fdfs_test /etc/fdfs/client.conf upload /home/wangdh/test.png
	This is FastDFS client test program v5.05
	Copyright (C) 2008, Happy Fish / YuQing
	FastDFS may be copied only under the terms of the GNU General
	Public License V3, which may be found in the FastDFS source kit.
	Please visit the FastDFS Home Page http://www.csource.org/ 
	for more detail.
	
	[2017-04-20 20:03:48] DEBUG - base_path=/data/fastdfs/client/data-and-log, connect_timeout=30, network_timeout=60, tracker_server_count=1, anti_steal_token=0, anti_steal_secret_key length=0, use_connection_pool=0, g_connection_pool_max_idle_time=3600s, use_storage_id=0, storage server id count: 0
	
	tracker_query_storage_store_list_without_group: 
		server 1. group_name=, ip_addr=192.168.77.132, port=23000
	
	group_name=group1, ip_addr=192.168.77.132, port=23000
	storage_upload_by_filename
	group_name=group1, remote_filename=M00/00/00/wKhNhFj5dpSAaVsZAAASQogJSF4627.png
	source ip address: 192.168.77.132
	file timestamp=2017-04-20 20:03:48
	file size=4674
	file crc32=2282309726
	example file url: http://192.168.77.132/group1/M00/00/00/wKhNhFj5dpSAaVsZAAASQogJSF4627.png
	storage_upload_slave_by_filename
	group_name=group1, remote_filename=M00/00/00/wKhNhFj5dpSAaVsZAAASQogJSF4627_big.png
	source ip address: 192.168.77.132
	file timestamp=2017-04-20 20:03:48
	file size=4674
	file crc32=2282309726
	example file url: http://192.168.77.132/group1/M00/00/00/wKhNhFj5dpSAaVsZAAASQogJSF4627_big.png

> 返回的http://192.168.77.132/group1/M00/00/00/wKhNhFj5dpSAaVsZAAASQogJSF4627.png 即为图片的地址，知道图片的访问地址也访问不了，因为还没装 FastDFS 的 Nginx 模块


### 安装 fastdfs-nginx-module

	 tar -zxvf fastdfs-nginx-module_v1.16.tar.gz   
	 cd fastdfs-nginx-module/src
	 vim config
	 
	修改配置，找到下面这行
	CORE_INCS="$CORE_INCS /usr/local/include/fastdfs /usr/local/include/fastcommon/"
	改成
	CORE_INCS="$CORE_INCS /usr/include/fastdfs /usr/include/fastcommon/"
	
	压根就没有local

	-- 下面的Nginx模块的配置文件，默认是存放到该路径的
	cp mod_fastdfs.conf /etc/fdfs/   

	修改如下几项：
	tracker_server=192.168.199.130:22122
	store_path0=/home/zq/fastdfs
	base_path=/home/zq/fastdfs
	url_have_group_name = true（配置多个group时，应该将此项设置为true）

	-- Nginx需要是要到该配置文件
	cp /home/wangdh/fastdfs-5.05/conf/http.conf /etc/fdfs
	cp /home/wangdh/fastdfs-5.05/conf/mime.types /etc/fdfs  

	yum -y install pcre*
	yum -y install openssl*
	wget http://nginx.org/download/nginx-1.7.8.tar.gz
	tar -zxvf nginx-1.7.8.tar.gz
	cd /nginx-1.7.8/
	
	-- 编译的时候将Nginx模块加进去
	./configure --prefix=/usr/local/nginx --with-http_ssl_module --with-http_spdy_module --with-http_stub_status_module --with-pcre --add-module=/home/wangdh/fastdfs-nginx-module/src
	make & make install


### 配置nginx模块 和nginx配置文件

	vim /etc/fdfs/mod_fastdfs.conf
	vim /usr/local/nginx/conf/nginx.conf

	mod_fastdfs.conf是Nginx模块的配置文件，可以配置tracker的地址和每个组访问的端口和文件实际存储的地址。

	需要在每个storage上部署Nginx模块，才能访问该存储节点上的数据。

	添加如下内容：
	
	location /group1/M00{
            ngx_fastdfs_module;
        }
		
	这里的group1是组名，需要和mod_fastdfs.conf中的一致。


> 如果一台机器上部署了多个storage节点，配置如下

		/etc/fdfs/mod_fastdfs.conf 配置文件配置如下

		connect_timeout=2
		network_timeout=30
		base_path=/tmp
		load_fdfs_parameters_from_tracker=false
		storage_sync_file_max_delay = 86400
		use_storage_id = false
		storage_ids_filename = storage_ids.conf
		tracker_server=192.168.9.64:22122
		url_have_group_name = true # 访问时一定要带上组名
		log_level=info
		log_filename=
		response_mode=proxy
		if_alias_prefix=
		flv_support = true
		flv_extension = flv
		group_count = 2  # 如果有多个组，这里配置组的数量(非0)
		[group1] # 标示组1，下面是组1的配置
		group_name=wangdh
		storage_server_port=23000
		store_path_count=1
		store_path0=/data/fastdfs/storage/group1
		[group2] # 标示组2，下面是组2的配置
		group_name=wangdh02
		storage_server_port=23001
		store_path_count=1
		store_path0=/data/fastdfs/storage/group2

		nginx.conf需要为每个组做配置，如下：
	
		location /wangdh/{
	            ngx_fastdfs_module;
	        }
	
		location /wangdh02/{
	            ngx_fastdfs_module;
	        }
		或
		 location ~* /(.+?)/M00 {
		      root /home/dfs/fdfs/$1/data/M00;
		      ngx_fastdfs_module;
		   }










