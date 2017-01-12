### Spring Cache:Spring 缓存



#### Spring Cache注解

- @Cacheable注解

针对方法配置，能根据方法的请求参数对结果进行缓存




- @CachePut注解

针对方法配置，能根据方法的请求参数对结果进行缓存，与@Cacheable不同的是，每次都会触发真实方法的调用。


- @CacheEvict注解

针对方法配置，能够根据一定条件对缓存进行清空