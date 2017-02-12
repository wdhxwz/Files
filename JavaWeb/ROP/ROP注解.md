## ROP注解

ROP默认提供下面四个注解

	@IgnoreSign：标注在请求类的属性上，则表示该属性无需进行签名
	@Temporary：默认情况下，请求对象的所有field都会作为请求参数提交，如果希望某个field不作为参数提交，可以打上@Temporary注解
	@ServiceMethodBean：在类上标注，标注了该注解的类同时标注了@Service注解，成为Spring的Bean
	@ServiceMethod： 使用该注解对服务方法进行标注，使其成为ROP服务方法

### @ServiceMethodBean

@ServiceMethodBean含有如下的属性：

	group：服务所属的分组
	groupTitle:组中文名
	tags:标签
	timeout:访问过期时间，单位毫秒，0或负数时不限制
	version:服务对应的版本号，为空时标时不进行版本检查，调用时v参数也必须为空
	httpAction:请求方法，默认不限制
	needInSession:是否需要会话检查，默认检查
	ignoreSign:是否忽略签名检查，默认不忽略
	obsoleted:标识服务是否已过期，默认不过期


### @ServiceMethod

	和@ServiceMethodBean注解一致。







