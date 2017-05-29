## Spring：普通类调用Spring Bean对象

要在一个类使用spring提供的bean对象，需要把这个类注入到spring容器中，交给spring容器进行管理，但是在实际当中，往往会碰到在一个普通的Java类中，想直接使用spring提供的其他对象。如果这是spring框架的独立应用程序，可以通过：

	ApplicationContext ac = new FileSystemXmlApplicationContext("applicationContext.xml");
	ac.getBean("beanId"); 

但是往往所做的都是Web Application，这时我们启动spring容器是通过在web.xml文件中配置，这样就不适合使用上面的方式在普通类去获取对象了，因为这样做就相当于加载了两次spring容器。下面提供了三种方式，可以在Web应用中获取Spring上下文。

### 通过Spring提供的工具类获取ApplicationContext对象

	ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());

这种方式需要request对象，但request对象一般只在controller层。用法比较局限。

### 继承自抽象类ApplicationObjectSupport或其子类WebApplicationObjectSupport 

当启动web服务容器的时候，就将ApplicationContext注入到一个spring工具类的一个静态属性中，这样在普通类就可以通过这个工具类获取ApplicationContext，从而通过getBean( )获取bean对象。

	@Component
	public class SpringUtil extends ApplicationObjectSupport {
		private static ApplicationContext context = null;
	
		@Override
		protected void initApplicationContext(ApplicationContext context) throws BeansException {
			super.initApplicationContext(context);
			if (SpringUtil.context == null) {
				SpringUtil.context = context;
			}
		}
		
		public static ApplicationContext getContext(){
			return context;
		}
		
		public static <T> T getBean(Class<T> requiredType){
			return getContext().getBean(requiredType);
		}
	}

>  需要将上面的SpringUtil交给Spring容器进行管理

### 实现接口ApplicationContextAware

实际上ApplicationObjectSupport是继承自ApplicationContextAware接口的，所以可以直接的继承自该接口，实现上下文的获取：

	@Component
	public class SpringTool implements ApplicationContextAware {
		private static ApplicationContext context = null;
	
		@Override
		public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
			if (SpringTool.context == null) {
				context = applicationContext;
			}
		}
		
		public static ApplicationContext getContext(){
			return context;
		}
		
		public static <T> T getBean(Class<T> requiredType){
			return getContext().getBean(requiredType);
		}
	}

>  需要将上面的SpringTool交给Spring容器进行管理