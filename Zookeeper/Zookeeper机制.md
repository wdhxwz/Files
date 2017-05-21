## Zookeeper机制

### Zookeeper简介

ZooKeeper 是 Apache 的一个顶级项目，为分布式应用提供高效、高可用的分布式协调服务，提供了诸如数据发布/订阅、负载均衡、命名服务、分布式协调/通知和分布式锁等分布式基础服务。

### Zookeepe设计目的

	最终一致性：client不论连接到哪个Server，展示给它都是同一个视图，这是zookeeper最重要的性能。

	可靠性：具有简单、健壮、良好的性能，如果消息m被一台服务器接受，那么它将被所有的服务器接受。

	实时性：Zookeeper保证客户端将在一个时间间隔范围内获得服务器的更新信息，或者服务器失效的信息。但由于网络延时等原因，Zookeeper不能保证两个客户端能同时得到刚更新的数据，如果需要最新数据，应该在读数据之前调用sync()接口。

	等待无关（wait-free）：慢的或者失效的client不得干预快速的client的请求，使得每个client都能有效的等待。

	原子性：更新只能成功或者失败，没有中间状态。

	顺序性：包括全局有序和偏序两种：全局有序是指如果在一台服务器上消息a在消息b前发布，则在所有Server上消息a都将在消息b前被发布；偏序是指如果一个消息b在消息a后被同一个发送者发布，a必将排在b前面。


### Zookeeper基本概念

Zookeeper中的角色主要有以下三类：

	领导者(Leader):负责进行投票的发起和决议，更新系统状态
	学习者(Learner):Follower用于接收客户端请求并向客户端返回结果，在选主过程中参与投票；、observer可以接收客户端连接，将请转发给leader节点，不参与投票，只同步leader的状态，其目的是为了扩展系统，提高读取速度
	客户端(client):请求发起方法

Zookeeper的集群节点可以简单分成两类，一个是Leader，唯一一个，其余的都是follower：

	Leader和各个follower是互相通信的，对于zk系统的数据都是保存在内存里面的，同样也会备份一份在磁盘上。  
	对于每个zk节点而言，每个zk节点的命名空间是一样的，也就是有同样的数据。  
	如果Leader挂了，zk集群会重新选举，在毫秒级别就会重新选举出一个Leaer。
	集群中除非有一半以上的zk节点挂了，zk service才不可用。

Zookeeper的命名空间就是zk应用的文件系统，它和linux的文件系统很像，也是树状，这样就可以确定每个路径都是唯一的，对于命名空间的操作必须都是绝对路径操作。   
与linux文件系统不同的是，linux文件系统有目录和文件的区别，而zk统一叫做znode，一个znode节点可以包含子znode，同时也可以包含数据。

### zk读写数据

	写数据：
	当客户端进行写数据请求时，会指定zk集群中节点，如果是follower接收到写请求，就会把请求转发给Leader，Leader通过内部的Zab协议进行原子广播，直到所有zk节点都成功写了数据后（内存同步以及磁盘更新），这次写请求算是完成，然后zk service就会给client发回响应

	读数据：
	因为集群中所有的zk节点都呈现一个同样的命名空间视图（就是结构数据），上面的写请求已经保证了写一次数据必须保证集群所有的zk节点都是同步命名空间的，所以读的时候可以在任意一台zk节点上

> 其实写数据的时候不是要保证所有zk节点都写完才响应，而是保证一半以上的节点写完了就把这次变更更新到内存，并且当做最新命名空间的应用。所以在读数据的时候可能会读到不是最新的zk节点，这时候只能通过sync()解决

### Zookeeper节点类型

Zookeeper中znode节点创建时候是可以指定类型的，主要有下面几种类型：

	PERSISTENT：持久化znode节点，一旦创建这个znode点存储的数据不会主动消失，除非是客户端主动的delete。

	SEQUENCE：顺序增加编号znode节点，比如ClientA去zk service上建立一个znode名字叫做/Nginx/conf，指定了这种类型的节点后zk会创建/Nginx/conf0000000000，ClientB再去创建就是创建/Nginx/conf0000000001，ClientC是创建/Nginx/conf0000000002，以后任意Client来创建这个znode都会得到一个比当前zk命名空间最大znode编号+1的znode，也就说任意一个Client去创建znode都是保证得到的znode是递增的，而且是唯一的。

	EPHEMERAL：临时znode节点，Client连接到zk service的时候会建立一个session，之后用这个zk连接实例创建该类型的znode，一旦Client关闭了zk的连接，服务器就会清除session，然后这个session建立的znode节点都会从命名空间消失。总结就是，这个类型的znode的生命周期是和Client建立的连接一样的。

	PERSISTENT|SEQUENTIAL：顺序自动编号的znode节点，这种znoe节点会根据当前已经存在的znode节点编号自动加 1，而且不会随session断开而消失。

	EPHEMERAL|SEQUENTIAL：临时自动编号节点，znode节点编号会自动增加，但是会随session消失而消失


### Zookeeper工作原理

Zookeeper 的核心是广播，这个机制保证了各个Server之间的同步。实现这个机制的协议叫做Zab协议。   
Zab协议有两种模式，分别是恢复模式（选主）和广播模式（同步）。   
当服务启动或者在领导者崩溃后，Zab就进入了恢复模式，当领导者被选举出来，且大多数Server完成了和leader的状态同步以后， 恢复模式就结束了。   
状态同步保证了leader和Server具有相同的系统状态。为了保证事务的顺序一致性，zookeeper采用了递增的事务id号 （zxid）来标识事务。所有的提议（proposal）都在被提出的时候加上了zxid。实现中zxid是一个64位的数字，它高32位是epoch用 来标识leader关系是否改变，每次一个leader被选出来，它都会有一个新的epoch，标识当前属于那个leader的统治时期。低32位用于递增计数。

每个Server在工作过程中有三种状态：

	LOOKING：当前Server不知道leader是谁，正在搜寻。
	LEADING：当前Server即为选举出来的leader。
	FOLLOWING：leader已经选举出来，当前Server与之同步。










