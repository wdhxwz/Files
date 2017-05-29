## Spring Boot 配置文件注入

### 获取系统变量和application.properties文件配置的变量

凡是被Spring管理的类，实现接口 EnvironmentAware 重写方法 setEnvironment 可以在工程启动时，获取到系统环境变量和application配置文件中的变量。    

	@Configuration
	public class MyEnvironmentAware implements EnvironmentAware {
	
		/**
		 * 注入配置项的值
		 */
		@Value("${myurl}")
		private String myUrl;
	
		@Override
		public void setEnvironment(Environment environment) {
			System.out.println("myurl=" + myUrl);
	
			//通过 environment 获取到系统属性.
	        System.out.println(environment.getProperty("JAVA_HOME"));
	        
	        //获取到前缀是"spring.datasource." 的属性列表值.
	        RelaxedPropertyResolver relaxedPropertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.");
	        System.out.println("spring.datasource.url="+relaxedPropertyResolver.getProperty("url"));
		}
	}

可以通过@ConfigurationProperties 读取application属性配置文件中的属性

	@ConfigurationProperties(prefix="my")
	public class MyProperties {
		private String name;
		private int age;
		private int sex;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public int getSex() {
			return sex;
		}
		public void setSex(int sex) {
			this.sex = sex;
		}
	}

使用@ConfigurationProperties注解，需要添加下面的引用：

	<!-- 解析配置文件 -->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-configuration-processor</artifactId>
		<optional>true</optional>
	</dependency>

### 添加自定义配置文件

spring boot使用application.properties默认了很多配置。有时需要对配置进行归类，并单独成配置文件，使用@ConfigurationProperties解析的时候，指定配置文件的路径，如：

	@ConfigurationProperties(prefix="my",locations="classpath:my.properties")
	public class MyProperties {
		private String name;
		private int age;
		private int sex;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public int getSex() {
			return sex;
		}
		public void setSex(int sex) {
			this.sex = sex;
		}
	}
 
