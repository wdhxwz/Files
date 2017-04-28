## Tomcat配置优化

### 精简Tomcat和配置文件

1. 删除不需要的管理应用和帮助应用，提高tomcat安全性

		删除webapps下所有文件：rm –fr $CATALINA_HOME/webapps/*
		删除server/webapps下所有文件:rm –fr $CATALINA_HOME/server/webapps/*

2. 精简sever.xml配置文件

		使用tomcat发布版本中的最小配置文件，提高性能，如果有功能上的需求，在逐个的加入功能配置。


### 连接器优化

在$CATALINA_HOME/conf/server.xml配置文件中的Connetctor节点，和连接数相关的参数配置和优化。

	1. maxThreads
	
	Tomcat使用线程来处理接收的每个请求。这个值表示Tomcat可创建的最大的线程数。默认值200。 可以根据机器的时期性能和内存大小调整，一般可以在400-500。最大可以在800左右。
	
	2. acceptCount
	
	指定当所有可以使用的处理请求的线程数都被使用时，可以放到处理队列中的请求数，超过这个数的请求将不予处理。默认值10。
	
	3. minSpareThreads
	
	Tomcat初始化时创建的线程数。默认值4。
	
	4. maxSpareThreads
	
	一旦创建的线程超过这个值，Tomcat就会关闭不再需要的socket线程。默认值50。
	
	5. enableLookups
	
	是否反查域名，默认值为true。为了提高处理能力，应设置为false。
	
	6. connnectionTimeout
	
	网络连接超时，默认值20000，单位：毫秒。设置为0表示永不超时，这样设置有隐患的。通常可设置为30000毫秒。
	
	7. maxKeepAliveRequests
	
	保持请求数量，默认值100。
	
	8. bufferSize
	
	输入流缓冲大小，默认值2048 bytes。
	
	9. compression
	
	压缩传输，取值on/off/force，默认值off。
	
	其中和最大连接数相关的参数为maxThreads和acceptCount。如果要加大并发连接数，应同时加大这两个参数。web server允许的最大连接数还受制于操作系统的内核参数设置，通常Windows是2000个左右，Linux是1000个左右。


配置示例：

	<!-- AJP连接 -->
	<Connector port="8009" maxTreads="500" minSpareThreads="10" 
	       maxSpareThreads="50" acceptCount="50" connectionTimeout="60000" 
	       enableLookups="false" redirectPort="8443" protocol="AJP/1.3" />
	
	<!-- 通用连接 -->
	<Connector port="8080"
	       maxTreads="500" minSpareThreads="10" maxSpareThreads="50"
	       acceptCount="50" connectionTimeout="60000"
	       enableLookups="false" redirectPort="8443" protocol="AJP/1.3"
	       compression="on"
	       compressionMinSize="2048"
	       noCompressionUserAgents="gozilla, traviata"
	       compressableMimeType="text/html,text/xml" />
	
	<!-- 主机和应用配置 -->
	<Host name="localhost" appBase=""
	    unpackWARs="true" autoDeploy="true"
	    xmlValidation="false" xmlNamespaceAware="false">
	    <Context path="" docBase="/www/xxxx/site/web" reloadable="true" debug="0"/>
	</Host>

### JVM优化

Tomcat默认可以使用的内存为128MB,Windows下,在文件{tomcat_home}/bin/catalina.bat，Unix下，在文件$CATALINA_HOME/bin/catalina.sh的前面，增加如下设置：

	JAVA_OPTS="$JAVA_OPTS -Xms[初始化内存大小] -Xmx[可以使用的最大内存]"
	# 或设置环境变量
	export JAVA_OPTS="$JAVA_OPTS -Xms[初始化内存大小] -Xmx[可以使用的最大内存]

一般说来，你应该使用物理内存的 80% 作为堆大小。如果本机上有Apache服务器，可以先折算Apache需要的内存，然后修改堆大小。建议设置为70％；建议设置[初始化内存大小]等于[可以使用的最大内存]，这样可以减少平凡分配堆而降低性能。

### 其他优化配置

1. Tomcat中如何禁止和允许列目录下的文件

		在$CATALINA_HOME/conf/web.xml中，把listings参数设置成false即可，如下：

		<servlet>
		   <servlet-name>default</servlet-name>
		   <servlet-class>org.apache.catalina.servlets.DefaultServlet</servlet-class>
		   <init-param>
		       <param-name>debug</param-name>
		       <param-value>0</param-value>
		   </init-param>
		   <init-param>
		       <param-name>listings</param-name>
		       <param-value>false</param-value>
		   </init-param>
		   <load-on-startup>1</load-on-startup>
		</servlet>


