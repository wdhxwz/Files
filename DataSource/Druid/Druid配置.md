## Druid配置

Druid含有如下配置项：

	name : 配置这个属性的意义在于，如果存在多个数据源，监控的时候可以通过名字来区分开来。如果没有配置，将会生成一个名字，格式是："DataSource-" + System.identityHashCode(this)
	jdbcUrl : 连接数据库的url，不同数据库不一样。例如：mysql : jdbc : mysql://10.20.153.104:3306/druid2 oracle : jdbc : oracle:thin:@10.20.149.85:1521:ocnauto
	userName : 连接数据库的用户名
	password : 连接数据库的密码。如果你不希望密码直接写在配置文件中，可以使用ConfigFilter
	driverClassName : 这一项可配可不配，如果不配置druid会根据url自动识别dbType，然后选择相应的driverClassName
	initialSize : 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时，默认值是0
	maxActive ： 最大连接池数量，默认值是8
	maxIdle : 已经不再使用，配置了也没效果，默认值是8
	minIdle : 最小连接池数量
	maxWait : 获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
	poolPreparedStatements : 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql5.5以下的版本中没有PSCache功能，建议关闭掉。5.5及以上版本有PSCache，建议开启。
	maxOpenPreparedStatements : 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100，默认值是-1
	validationQuery : 用来检测连接是否有效的sql，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会其作用。
	testOnBorrow : 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。默认值是true
	testOnReturn : 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。默认值false
	testWhileIdle : 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。默认值false
	timeBetweenEvictionRunsMillis : 有两个含义：1) Destroy线程会检测连接的间隔时间 2) testWhileIdle的判断依据
	numTestsPerEvictionRun : 不再使用，一个DruidDataSource只支持一个EvictionRun
	minEvictableIdleTimeMillis:
	connectionInitSqls : 物理连接初始化的时候执行的sql
	exceptionSorter : 当数据库抛出一些不可恢复的异常时，抛弃连接，根据dbType自动识别
	filters:属性类型是字符串，通过别名的方式配置扩展插件，
			常用的插件有：
			监控统计用的filter:stat 
			日志用的filter:log4j
			防御sql注入的filter:wall
	ProxyFilters : 类型是List<com.alibaba.druid.filter.Filter>，如果同时配置了filters和proxyFilters，是组合关系，并非替换关系
	
参数的配置意义：

	数据库连接池在初始化的时候会创建initialSize个连接，当有数据库操作时，会从池中取出一个连接。
	如果当前池中正在使用的连接数等于maxActive，则会等待一段时间，等待其他操作释放掉某一个连接，如果这个等待时间超过了maxWait，则会报错；
	如果当前正在使用的连接数没有达到maxActive，则判断当前是否空闲连接，如果有则直接使用空闲连接，如果没有则新建立一个连接。
	在连接使用完毕后，不是将其物理连接关闭，而是将其放入池中等待其他操作复用。	

	同时连接池内部有机制判断：
	如果当前的总的连接数少于miniIdle，则会建立新的空闲连接，以保证连接数得到miniIdle。
	如果当前连接池中某个连接在空闲了timeBetweenEvictionRunsMillis时间后任然没有使用，则被物理性的关闭掉。
	有些数据库连接的时候有超时限制（mysql连接在8小时后断开），或者由于网络中断等原因，连接池的连接会出现失效的情况，这时候设置一个testWhileIdle参数为true，可以保证连接池内部定时检测连接的可用性，不可用的连接会被抛弃或者重建，最大情况的保证从连接池中得到的Connection对象是可用的。
	当然，为了保证绝对的可用性，你也可以使用testOnBorrow为true（即在获取Connection对象时检测其可用性），不过这样会影响性能。


	
	
	
	
