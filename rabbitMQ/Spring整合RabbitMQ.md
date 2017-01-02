### Spring整合RabbitMQ

- maven依赖

		<!-- spring-rabbitMQ -->
		<dependency>
			<groupId>org.springframework.amqp</groupId>
			<artifactId>spring-rabbit</artifactId>
			<version>1.6.6.RELEASE</version>
		</dependency>

- rabbitMQ.properties配置

		mq.host=127.0.0.1
		mq.username=test
		mq.password=1q2w#E$R
		mq.port=5672
		mq.vhost=test

- 配置文件

		<beans xmlns="http://www.springframework.org/schema/beans"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
			xmlns:rabbit="http://www.springframework.org/schema/rabbit"
			xmlns:context="http://www.springframework.org/schema/context"
			xsi:schemaLocation="http://www.springframework.org/schema/beans 
			http://www.springframework.org/schema/beans/spring-beans.xsd
		    http://www.springframework.org/schema/context 
		    http://www.springframework.org/schema/context/spring-context-4.1.xsd
		    http://www.springframework.org/schema/rabbit
		    http://www.springframework.org/schema/rabbit/spring-rabbit-1.0.xsd">
			<description>rabbitmq 连接服务配置</description>
			<!-- 配置扫描 -->
			<context:component-scan base-package="com.rabbitmq.test" />
			<!-- 读取properties文件 -->
			<bean id="propertyConfigurerForProject1"
				class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
				<property name="order" value="1" />
				<property name="systemPropertiesModeName" value="SYSTEM_PROPERTIES_MODE_OVERRIDE" />
				<property name="ignoreResourceNotFound" value="true" />
				<property name="ignoreUnresolvablePlaceholders" value="true" />
				<property name="locations">
					<list>
						<value>classpath*:/rabbmitmq.properties</value>
					</list>
				</property>
			</bean>
			<!-- 连接配置 -->
			<rabbit:connection-factory id="connectionFactory"
				host="${mq.host}" username="${mq.username}" password="${mq.password}"
				port="${mq.port}" virtual-host="${mq.vhost}" />
			<rabbit:admin connection-factory="connectionFactory" />
			<!-- spring template声明 -->
			<rabbit:template exchange="test-mq-exchange" id="amqpTemplate"
				connection-factory="connectionFactory" message-converter="jsonMessageConverter" />
			<!-- 消息对象json转换类 -->
			<bean id="jsonMessageConverter"
				class="org.springframework.amqp.support.converter.Jackson2JsonMessageConverter" />
			<!-- 声明一个队列 -->
			<rabbit:queue id="test_queue" name="test_queue"
				durable="true" auto-delete="false" exclusive="false" />
			<!-- 声明一个交换器 -->
			<rabbit:direct-exchange name="test-mq-exchange"
				durable="true" auto-delete="false" id="test-mq-exchange">
				<rabbit:bindings>
					<rabbit:binding queue="test_queue" key="test_route_key" />
				</rabbit:bindings>
			</rabbit:direct-exchange>
	</beans>

- 消息发送者

		@Service
		public class MQProducer {
			@Autowired
			private AmqpTemplate amqpTemplate;
		
			private final static Logger logger = LoggerFactory.getLogger(MQProducer.class);
		
			public void sendDataToQueue(String routeKey, Object data) {
				try {
					amqpTemplate.convertAndSend(routeKey, data);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}


- 消息消费者

		public class QueueListenter implements MessageListener {
		
			@Override
			public void onMessage(Message msg) {
				try {
					System.out.println(new String(msg.getBody()));
					System.out.print(msg.toString());
					System.out.println(msg.getMessageProperties().getMessageId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

