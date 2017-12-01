## Spark常用概念


	Application：基于Spark构建的应用程序，由集群上的一个driver程序和多个executor组成
	Application jar：包含用户Spark应用的jar
	Driver program：该进程运行应用的main()方法并且创建SparkContext
	Cluster manager：用于获取集群上资源的外部服务，如：Standalone manager，Mesos，Yarn
	Deploy mode：根据driver程序运行地方区别，在集群模式中，框架在集群内部启动driver，在客户端模式中，提交者在集群外部启动Driver
	work node：任何在集群可以运行应用代码的节点
	executor：在work节点上，为应用而启动的进程，运行task并将数据保存在内存或磁盘，每个应用有自己的executor
	task：一个将要被发送到executor的工作单元
	job：由多个任务组成的并行计算，并且能从Spark Action中获取响应
	stage：每个job被分成更小的称作stage(阶段)的task组，stage彼此之间是相互依赖的