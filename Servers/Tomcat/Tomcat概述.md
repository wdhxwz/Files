## Tomcat概述

### Tomcat目录结构

	|-- bin 执行脚本目录；
	|   |-- bootstrap.jar					tomcat启动时所依赖的一个类，在启动tomcat时会发现Using CLASSPATH: 是加载的这个类；
	|   |-- catalina-tasks.xml				定义tomcat载入的库文件，类文件；
	|   |-- catalina.sh						tomcat单个实例在Linux平台上的启动/关闭脚本；
	|   |-- commons-daemon-native.tar.gz 	jsvc工具，可以使tomcat以守护进程方式运行，需单独编译安装；
	|   |-- commons-daemon.jar 				jsvc工具所依赖的java类；
	|   |-- configtest.sh 					tomcat检查配置文件语法是否正确的Linux平台脚本；
	|   |-- daemon.sh 						tomcat已守护进程方式运行时的，启动，停止脚本；
	|   |-- digest.sh
	|   |-- setclasspath.sh
	|   |-- shutdown.sh		 tomcat服务在Linux平台下关闭脚本；
	|   |-- startup.sh 		 tomcat服务在Linux平台下启动脚本；
	|   |-- tomcat-juli.jar
	|   |-- tomcat-native.tar.gz 			使tomcat可以使用apache的apr运行库，以增强tomcat的性能需单独编译安装；
	|   |-- tool-wrapper.sh
	|   |-- version.sh						查看tomcat以及JVM的版本信息；

	|-- conf 顾名思义，配置文件目录；
	|   |-- catalina.policy 		Java相关的安全策略配置文件，在系统资源级别上提供访问控制的能力，比如：配置tomcat对文件系统中目录或文件的读、写执行等权限，及对一些内存，session等的管理权限；
	|   |-- catalina.properties 	Tomcat内部package的定义及访问相关的控制，也包括对通过类装载器装载的内容的控制；Tomcat在启动时会事先读取此文件的相关设置；
	|   |-- context.xml 			tomcat的默认context容器，所有host的默认配置信息；
	|   |-- logging.properties 		Tomcat通过自己内部实现的JAVA日志记录器来记录操作相关的日志，此文件即为日志记录器相关的配置信息，可以用来定义日志记录的组件级别以及日志文件的存在位置等；
	|   |-- server.xml 				tomcat的主配置文件，包含Service, Connector, Engine, Realm, Valve, Hosts主组件的相关配置信息；
	|   |-- tomcat-users.xml 		Realm认证时用到的相关角色、用户和密码等信息；Tomcat自带的manager默认情况下会用到此文件；在Tomcat中添加/删除用户，为用户指定角色等将通过编辑此文件实现；
	|   |-- web.xml 				为不同的Tomcat配置的web应用设置缺省值的文件，遵循Servlet规范标准的配置文件，用于配置servlet，并为所有的Web应用程序提供包括MIME映射等默认配置信息；
	
	|-- lib 运行需要的库文件（JARS），包含被Tomcat使用的各种各样的jar文件。在Linux/UNIX上，任何这个目录中的文件将被附加到Tomcat的classpath中；

	|-- logs 日志文件默认存放目录；
	|   |-- localhost_access_log.2013-09-18.txt 	访问日志；
	|   |-- localhost.2013-09-18.log				错误和其它日志；
	|   |-- manager.2013-09-18.log 					管理日志；
	|   |-- catalina.2013-09-18.log 				Tomcat启动或关闭日志文件；

	|-- temp 临时文件存放目录；

	|-- webapps tomcat	默认存放应用程序的目录，好比apache的默认网页存放路径是/var/www/html一样；
	|   |-- docs 			tomcat文档；
	|   |-- examples 		tomcat自带的一个独立的web应用程序例子；
	|   |-- host-manager 	tomcat的主机管理应用程序；
	|   |   |-- META-INF 	整个应用程序的入口，用来描述jar文件的信息；
	|   |   |   |-- context.xml 	当前应用程序的context容器配置，它会覆盖tomcat/conf/context.xml中的配置；
	|   |   |-- WEB-INF 			用于存放当前应用程序的私有资源；
	|   |   |   |-- classes 		用于存放当前应用程序所需要的class文件；
	|   |   |   |-- lib 			用于存放当前应用程序所需要的jar文件；
	|   |   |   |-- web.xml 		当前应用程序的部署描述符文件，定义应用程序所要加载的servlet类，以及该程序是如何部署的；
	|   |-- manager 		tomcat的管理应用程序；
	|   |-- ROOT 			指tomcat的应用程序的根，如果应用程序部署在ROOT中，则可直接通过http://ip:port 访问到；
	|-- work 				用于存放JSP应用程序在部署时编译后产生的class文件；


### Tomcat模块结构


![](http://i.imgur.com/6ZMiBDk.png)

如上图所示，前端请求被tomcat直接接收或者由反向服务器代理 --> 通过HTTP，或者AJP代理给Tomcat，此时请求被tomcat中的connector接收，不同的connector和Engine被service组件关联起来，在一个Engine中定义了许多的虚拟主机，由Host容器定义，每一个Host容器代表一个主机，在各自的Host中，又可以定义多个Context，用此来定义一个虚拟主机中的多个独立的应用程序。