## Tomcat配置

### Server节点的层次结构

		|-- Server
		|    |-- Listener
		|    |-- GlobalNamingResources
		|    |    |-- Resource
		|    |-- Service
		|    |    |-- Connector
		|    |    |-- Engine
		|    |    |    |-- Realm
		|    |    |    |-- Host
		|    |    |    |    |-- Alias
		|    |    |    |    |-- Context
		|    |    |    |    |-- Value

上面列出的比较常用的组件元素，server.xml文件中可定义的元素非常多，包括Server, Service, Connector, Engine, Cluster, Host, Alias, Context, Realm, Valve, Manager, Listener, Resources, Resource, ResourceEnvRef, ResourceLink, WatchedResource, GlobalNameingResources, Store, Transaction, Channel, Membership, Transport, Member, ClusterListener等等。

> 每个Tomcat实例只跑一个独立的应用程序，那样我们应用程序之间就不会在互相受到影响


#### Server元素

这会让Tomcat启动一个server实例（即一个JVM），它监听在8005端口以接收shutdown命令。各Server的定义不能使用同一个端口，这意味着如果在同一个物理机上启动了多个Server实例，必须配置它们使用不同的端口。这个端口的定义用于为管理员提供一个关闭此实例的便捷途径，因此，管理员可以直接telnet至此端口使用SHUTDOWN命令关闭此实例。不过，基于安全角度的考虑，这通常不允许远程进行。

该元素代表整个容器，是Tomcat实例的顶层元素。由org.apache.catalina.Server接口来定义。它包含一个或多个Service元素。并且它不能做为任何元素的子元素。

	<Server port ="8005" shutdown ="SHUTDOWN" debug ="0">

可配置的属性：

	 className：指定实现org.apache.catalina.Server接口的类。默认值为: org.apache.catalina.core.StandardServer；
	 port：指定Tomcat监听shutdown命令端口。终止服务器运行时，必须在Tomcat服务器所在的机器上发出shutdown命令。该属性是必须的；
	 shutdown：指定终止Tomcat服务器运行时，发给Tomcat服务器的shutdown监听端口的字符串。该属性必须设置；

#### Service元素

Service主要用于关联一个引擎和与此引擎相关的连接器，每个连接器通过一个特定的端口和协议接收入站请求交将其转发至关联的引擎进行处理。因此，Service要包含一个引擎、一个或多个连接器。

该元素由org.apache.catalina.Service接口定义，它包含一个<Engine>元素，以及一个或多个<Connector>，这些Connector元素共享用同一个Engine元素。

	<Service name="Catalina"> // 第一个<Service>处理所有直接由Tomcat服务器接收的web客户请求
	<Service name="Apache"> // 第二个<Service>处理所有由Apahce服务器转发过来的Web客户请求

可配置的属性：

	className：指定实现org.apahce.catalina.Service接口的类。默认为：org.apahce.catalina.core.StandardService；
	name：定义Service的名字，此名字也会在产生相关的日志信息时记录在日志文件当中，默认为Catalina；

#### Engine元素

Engine是Servlet处理器的一个实例，即servlet引擎，默认为定义在server.xml中的Catalina。

每个Service元素只能有一个Engine元素。元素处理在同一个<Service>中所有<Connector>元素接收到的客户请求。由org.apahce.catalina.Engine接口定义。

	<Engine name="Catalina" defaultHost="localhost" debug="0">

可配置的属性：

	className：指定实现Engine接口的类，默认值为StandardEngine；
	defaultHost：指定处理客户的默认主机名，在<Engine>中的<Host>子元素中必须定义这一主机；定义一个接收所有发往非明确定义虚拟主机的请求的host组件；
	name：定义Engine的名字；
	在<Engine>可以包含如下元素<Logger>, <Realm>, <Value>, <Host>，<Listener>；

#### Host元素

位于Engine容器中用于接收请求并进行相应处理的主机或虚拟主机。

它由Host接口定义。一个Engine元素可以包含多个<Host>元素。每个<Host>的元素定义了一个虚拟主机。它包含了一个或多个Web应用<Context>。

	<Host name="localhost" debug="0" appBase="webapps" unpackWARs="true" autoDeploy="true">

可配置的属性：

	className：指定实现Host接口的类。默认值为StandardHost
	appBase：指定虚拟主机的目录，可以指定绝对目录，也可以指定相对于<CATALINA_HOME>的相对目录。如果没有此项，默认为<CATALINA_HOME>/webapps；
	autoDeploy：如果此项设为true，表示Tomcat服务处于运行状态时，能够监测appBase下的文件，如果有新有web应用加入进来，会自运发布这个WEB应用；
	unpackWARs：如果此项设置为true，表示把WEB应用的WAR文件先展开为开放目录结构后再运行。如果设为false将直接运行为WAR文件；
	alias：指定主机别名，可以指定多个别名；
	deployOnStartup：如果此项设为true，表示Tomcat服务器启动时会自动发布appBase目录下所有的Web应用；
	如果Web应用中的server.xml没有相应的<Context>元素，将采用Tomcat默认的Context；
	name：定义虚拟主机的名字；
	在<Host>元素中可以包含如下子元素:<Logger>, <Realm>, <Value>, <Context>；

#### Context元素

Context在某些意义上类似于apache中的路径别名，一个Context定义用于标识tomcat实例中的一个Web应用程序。

它由Context接口定义。是使用最频繁的元素。每个<Context>元素代表了运行在虚拟主机上的单个Web应用。一个<Host>可以包含多个<Context>元素。每个web应用有唯一的一个相对应的Context代表web应用自身。servlet容器为第一个web应用创建一个 ServletContext对象。

	<Context path="/sample" docBase="sample" debug="0" reloadbale="true">

可配置的属性：

	className：指定实现Context的类，默认为StandardContext类；
	docBase：相应的Web应用程序的存放位置；也可以使用相对路径，起始路径为此Context所属Host中appBase定义的路径；切记，docBase的路径名不能与相应的Host中appBase中定义的路径名有包含关系，比如，如果appBase为deploy，而docBase绝不能为deploy-bbs类的名字；
	path：指定访问Web应用的URL入口,注意/myweb,而不是myweb了事；
	reloadable：如果这个属性设为true, Tomcat服务器在运行状态下会监视在WEB-INF/classes和Web-INF/lib目录CLASS文件的改动。如果监视到有class文件被更新，服务器自重新加载Web应用；
	cookies：指定是否通过Cookies来支持Session，默认值为true；
	useNaming：指定是否支持JNDI，默认值为了true；
	在<Context>元素中可以包含如下元素:<Logger>, <Realm>, <Resource>, <ResourceParams>；

#### Connector元素

由Connector接口定义。<Connector>元素代表与客户程序实际交互的组件，它负责接收客户请求，以及向客户返回响应结果。

进入Tomcat的请求可以根据Tomcat的工作模式分为如下两类：

	Tomcat作为应用程序服务器：请求来自于前端的web服务器，这可能是Apache, IIS, Nginx等；
	Tomcat作为独立服务器：请求来自于web浏览器；









