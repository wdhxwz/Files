### RabbitMQ 概念介绍


- AMQP:Advanced Message Queuing Protocol高级消息队列协议

	是应用层协议的一个开放标准，为面向消息的中间件设计。消息中间件主要用于组件之间的解耦，消息的发送者无需知道消息使用者的存在，反之亦然。

	AMQP的主要特征是面向消息、队列、路由（包括点对点和发布/订阅）、可靠性、安全。

- RabbitMQ

	RabbitMQ是一个开源的AMQP实现，服务器端用Erlang语言编写，支持多种客户端，如：Python、Ruby、.NET、Java、JMS、C、PHP、ActionScript、XMPP、STOMP等，支持AJAX。用于在分布式系统中存储转发消息，在易用性、扩展性、高可用性等方面表现不俗。

- 队列：queue

	是RabbitMQ的内部对象，用于存储信息；RabbitMQ中的信息只能存储在Quque中，生产者生产者消息并最终投递到Quque中，消费者可以从Quque中获取消息并消费。

- 交换器：Exchange 

	生产者将消息发送到Exchange，Exchange将消息路由到一个或者多个Quque中或者丢弃。

- 路由key：routing key

	生产者在将消息发送给Exchange的时候，一般会指定一个routing key，来指定这个路由规则，而这个routing key需要与 Exchange Type 与binding key 联合使用才能最终生效。

- 绑定：Binding 

	RabbitMQ 中通过Binding 将Exchange 与Quque关联起来，这样RabbitMQ就知道如何正确地将消息路由到指定的Quque了

- 交换器类型：fanout、direct、top、headers

	fanout：分列，会把所有发送到该Exchange的消息路由到所有与它绑定的Queue中。

	direct：重定向，会把消息路由到那些binding key与routing key***完全匹配***的Queue中

	topic：主题，topic类型的Exchange在匹配规则上进行了扩展，它与direct类型的Exchage相似，也是将消息路由到binding key与routing key相匹配的Queue中，但这里的匹配规则有些不同，它约定：
	
	routing key为一个句点号“. ”分隔的字符串（我们将被句点号“. ”分隔开的每一段独立的字符串称为一个单词），如“stock.usd.nyse”、“nyse.vmw”、“quick.orange.rabbit”
	
	binding key与routing key一样也是句点号“. ”分隔的字符串
	
	binding key中可以存在两种特殊字符“*”与“#”，用于做模糊匹配，其中“*”用于匹配一个单词，“#”用于匹配多个单词（可以是零个）】

	headers类型的Exchange不依赖于routing key与binding key的匹配规则来路由消息，而是根据发送的消息内容中的headers属性进行匹配。

- 生产者：producer，产生消息的客户端

- 消费者：consumer，消费消息的客户端

-  Connection： 就是一个TCP的连接。Producer和Consumer都是通过TCP连接到RabbitMQ Server的

- Channels： 虚拟连接。它建立在上述的TCP连接中。数据流动都是在Channel中进行的。也就是说，一般情况是程序起始建立TCP连接，第二步就是建立这个Channel。

- 虚拟主机：Virtual hosts，本质上都是一个RabbitMQ Server，拥有它自己的queue，exchagne，和bings rule等等。这保证了你可以在多个不同的application中使用RabbitMQ。

- 消息回执：Message acknowlegment，消费者在消费完消息后发送一个回执给RabbitMQ,RabbitMQ收到消息回执（Message acknowledge）后，才将该消息从Quque中移除。

- 消息持久化：Message durability，希望即使在RabbitMQ在重启的情况下，也不会丢失消息，那么我们将Quque与Message都设置成可持久化的（durability），这样就可以保证绝大部分情况下我们的RabbitMQ消息不会丢失。

-

