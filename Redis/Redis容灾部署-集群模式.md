## Redis容灾部署-集群模式

### 简介

redis集群是一个无中心的分布式存储架构，可以在多个节点之间进行数据共享，解决了Redis高可用、可扩展等问题。redis集群提供了以下两个好处：  

	1、将数据自动切分(split)到多个节点
	2、当集群中的某一个节点故障时，redis还可以继续处理客户端的请求。
 
一个 Redis 集群包含 16384 个哈希槽（hash slot），数据库中的每个数据都属于这16384个哈希槽中的一个。集群使用公式 CRC16(key) % 16384 来计算键 key 属于哪个槽。   
集群中的每一个节点负责处理一部分哈希槽。
 
- 集群中的主从复制

集群中的每个节点都有1个至N个复制品，其中一个为主节点，其余的为从节点，如果主节点下线了，集群就会把这个主节点的一个从节点设置为新的主节点，继续工作。这样集群就不会因为一个主节点的下线而无法正常工作。
 
- 注意：

1、如果某一个主节点和他所有的从节点都下线的话，redis集群就会停止工作了。redis集群不保证数据的强一致性，在特定的情况下，redis集群会丢失已经被执行过的写命令   
2、使用异步复制（asynchronous replication）是 Redis 集群可能会丢失写命令的其中一个原因，有时候由于网络原因，如果网络断开时间太长，redis集群就会启用新的主节点，之前发给主节点的数据就会丢失。


### 环境准备

redis集群的创建脚本是使用ruby编写的，需要配置ruby环境：

	yum install ruby  
	yum install rubygems  
	gem install redis

### 节点准备

- 目录准备

	构建如下目录：

		/usr/local/redis-cluster/7000
		/usr/local/redis-cluster/7001
		/usr/local/redis-cluster/7002
		/usr/local/redis-cluster/7003
		/usr/local/redis-cluster/7004
		/usr/local/redis-cluster/7005

- 配置准备

准备6个节点，分成3组，每组一个主节点，一个从节点，每个节点的配置文件如下：redis.conf

	daemonize yes  
	# 每个配置文件只是端口号和日志文件路径不一样
	port 7000  
	cluster-enabled yes  
	cluster-config-file nodes.conf  
	cluster-node-timeout 5000  
	logfile "/data/logs/redis/cluster/7000.log"


将每个配置文件分别放到上面两个路径下(端口号需对应),然后分别进入上面六个目录执行下面命令：

	redis-server redis.conf

### 集群启动

进入下载的redis源包的src目录，使用redis-trib.rb，执行下面命令：

	./redis-trib.rb  create --replicas 1 127.0.0.1:7000 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005

通过--replicas 1指定只有一个从节点，一般前面列出的节点会被指定为直接点

### 集群操作

	进入集群：redis-cli -c  -p [port]  
	查看所有节点信息：redis-cli -c -p 7000 cluster nodes | grep master
	查看所有主节点信息：redis-cli -c -p 7000 cluster nodes | grep master

### 添加节点

根据添加节点类型的不同，有两种方法来添加新节点 
 
	1、主节点：如果添加的是主节点，那么我们需要创建一个空节点，然后将某些哈希槽移动到这个空节点里面   
	2、从节点：如果添加的是从节点，我们也需要创建一个空节点，然后把这个新节点设置成集群中某个主节点的复制品。

- 添加节点：

		./redis-trib.rb add-node 192.168.33.130:7006 192.168.33.130:7000  

第一个参数是新节点的ip:port，第二个参数是集群中某个主节点的ip:port

- 让新节点成了集群中某个主节点的从节点：

		redis-cli -c -p 7006 cluster replicate a246963893faf03c45cc19ef4188f82f5393bfef 

a246963893faf03c45cc19ef4188f82f5393bfef 是集群中某个主节点的id


-  让新节点成为主节点

使用redis-trib程序，将集群中的某些哈希槽移动到新节点里面，这个新节点就成为真正的主节点了。执行下面的命令对集群中的哈希槽进行移动：

	redis-trib.rb reshard 192.168.33.130:7000  

命令执行后，系统会提示我们要移动多少哈希槽，需要指定把这些哈希槽转移到哪个节点上，需要我们指定转移哪几个节点的哈希槽(输入all 表示从所有的主节点中随机转移，凑够指定数量的哈希槽)

### 集群删除节点

如果删除的节点是主节点，首先要把节点中的哈希槽转移到其他节点中，执行下面的命令：

	redis-trib.rb reshard 192.168.33.130:7000  

系统会提示要移动多少哈希槽，输入该节点上哈希槽的数量。  
然后系统提示输入要接收这些哈希槽的节点的ID    
然后要选择从那些节点中转出哈希槽(输入要删除节点的id)   
最后输入done表示输入完毕  

最后一步，使用下面的命令把这个节点删除：

	redis-trib.rb del-node 192.168.33.130:7000 d113e0f033c98e2f6b88fb93e6e98866256d85c4

第一个参数是集群某个主节点的地址，第二个参数是需要删除节点的id

如果是从节点，直接删除即可：

	redis-trib.rb del-node 192.168.33.130:7000 d113e0f033c98e2f6b88fb93e6e98866256d85c4



> http://blog.csdn.net/u011204847/article/details/51307044