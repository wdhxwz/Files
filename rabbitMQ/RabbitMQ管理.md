#### RabbitMQ管理

RabbitMQ服务器主要是通过RabbitCtl和RabbitMQ-plugins两个工具来管理的，以下是一些常用功能：

- 服务器启动与关闭

		启动：rabbitmq-server –detached
		关闭：rabbitmqctl stop
		备注：若单机有多个实例，则在rabbitmqctlh后加–n 指定名称
			-detached参数使得rabbitmq以守护进程的方式在后台运行

- 插件管理

		开启某个插件：rabbitmq-plugins enable xxx
		关闭某个插件：rabbitmq-plugins disable xxx
		备注：重启服务器后生效

- 虚拟主机(virtual host)管理

		新建虚拟主机：rabbitmqctl add_vhost xxx
		撤销虚拟主机：rabbitmqctl delete_vhost xxx

- 用户管理

		新建用户：rabbitmqctl add_user {username} {password}
		删除用户：rabbitmqctl delete_user {username}
		修改密码：rabbitmqctl change_password {username} {new password}
		设置角色：rabbitmqctl set_user_tags {username} {tag...}
			备注：tag可以为administrator,monitoring,management

- 权限管理
	
		权限设置：rabbitmqctl set_permissions [-p vhostpath] {user} {conf} {write} {read}
		删除权限：rabbitmqctl clear_permissions [-p vhostpath] {user}
		列出权限：rabbitmqctl list_permissions [-p vhostpath]
		备注：-p vhostpath 指定虚拟主机，不指定为跟虚拟主机/
		{conf}{write}{read}表示执行，写，读权限，是正则表达式配置：
		".*":匹配所有的交换器和队列
		"checks-.*":匹配所有以checks-开头的交换器和队列
		"":不匹配队列和交换器




- 获取服务器状态信息

		服务器状态:rabbitmqctl status
		队列信息：rabbitmqctl list_queues [-p vhostpath] [queueu info item]
			Queueinfoitem可以为：name，durable，auto_delete，arguments，messages_ready，messages_unacknowledged，messages，consumers，memory
		交换器信息：rabbitmqctl list_exchanges[-p vhostpat] [exchangeinfoitem]
            Exchangeinfoitem有：name，type，durable，auto_delete，internal，arguments.
		Binding信息：rabbitmqctl list_bindings[-p vhostpath] [bindinginfoitem]       
            Bindinginfoitem有：source_name，source_kind，destination_name，destination_kind，routing_key，arguments
		Connection信息：rabbitmqctl list_connections [connectioninfoitem ...
		Connectioninfoitem有：recv_oct，recv_cnt，send_oct，send_cnt，send_pend等。
		Channel信息：rabbitmqctl  list_channels[channelinfoitem ...]
		Channelinfoitem有consumer_count，messages_unacknowledged，messages_uncommitted，acks_uncommitted，messages_unconfirmed，prefetch_count，client_flow_blocked

