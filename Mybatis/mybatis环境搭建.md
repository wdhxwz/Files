### mybatis环境搭建

在开始实际的开发工作之前，首先需要设置好mybatis环境，本节便是对此的一个介绍。

#### 安装mybatis

根据下面的步骤，可以将mybatis安装到机器上：

- 下载最新版本的mybatis
- 下载对应的数据库驱动，如mysql驱动，sqlserver驱动
- 将上面的jar文件放置到适当的文件夹下，并将该文件夹路径设置到CLASSPATH环境变量下
 
> mybatis下载地址：https://github.com/mybatis/mybatis-3/releases/download/mybatis-3.4.2/mybatis-3.4.2.zip


#### mybatis maven依赖

		<!-- mybatis -->
		<dependency>
			<groupId>org.mybatis</groupId>
			<artifactId>mybatis</artifactId>
			<version>3.3.0</version>
		</dependency>

		<!-- 添加mysql驱动 -->
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>5.1.12</version>
		</dependency>

		<!-- SqlServer 驱动 -->
		<dependency>
			<groupId>com.hynnet</groupId>
			<artifactId>sqljdbc4-chs</artifactId>
			<version>4.0.2206.100</version>
		</dependency>