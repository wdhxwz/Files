## Spring Boot 静态资源处理

### 静态资源默认处理

Spring Boot 默认提供了静态资源处理，使用 WebMvcAutoConfiguration 中的配置各种属性。

	默认配置的 /** 映射到 /static （或/public、/resources、/META-INF/resources） 
	默认配置的 /webjars/** 映射到 classpath:/META-INF/resources/webjars/ 
	
	上面的 static、public、resources 等目录都在 classpath: 下面（如 src/main/resources/static）

	读取资源的优先级为：META/resources > resources > static > public  


### 自定义静态资源处理

实现类继承 WebMvcConfigurerAdapter 并重写方法 addResourceHandlers，如：

	/**
	 * 自定义资源映射
	 * @author wdhcxx
	 *
	 */
	@Configuration
	public class StaticResoucesConfig extends WebMvcConfigurerAdapter{
	
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler("/images/**").addResourceLocations("classpath:/images/");
			
			super.addResourceHandlers(registry);
		}	
	} 

上面讲/images开头的资源请求，映射到classpath:images下。   

> 如果添加的模式与默认的一致，则会覆盖系统的配置，可以多次使用 addResourceLocations 添加目录，优先级先添加的高于后添加的。


















