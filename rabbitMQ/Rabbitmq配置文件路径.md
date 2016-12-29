### RabbitMQ配置文件路径

可以通过设置下面的环境变量来为rabbitmq指定配置文件路径，大多数情况下是不需要设置的。

|配置文件名称|描述|
|-|-|
|RABBITMQ_BASE|RabbitMQ服务数据库合日志文件的基目录，也可以通过设置RABBITMQ_MNESIA_BASE和RABBITMQ_LOG_BASE来单独指定|
|RABBITMQ_CONFIG_FILE|配置文件的路径|
|RABBITMQ_MNESIA_BASE|设置MNESIA数据库基目录|
|RABBITMQ_MNESIA_DIR|设置MNESIA数据库目录|
|RABBITMQ_LOG_BASE|日志文件存放基目录|
|RABBITMQ_LOGS|日志文件存放地址|
|RABBITMQ_SASL_LOGS|SASL (System Application Support Libraries) |
|RABBITMQ_PLUGINS_DIR||
|RABBITMQ_PLUGINS_EXPANG_DIR||
|RABBITMQ_ENABLED_PLUGINS_FILE||
|RABBITMQ_PID_FILE||


#### linux下的默认路径

|配置文件名称|路径|
|-|-|
|RABBITMQ_BASE|没有用|
|RABBITMQ_CONFIG_FILE|$RABBITMQ_HOME/etc/rabbitmq/rabbitmq|
|RABBITMQ_MNESIA_BASE|$RABBITMQ_HOME/var/lib/rabbitmq/mnesia|
|RABBITMQ_MNESIA_DIR|$RABBITMQ_MNESIA_BASE/$RABBITMQ_NODENAME|
|RABBITMQ_LOG_BASE|$RABBITMQ_HOME/var/log/rabbitmq|
|RABBITMQ_LOGS|$RABBITMQ_LOG_BASE/$RABBITMQ_NODENAME.log|
|RABBITMQ_SASL_LOGS|$RABBITMQ_LOG_BASE/$RABBITMQ_NODENAME-sasl.log|
|RABBITMQ_PLUGINS_DIR|$RABBITMQ_HOME/plugins|
|RABBITMQ_PLUGINS_EXPANG_DIR|$RABBITMQ_MNESIA_BASE/$RABBITMQ_NODENAME-plugins-expand|
|RABBITMQ_ENABLED_PLUGINS_FILE|$RABBITMQ_HOME/etc/rabbitmq/enabled_plugins|
|RABBITMQ_PID_FILE|$RABBITMQ_MNESIA_DIR.pid|

> 可以通过echo $RABBITMQ_HOME获取RabbitMQ的安装路径

> 上面的变量可以通过/usr/lib/rabbitmq/bin/rabbitmq-server文件中进行设置，已修改默认值

		CONFIG_FILE=/etc/rabbitmq/rabbitmq
		LOG_BASE=/var/log/rabbitmq
		MNESIA_BASE=/var/lib/rabbitmq/mnesia
		SERVER_START_ARGS=
		ENABLED_PLUGINS_FILE=/etc/rabbitmq/enabled_plugins	

#### windows下的默认配置

|配置文件名称|路径|
|-|-|
|RABBITMQ_BASE|C:\Users\Administrator\AppData\Roaming\RabbitMQ|
|RABBITMQ_CONFIG_FILE|C:\Users\Administrator\AppData\Roaming\RabbitMQ\rabbitmq.config|
|RABBITMQ_MNESIA_BASE|C:\Users\Administrator\AppData\Roaming\RabbitMQ\db|
|RABBITMQ_MNESIA_DIR|C:\Users\Administrator\AppData\Roaming\RabbitMQ\db\rabbit@DESKTOP-SBUADOM-mnesia|
|RABBITMQ_LOG_BASE|C:\Users\Administrator\AppData\Roaming\RabbitMQ\log|
|RABBITMQ_LOGS|C:\Users\Administrator\AppData\Roaming\RabbitMQ\log\{rabbitmq_nodename}.log|
|RABBITMQ_SASL_LOGS|C:\Users\Administrator\AppData\Roaming\RabbitMQ\log\{rabbitmq_nodename}-sasl.log|
|RABBITMQ_PLUGINS_DIR|C:\Program Files\RabbitMQ Server\rabbitmq_server-3.6.5\plugins|
|RABBITMQ_PLUGINS_EXPANG_DIR|C:\Users\Administrator\AppData\Roaming\RabbitMQ\db\rabbit@DESKTOP-SBUADOM-plugins-expand|
|RABBITMQ_ENABLED_PLUGINS_FILE|C:\Users\Administrator\AppData\Roaming\RabbitMQ\enabled_plugins|
|RABBITMQ_PID_FILE|没有|










