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
	
	[root@localhost libfastcommon-1.0.7]# ./make.sh install
	mkdir -p /usr/lib64
	install -m 755 libfastcommon.so /usr/lib64
	mkdir -p /usr/include/fastcommon
	install -m 644 common_define.h hash.h chain.h logger.h base64.h shared_func.h pthread_func.h ini_file_reader.h _os_bits.h sockopt.h sched_thread.h http_func.h md5.h local_ip_func.h avl_tree.h ioevent.h ioevent_loop.h fast_task_queue.h fast_timer.h process_ctrl.h fast_mblock.h connection_pool.h /usr/include/fastcommon

 	ln -s /usr/lib64/libfastcommon.so /usr/local/lib/libfastcommon.so 
	ln -s /usr/lib64/libfastcommon.so /usr/lib/libfastcommon.so 
	

### 安装fastdfs-5.05

	tar zxvf FastDFS_v5.05.tar.gz
	cd fastdfs-5.05
	./make.sh
	./make.sh install

	[root@localhost fastdfs-5.05]# ./make.sh install
	mkdir -p /usr/bin
	mkdir -p /etc/fdfs
	cp -f fdfs_trackerd /usr/bin
	if [ ! -f /etc/fdfs/tracker.conf.sample ]; then cp -f ../conf/tracker.conf /etc/fdfs/tracker.conf.sample; fi
	mkdir -p /usr/bin
	mkdir -p /etc/fdfs
	cp -f fdfs_storaged  /usr/bin
	if [ ! -f /etc/fdfs/storage.conf.sample ]; then cp -f ../conf/storage.conf /etc/fdfs/storage.conf.sample; fi
	mkdir -p /usr/bin
	mkdir -p /etc/fdfs
	mkdir -p /usr/lib64
	cp -f fdfs_monitor fdfs_test fdfs_test1 fdfs_crc32 fdfs_upload_file fdfs_download_file fdfs_delete_file fdfs_file_info fdfs_appender_test fdfs_appender_test1 fdfs_append_file fdfs_upload_appender /usr/bin
	if [ 0 -eq 1 ]; then cp -f libfdfsclient.a /usr/lib64; fi
	if [ 1 -eq 1 ]; then cp -f libfdfsclient.so /usr/lib64; fi
	mkdir -p /usr/include/fastdfs
	cp -f ../common/fdfs_define.h ../common/fdfs_global.h ../common/mime_file_parser.h ../common/fdfs_http_shared.h ../tracker/tracker_types.h ../tracker/tracker_proto.h ../tracker/fdfs_shared_func.h ../storage/trunk_mgr/trunk_shared.h tracker_client.h storage_client.h storage_client1.h client_func.h client_global.h fdfs_client.h /usr/include/fastdfs
	if [ ! -f /etc/fdfs/client.conf.sample ]; then cp -f ../conf/client.conf /etc/fdfs/client.conf.sample; fi


 	ln -s /usr/lib64/libfdfsclient.so /usr/local/lib/libfdfsclient.so 
	ln -s /usr/lib64/libfdfsclient.so /usr/lib/libfdfsclient.so 

> /usr/bin 存放有编译出来的文件   
> /etc/fdfs 存放有配置文件


### 配置 tracker 服务

	cd /etc/fdfs/
	mv tracker.conf.sample tracker.conf
	vim tracker.conf

> 修改 base_path=/data/fastdfs/tracker/data-and-log   
> http.server_port=9090


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
> store_path1=/data/fastdfs/storage/images-data1 
> store_path2=/data/fastdfs/storage/images-data2 
> tracker_server=192.168.77.132:22122    
> http.server_port=9999


### 启动存储服务

	/usr/bin/fdfs_storaged /etc/fdfs/storage.conf 
	/usr/bin/fdfs_storaged /etc/fdfs/storage.conf restart
	ps aux | grep storage 

### 测试是否部署成功

利用自带的 client 进行测试  

	cp /etc/fdfs/client.conf.sample /etc/fdfs/client.conf    
	vim /etc/fdfs/client.conf
	/usr/bin/fdfs_test /etc/fdfs/client.conf upload /home/wangdh/test.png     

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

> 我们现在知道图片的访问地址我们也访问不了，因为我们还没装 FastDFS 的 Nginx 模块


### 安装 fastdfs-nginx-module

	 tar -zxvf fastdfs-nginx-module_v1.16.tar.gz   
	 cd fastdfs-nginx-module/src
	 vim config
	 
	修改配置，找到下面这行
	CORE_INCS="$CORE_INCS /usr/local/include/fastdfs /usr/local/include/fastcommon/"
	改成
	CORE_INCS="$CORE_INCS /usr/include/fastdfs /usr/include/fastcommon/"
	
	压根就没有local

	cp mod_fastdfs.conf /etc/fdfs/  

	修改如下几项：
	tracker_server=192.168.199.130:22122
	store_path0=/home/zq/fastdfs
	base_path=/home/zq/fastdfs
	url_have_group_name = true（配置多个tracker时，应该将此项设置为true）

	cp /home/wangdh/fastdfs-5.05/confhttp.conf /etc/fdfs
	cp /home/wangdh/fastdfs-5.05/confmime.types /etc/fdfs  

	yum -y update
	yum -y install pcre*
	yum -y install openssl*
	wget http://nginx.org/download/nginx-1.7.8.tar.gz
	tar -zxvf nginx-1.7.8.tar.gz
	yum -y  install gcc
	yum -y install gcc-c++
	cd /nginx-1.7.8/
	./configure --prefix=/usr/local/nginx --with-http_ssl_module --with-http_spdy_module --with-http_stub_status_module --with-pcre --add-module=/home/wangdh/fastdfs-nginx-module/src
	make & make install




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








