## Zookeeper常用命令

### 可执行文件

	zkServer.sh     ： ZooKeeper服务器的启动、停止和重启脚本；
	zkCli.sh        ： ZooKeeper的简易客户端；
	zkEnv.sh        ： 设置ZooKeeper的环境变量；
	zkCleanup.sh    ： 清理ZooKeeper历史数据，包括事务日志文件和快照数据文件。


### 服务器管理命令

	启动ZK服务:        	bin/zkServer.sh start
	查看ZK服务状态:	   bin/zkServer.sh status
	停止ZK服务:       	bin/zkServer.sh stop
	重启ZK服务:        	bin/zkServer.sh restart 

### 客户端命令

	连接服务器          zkCli.sh 连接的是本机
	连接服务器          zkCli.sh -server ip:port
	列出指定节点的子节点	ls /node
	创建节点，并关联数据	create /testnode "this is a test node"
	查看节点内容		   get /testnode
	设置节点内容		   set /testnode "数据被修改了"
	删除节点			delete /testnode

