## Mybatis问题集锦

### Mybatis缓存禁用失败

默认情况下，Mybatis开启了一级缓存，即使将cacheEnabled设置为false，且对应的select语句加上useCache="false"标签，还是无法禁用缓存。

Mybatis还有一个配置localCacheScope，定义了本地的缓存机制，可取值是：SESSION|STATEMENT，默认是SESSION，这种情况下会缓存一个会话中执行的所有查询；若设置值为 STATEMENT，本地会话仅用在语句执行上，对相同 SqlSession 的不同调用将不会共享数据。

当需要禁用缓存时，需要同时作下面的设置：

	<settings>
		<setting name="cacheEnabled" value="false" />
		<setting name="localCacheScope" value="STATEMENT" />
	</settings>










