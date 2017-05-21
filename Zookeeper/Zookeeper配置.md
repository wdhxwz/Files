## Zookeeper配置


	tickTime：服务器之间或客户端与服务器之间维持心跳的时间间隔，单位毫秒

	dataDir：Zookeeper 保存数据的目录，默认情况下，写数据的日志文件也保存在这个目录里

	dataLogDir :日志存放目录 

	clientPort：客户端连接 Zookeeper 服务器的端口，Zookeeper 会监听这个端口，接受客户端的访问请求。

	initLimit：Zookeeper 服务器集群中连接到 Leader 的 Follower 服务器，初始化连接时最长能忍受多少个心跳时间间隔数

	syncLimit：Leader 与 Follower 之间发送消息，请求和应答时间长度，最长不能超过多少个 tickTime 的时间长度

	server.A=B：C：D
		其中 A 是一个数字，表示这个是第几号服务器；
		B 是这个服务器的 ip 地址；
		C 表示的是这个服务器与集群中的 Leader 服务器交换信息的端口；
		D 表示的是万一集群中的 Leader 服务器挂了，需要一个端口来重新进行选举，选出一个新的 Leader，而这个端口就是用来执行选举时服务器相互通信的端口

	maxClientCnxns： 最大链接数，0表示不限制，默认为0

	myid ： dataDir中，放置一个myid的文件，这个文件就一个数字，标志zookeeper服务

