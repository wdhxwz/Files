## Spring Cache:Spring 缓存

Spring Cache是一种抽象，而未提供具体的实现；自Spring 3.1开始引入，通过在方法上标注注解并使用Aop对方法进行织入，来实现缓存。  

Spring Cache的一些优点：  

- 开箱即用，提供缓存编程的一致性，方便提供各种底层的Cache实现
- 使用简单，通过Cache注解即可实现缓存逻辑
- 事务回滚时，缓存也会回滚
- 支持复杂的缓存逻辑 

>ps:Spring Cache抽象没有锁的概念，当多线程并发操作时同一个缓存项时，将可能得到过期的数据。


### Spring Cache注解

- @Cacheable注解

针对方法配置，能根据方法的请求参数对结果进行缓存；缓存的本质就是键/值对集合，默认情况下，缓存抽象使用方法签名及参数值作为缓存的键值。

工作原理：先从缓存中查找数据，没有缓存则执行方法并缓存结果，然后返回数据。

@Cacheable注解包含如下参数：

	value/cacheNames -- 缓存的名称  
	key -- 缓存key   
	condition -- 缓存条件，返回true才进行缓存，方法返回前预先校验   
	unless  -- 缓存条件，满足条件则不进行缓存


- @CachePut注解

针对方法配置，能根据方法的请求参数对结果进行缓存，与@Cacheable不同的是，每次都会触发真实方法的调用。

工作原理：先执行方法，然后将返回值放入缓存，常用于更新缓存

包含的参数与@Cacheable一致

> ps在同一个方法内不能同时使用@CachePut和@Cacheable注解

- @CacheEvict注解
针对方法配置，能够根据一定条件对缓存进行清空，通常用于更新或者删除操作。默认情况下，在方法调用之后运行。

常用的属性：

	value/cacheNames -- 缓存的名称  
	key -- 缓存key   
	condition -- 缓存条件，返回true才进行清除，方法返回前预先校验   
	allEnties -- 是否删除缓存中的所有元素，默认false
	beforeInvocation -- 清除缓存是否在方法执行前，默认false

- @Caching注解

@Caching是一个组注解，可以为方法定义提供基于@Cacheable、@CacheEvict、@CachePut注解的数组

- @CacheConfig注解

Spring 4.1引入，作用于类上的全局缓存注解，提供缓存名字、缓存键值策略、缓存器的全局配置。

### 缓存中的SpEL

	#root.methodname:当前被调用的方法名
	#root.method:当前被调用的方法
	#root.target:当前被调用的目标对象实例
	#root.targetClass:当前被调用的目标类
	#root.args[0]:当前被调用的参数列表，第一个参数
	#root.caches[0].name：当前被调用的缓存列表，第一个缓存的名字
	#user.id：当前被调用的方法参数，id属性
	#result：方法执行后的返回值

### Spring Cache配置

	// Xml配置方式
	xmlns:cache="http://www.springframework.org/schema/cache"

	http://www.springframework.org/schema/cache
	http://www.springframework.org/schema/cache/spring-cache.xsd

	<cache:annotation-driven/>

	// Java Config方式
	@Configuration
	@EnableCaching 

> 默认查找bean名称为cacheManager的缓存管理器


### CacheManager：缓存管理器



### 使用Redis进行数据缓存：Jedis+Spring-data-redis

	package com.wangdh.spring.cache.config;
	
	import org.springframework.cache.CacheManager;
	import org.springframework.cache.annotation.CachingConfigurerSupport;
	import org.springframework.cache.annotation.EnableCaching;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.ComponentScan;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.data.redis.cache.RedisCacheManager;
	import org.springframework.data.redis.connection.RedisClusterConfiguration;
	import org.springframework.data.redis.connection.RedisConnectionFactory;
	import org.springframework.data.redis.connection.RedisNode;
	import org.springframework.data.redis.connection.RedisSentinelConfiguration;
	import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
	import org.springframework.data.redis.core.RedisTemplate;
	import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
	import org.springframework.data.redis.serializer.StringRedisSerializer;
	
	import redis.clients.jedis.JedisPoolConfig;

	/**
	 * Redis缓存配置
	 * 
	 * @author wdhcxx
	 *
	 */
	@Configuration
	@EnableCaching
	@ComponentScan(basePackages = { "com.wangdh.spring.cache.service" })
	public class RedisCacheConfig extends CachingConfigurerSupport {
		@Bean
		public JedisConnectionFactory redisConnectionFactory() {
			JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
			
			// 单机配置
			connectionFactory.setHostName("127.0.0.1");
			connectionFactory.setPort(6379);
			
			// 设置连接池配置
		    connectionFactory.setPoolConfig(jedisConfig());
		    
		    // 设置数据库，默认0
		    connectionFactory.setDatabase(2);
			
			// 设置使用连接池
			connectionFactory.setUsePool(true); 
			
			// 哨兵配置
			RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
			RedisNode redisNode = new RedisNode("127.0.0.1", 6397);
			redisSentinelConfiguration.addSentinel(redisNode);
			
			
			// 集群配置
			RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
			redisClusterConfiguration.addClusterNode(redisNode);
	
			return connectionFactory;
		}
		
		@Bean
		public JedisPoolConfig jedisConfig(){
			JedisPoolConfig config = new JedisPoolConfig();
			
			// 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
			config.setBlockWhenExhausted(true);
			
			// 设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
			config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
			
			//最大空闲连接数, 默认8个
			config.setMaxIdle(8);
			
			//最大连接数, 默认8个
			config.setMaxTotal(10);
			
			//获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
			config.setMaxWaitMillis(-1);
			
			//逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
			config.setMinEvictableIdleTimeMillis(1800000);
			
			//最小空闲连接数, 默认0
			config.setMinIdle(0);
			
			//每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
			config.setNumTestsPerEvictionRun(3);
			
			//在获取连接的时候检查有效性, 默认false
			config.setTestOnBorrow(false);
			
			//在空闲时检查有效性, 默认false
			config.setTestWhileIdle(false);
			
			//逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
			config.setTimeBetweenEvictionRunsMillis(-1);
			
			return config;
		}
	
		@Bean
		public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
			RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
			redisTemplate.setConnectionFactory(redisConnectionFactory);
			
			// 设置key的序列化
			redisTemplate.setKeySerializer(new StringRedisSerializer());
			
			// 设置value序列化存储
			redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
	
			return redisTemplate;
		}
	
		@Bean
		public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
			RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
			
			// 设置使用CacheName作为每个key的前缀
			cacheManager.setUsePrefix(true);
	
			return cacheManager;
		}
	}


### 使用Redis进行数据缓存：Redisson

	package com.wangdh.spring.cache.config;
	
	import java.io.IOException;
	
	import org.redisson.Redisson;
	import org.redisson.api.RedissonClient;
	import org.redisson.config.Config;
	import org.redisson.spring.cache.RedissonSpringCacheManager;
	import org.springframework.cache.CacheManager;
	import org.springframework.cache.annotation.EnableCaching;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.ComponentScan;
	import org.springframework.context.annotation.Configuration;
	
	@Configuration
	@ComponentScan(basePackages = { "com.wangdh.spring.cache.service" })
	@EnableCaching
	public class RedissonConfig {
		@Bean(destroyMethod="shutdown")
	    RedissonClient redisson() throws IOException {
	        Config config = new Config();
	        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
	        
	        return Redisson.create(config);
	    }
		
		@Bean
	    CacheManager cacheManager(RedissonClient redissonClient) {
			return new RedissonSpringCacheManager(redissonClient, "classpath:/cache-config.yaml");
	    }
	}