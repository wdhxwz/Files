## Spring Boot 热部署


热部署可以解决代码修改完后自己部署，而无需手动进行。首先需要加入下面配置：

	<plugin>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-maven-plugin</artifactId>
		<dependencies>
			<!--springloaded 热部署 需要勾选上项目的自动构建才行 -->
			<dependency>
				<groupId>org.springframework</groupId>
				<artifactId>springloaded</artifactId>
				<version>1.2.4.RELEASE</version>
			</dependency>
		</dependencies>
			<executions>
				<execution>
					<goals>
						<goal>repackage</goal>
					</goals>
					<configuration>
						<classifier>exec</classifier>
					</configuration>
				</execution>
			</executions>
	</plugin>

配置完后，使用下面方式进行：

	maven 命令：spring-boot:run
	run as 方式：把spring-loader-1.2.4.RELEASE.jar下载下来，然后在IDEA的run参数里VM参数设置为：-javaagent:.\lib\springloaded-1.2.4.RELEASE.jar -noverify


### 重启部署

使用springloaded进行热部署，但是有部分代码修改了，并不会进行部署。可以通过重启机制解决这个问题。   
spring-boot-devtools 是一个为开发者服务的一个模块，其中最重要的功能就是自动应用代码更改到最新的App上面去。    
原理是在发现代码有更改之后，重新启动应用，其深层原理是使用了两个ClassLoader，一个Classloader加载那些不会改变的类（第三方Jar包），另一个ClassLoader加载会更改的类，称为  restart ClassLoader,这样在有代码更改的时候，原来的restart ClassLoader 被丢弃，重新创建一个restart ClassLoader，由于需要加载的类相比较少，所以实现了较快的重启时间（5秒以内）。

### 添加依赖

	<!-- 重启部署依赖 -->
	<dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
    </dependency>


	<plugin>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-maven-plugin</artifactId>
		<configuration>
	        <!-- fork:如果没有该项配置,devtools不会起作用,即应用不会restart -->
	        <fork>true</fork>
        </configuration>
	</plugin>

### 知识补充

默认情况下，/META-INF/maven，/META-INF/resources，/resources，/static，/templates，/public这些文件夹下的文件修改不会使应用重启，但是会重新加载（devtools内嵌了一个LiveReload server，当资源发生改变时，浏览器刷新）。

### 相关配置项

	# 设置不重启的目录
	spring.devtools.restart.exclude=static/**,public/**
	
	# 保留默认设置的基础上还要添加其他的排除目录
	spring.devtools.restart.additional-exclude
	
	# 当非classpath下的文件发生变化时应用得以重启
	spring.devtools.restart.additional-paths

	# 关闭自动重启
	spring.devtools.restart.enabled=false


### Remake

IDEA 热部署设置：

File -> Settings -> Compiler 勾选上Build project automatically  

快捷键ctrl+alt+shift  / ,选择Registry，然后勾选上compiler.automake.allow.when.app.running