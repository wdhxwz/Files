### 数据类型

redis并不是一个简单的key-value存储，而是一个数据结构服务器，其value的数据类型不仅仅是string，还可以是其他复杂类型，下面是redis支持的数据结构：

- string：二进制安全
- Lists：String列表，根据插入排序
- Sets：唯一、无序的字符串集合
- Sorted Sets：唯一，有序的字符串集合，可以为每个元素赋值一个float类型的score值，用于排序
- Hashes：
- Bit arrays(simply bitmaps)：
- HyperLogLogs：

### key

关于redis的key，有如下原则：

- 空字符串也是一个有效的key
- key的最大长度是512M
- 建议适用可读性强的命名，如：object-type:id,eg:user:1000

### 过期时间

当key超时后，会被删除。设置过期时间有两种方式：对已存在的key使用expire命令，创建key的时候用参数ex指定过期时间。过期时间对所有的redis类型都适用
	
	>expire key seconds -- 为已存在的key设置过期时间，单位秒
	>set key value ex seconds -- 创建key的时候设置过期时间，单位秒
	>ttl key -- 查看指定key的剩余时间，-1 表示永不超时，-2表示已过期

### String

