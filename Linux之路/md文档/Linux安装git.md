## Linux安装git

- 安装依赖包

		yum install curl-devel expat-devel gettext-devel openssl-devel zlib-devel gcc perl-ExtUtils-MakeMaker

- 下载git源码并解压缩

		wget https://github.com/git/git/archive/v2.3.0.zip
		unzip v2.3.0.zip
		cd git-2.3.0

- 编译安装

		make prefix=/usr/local/git all
		make prefix=/usr/local/git install


- 设置环境变量

		vim /etc/profile
		export PATH=/usr/local/git/bin:$PATH
		source /etc/profile

- 测试安装

		git --version

### Ubuntu

- 需要先安装依赖

		sudo apt-get install libcurl4-openssl-dev  libexpat1-dev 
		sudo apt-get install libssl-dev 