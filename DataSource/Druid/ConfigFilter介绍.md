## ConfigFilter介绍

 ConfigFilter的作用包括： 从配置文件中读取配置、从远程http文件中读取配置、为数据库密码提供加密功能。

ConfigFilter位于com.alibaba.druid.filter.config包下。

- 从配置文件中读取配置

		<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource"
		      init-method="init" destroy-method="close">
		      <property name="filters" value="config" />
		      <property name="connectionProperties" value="config.file=file:///home/admin/druid-pool.properties" />
		  </bean>


- 从远程服务器读取配置

		<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource"
		      init-method="init" destroy-method="close">
		      <property name="filters" value="config" />
		      <property name="connectionProperties" value="config.file=http://127.0.0.1/druid-pool.properties" />
		  </bean>

- 数据库密码加密

	- 加密密码
	首先需要到druid-x.x.x.jar所在目录下执行下面命令，以生成密码的加密串：
 
	java -cp druid-x.x.x.jar com.alibaba.druid.filter.config.ConfigTools you_password

	- 作下面的配置

			<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource"
			      init-method="init" destroy-method="close">
			      <property name="url" value="jdbc:derby:memory:spring-test;create=true" />
			      <property name="username" value="sa" />
			      <property name="password" value="h9gzp23dkJIZ95Xzj/waxsC2oJ1JoWTh76o4aw7+uGGh63ovAULVOrPewOwHP5i3LCIXqNyvpxJ2nceDFBbzVw==" />
			      <property name="filters" value="config" />
			      <property name="connectionProperties" value="config.decrypt=true" />
			  </bean>	
	
> 原文：https://github.com/alibaba/druid/wiki/%E4%BD%BF%E7%94%A8ConfigFilter
