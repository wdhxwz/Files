### RabbitMQ Java 客户端01

- maven依赖

		<dependency>
			<groupId>com.rabbitmq</groupId>
			<artifactId>amqp-client</artifactId>
			<version>3.6.5</version>
		</dependency>
		<dependency>
			<groupId>commons-lang</groupId>
			<artifactId>commons-lang</artifactId>
			<version>2.6</version>
		</dependency>

- 常用的类

		com.rabbitmq.client.BasicProperties  
		com.rabbitmq.client.Channel  
		com.rabbitmq.client.Connection  
		com.rabbitmq.client.ConnectionFactory  
		com.rabbitmq.client.Consumer  
		com.rabbitmq.client.MessageProperties  
		com.rabbitmq.client.QueueingConsumer 



- hello world 发送示例

		public class TestApp {
				private static String userName = "test";
				private static String password = "1q2w#E$R";
				private static String virtualHost = "test";
				private static String exChanges = "";
				private static String queueName = "app-01";
				private static String host = "127.0.0.1";
				private static int port = 5672;
	
				/**
				 * @param args
				 * @throws Exception
				 */
				public static void main(String[] args) throws Exception {
					// 实例化一个rabbitmq tcp连接工厂
					// 该工厂需要配置rabbitmq的连接参数
					ConnectionFactory factory = new ConnectionFactory();
					factory.setUsername(userName);
					factory.setPassword(password);
					factory.setVirtualHost(virtualHost);
					factory.setHost(host);
					factory.setPort(port);
			
					// 从连接工厂中创建一个tcp连接
					Connection connection = factory.newConnection();
			
					// 从tcp连接中创建信道
					Channel channel = connection.createChannel();
			
					// 创建队列
					channel.queueDeclare(queueName, true, false, false, null);
					
					// 发送的消息
					String message="发送给RabbitMQ的消息";
					channel.basicPublish(exChanges, queueName, false, null, message.getBytes());
					
					// 释放资源
					channel.close();
					connection.close();
					
					System.out.println("OK");
				}
			}


>说明

>RabbitMQ默认有一个exchange，叫default exchange，它用一个空字符串表示，它是direct exchange类型，任何发往这个exchange的消息都会被路由到routing key的名字对应的队列上，如果没有对应的队列，则消息会被丢弃。这就是为什么代码中channel执行basicPulish方法时，第二个参数本应该为routing key，却被写上了QUEUE_NAME。

- hello world 消费示例

		// 创建队列消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		// 指定消费队列
		channel.basicConsume(queueName, true, consumer);
		while (true) {
			// nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			System.out.println(new String(delivery.getBody()));
		}


>说明

- channel.queueDeclare：定义队列

		第一个参数：队列名字，
		第二个参数：队列是否可持久化即重启后该队列是否依然存在，
		第三个参数：该队列是否时独占的即连接上来时它占用整个网络连接，
		第四个参数：是否自动销毁即当这个队列不再被使用的时候即没有消费者对接上来时自动删除，
		第五个参数：其他参数如TTL（队列存活时间）等。
- channel.basicConsume：消费队列

		第一个参数：队列名字
		第二个参数：是否自动应答，如果为真，消息一旦被消费者收到，服务端就知道该消息已经投递，从而从队列中将消息剔除，否则，需要在消费者端手工调用channel.basicAck()方法通知服务端，如果没有调用，消息将会进入unacknowledged状态，并且当消费者连接断开后变成ready状态重新进入队列，
		第三个参数，具体消费者类。

- channel.exchangeDeclare：定义交换器

		第一个参数：交换器的名称
		第二个参数：交换器的类型，topic，direct，fanout，header

- channel.queueBind：绑定队列到交换器

		第一个参数：队列名称
		第二个参数：交换器名称
		第三个参数：路由键值

- 多个消费者监听一个队列

		队列会按轮休方式向每个消费者发送消息

- 路由：route

		将消息从交换器路由到队列或丢弃。
		一个交换器可以绑定多个队列
		一个队列可以通过两个routekey绑定到同一个交换器
