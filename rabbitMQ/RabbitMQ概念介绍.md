### RabbitMQ 概念介绍


#### AMQP与RabbitMQ

- AMQP:Advanced Message Queuing Protocol高级消息队列协议

	是应用层协议的一个开放标准，为面向消息的中间件设计。消息中间件主要用于组件之间的解耦，消息的发送者无需知道消息使用者的存在，反之亦然。

	AMQP的主要特征是面向消息、队列、路由（包括点对点和发布/订阅）、可靠性、安全。

- RabbitMQ

	RabbitMQ是一个开源的AMQP实现，服务器端用Erlang语言编写，支持多种客户端，如：Python、Ruby、.NET、Java、JMS、C、PHP、ActionScript、XMPP、STOMP等，支持AJAX。用于在分布式系统中存储转发消息，在易用性、扩展性、高可用性等方面表现不俗。

#### MQ中的5中角色

		Producer：消息生产者，产生消息的角色
		Exchange ： 交换器, 在得到生产者的消息后,根据路由将消息扔到对应队列的角色.
		Queue ： 队列, 消息暂时呆的地方.
		Consumer：消费者, 把消息从队列中取出的角色.
		Message：消息 ， RabbitMQ 中的消息有自己的一系列属性, 某些属性对信息流有直接影响.

#### MQ中较重要的策略

		持久化：服务重启时, 是否能恢复队列中的数据.
		调度策略：交换器如何把消息给到哪些队列, 是每个队列给一条, 或者把一条消息给多个队列.
		分配策略： 队列面对消费者时, 如何把消息吐出去, 来一个消费者就把消息全给它, 还是只给一条.
		状态反馈：当消息从某一个队列中被提出后, 这个消息的生命周期就此结束, 还是说需要一个具体的信号以明确标识消息已被正确处理.

- 队列：queue

		是RabbitMQ的内部对象，用于存储信息；RabbitMQ中的信息只能存储在Quque中，生产者生产的消息并最终投递到Quque中，消费者可以从Quque中获取消息并消费。

- 交换器：Exchange 

		生产者将消息发送到Exchange，Exchange将消息路由到一个或者多个Quque中或者丢弃。

- 路由key：routing key

		生产者在将消息发送给Exchange的时候，一般会指定一个routing key，而这个routing key需要与 Exchange Type 与binding key 联合使用才能最终生效。

- 绑定：Binding 

		RabbitMQ 中通过Binding 将Exchange 与Quque关联起来，这样RabbitMQ就知道如何正确地将消息路由到指定的Quque了

- 交换器类型：fanout、direct、top、headers

		fanout：分列，会把所有发送到该Exchange的消息路由到所有与它绑定的Queue中，此时routing key是不起作用的。

		direct：重定向，会把消息路由到那些binding key与routing key***完全匹配***的Queue中

		topic：主题，topic类型的Exchange在匹配规则上进行了扩展，它与direct类型的Exchage相似，也是将消息路由到binding key与routing key相匹配的Queue中，但这里的匹配规则有些不同，它约定：
	
		routing key为一个句点号“. ”分隔的字符串（我们将被句点号“. ”分隔开的每一段独立的字符串称为一个单词），如“stock.usd.nyse”、“nyse.vmw”、“quick.orange.rabbit”
	
		binding key与routing key一样也是句点号“. ”分隔的字符串
	
		binding key中可以存在两种特殊字符“*”与“#”，用于做模糊匹配，其中“*”用于匹配一个单词，“#”用于匹配多个单词（可以是零个）】

		headers类型的Exchange不依赖于routing key与binding key的匹配规则来路由消息，而是根据发送的消息内容中的headers属性进行匹配。

-  Connection

		 就是一个TCP的连接。Producer和Consumer都是通过TCP连接到RabbitMQ Server的

- Channel

		 虚拟连接。它建立在上述的TCP连接中。数据流动都是在Channel中进行的。也就是说，一般情况是程序起始建立TCP连接，第二步就是建立这个Channel。

- 虚拟主机

		Virtual hosts，本质上都是一个RabbitMQ Server，拥有它自己的queue，exchagne，和bings rule等等。这保证了你可以在多个不同的application中使用RabbitMQ。

- 消息回执

		Message acknowlegment，消费者在消费完消息后发送一个回执给RabbitMQ,RabbitMQ收到消息回执（Message acknowledge）后，才将该消息从Quque中移除。

- 消息持久化

		Message durability，希望即使在RabbitMQ在重启的情况下，也不会丢失消息，那么我们将Quque与Message都设置成可持久化的（durability），这样就可以保证绝大部分情况下我们的RabbitMQ消息不会丢失。

- 

