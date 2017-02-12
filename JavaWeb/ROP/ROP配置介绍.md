## ROP配置介绍

ROP的相关配置节点有：

	<rop:annotation-driven /> :ROP主配置节点，用于启动ROP框架
	<rop:interceptors/> ：配置ROP拦截器
	<rop:listeners/> ： 配置ROP监听
	<rop:sysparams/> ： 更改ROP默认系统参数的名称

### <rop:annotation-driven />

主配置节点，包含的属性有：

	formatting-conversion-service：自定义的类型转换服务，需继承自org.springframework.format.support.FormattingConversionService
	session-manager：session管理器，需继承com.rop.session.SessionManager
	app-secret-manager：应用密码管理器，需继承com.rop.security.AppSecretManager
	service-access-controller：服务访问限制器，需继承com.rop.security.ServiceAccessController
	invoke-times-controller：服务调用频率限制器，需继承com.rop.security.InvokeTimesController
	sign-enable:是否开启请求数据签名验证，默认是true
	ext-error-base-name:自定义的错误消息
	ext-error-base-names:自定义的错误消息，多个之间用逗号分隔
	core-pool-size:核心线程池数，默认是200
	max-pool-size:最大线程池数，默认是500
	keep-alive-seconds:如果当前线程池中有多于corePoolSize，则这些多出来的线程在空闲了keep-alive-seconds秒后，会自动释放
	queue-capacity:队列长度；当线程池中的线程数大于corePoolSize,小于maxPoolSize，只有当队列满时才会创建新线程
	service-timeout-seconds:服务执行最大过期时间，单位秒，会被每个服务定义的过期时间覆盖
	upload-file-max-size:限制最大上传文件的大小，单位是k
	upload-file-types: 运行上传的文件类型，不限制为*，多种类型之间用逗号分隔

### <rop:interceptors/> 

配置ROP拦截器，子元素是<bean/>元素，每个拦截器需继承抽象类AbstractInterceptor或接口Interceptor，配置样例如下：

	<!--注册拦截器，可配置多个-->
    <rop:interceptors>
        <bean class="com.rop.sample.ReservedUserNameInterceptor"/>
    </rop:interceptors>

### <rop:listeners/>

配置ROP事件监听，事件监听需要继承自RopEventListener<E> ,其中E是事件类型，常用的事件类型有：

- AfterStartedRopEvent(Rop框架初始化后产生的事件)
- PreDoServiceEvent(执行服务方法之前产生的事件)
- AfterDoServiceEvent(执行服务方法之后产生的事件)

配置样例：

	<!--注册监听器，可配置多具-->
    <rop:listeners>
        <bean class="com.rop.sample.SamplePostInitializeEventListener"/>
        <bean class="com.rop.sample.SamplePreDoServiceEventListener"/>
        <bean class="com.rop.sample.SampleAfterDoServiceEventListener"/>
    </rop:listeners>

### <rop:sysparams/> 

配置系统参数的名称，具有的属性有：

	appkey-param-name：应用键的参数名，默认是：appKey
	sessionid-param-name：会话ID参数名，默认是：sessionId
	method-param-name：方法参数名，默认是：method
	version-param-name：服务版本号参数名，默认是：v
	format-param-name：格式化参数名，默认是：format
	locale-param-name：本地化参数名，默认是：locale
	sign-param-name：签名数据的参数名，默认是:sign
	jsonp-param-name：jsonp的参数名，默认是：callback