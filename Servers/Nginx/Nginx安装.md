## Nginx安装

### 环境准备

nginx依赖以下模块：

- gzip模块需要安装zlib库
- rewrite模块需要pcre库
- ssl功能需要openssl库

#### 安装pcre库

1. 获取安装包：http://www.pcre.org
2. 解压pcre-xxx.tar.gz: tar -zxvf pcre-xxx.tar.gz 
3. 进入解压缩目录，执行./configure
4. make & make install

#### 安装openssl

1. 获取openssl编译安装包:http://www.openssl.org/source/
2. 解压缩openssl-xx.tar.gz包。
3. 进入解压缩目录，执行./config。
4. make & make install

#### 安装zlib

1. 获取zlib编译安装包：http://www.zlib.net/
2. 解压缩openssl-xx.tar.gz包。
3. 进入解压缩目录，执行./configure。
4. make & make install

#### 安装nginx

1. 获取nginx，：http://nginx.org/en/download.html
2. 解压缩nginx-xx.tar.gz包。
3. 进入解压缩目录，执行./configure
4. make & make install

> 若安装时找不到上述依赖模块，使用--with-openssl=<openssl_dir>、--with-pcre=<pcre_dir>、--with-zlib=<zlib_dir>指定依赖的模块目录。如已安装过，此处的路径为安装目录；若未安装，则此路径为编译安装包路径，nginx将执行模块的默认编译安装。

#### 将nginx安装路径配置到环境变量

	vim /etc/profile
	export NGINX_HOME=/usr/local/nginx
	export PATH=$PATH:$NGINX_HOME
	source /etc/profile


#### nginx 常用命令

	nginx -s reload            # 重新载入配置文件
	nginx -s reopen            # 重启 Nginx
	nginx -s stop              # 停止 Nginx
	nginx -t 				   # 测试配置文件
	nginx -v				   # 查看nginx版本
























































































