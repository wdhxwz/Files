## 搭建Maven私服

### 下载Nexus

	下载地址：https://sonatype-download.global.ssl.fastly.net/nexus/oss/nexus-2.14.4-03-bundle.tar.gz

	tar zxvf nexus-2.14.4-03-bundle.tar.gz
	cd nexus-2.14.4-03/bin
	./nexus start

> 默认是不能以root用户启动的，启动时会提示，根据提示操作


### 配置Nexus

	Nexus内置提供了一个Jetty容器，启动时会监听8081端口，启动后，访问：http://ip:8081/nexus

	配置中央仓库索引：


 