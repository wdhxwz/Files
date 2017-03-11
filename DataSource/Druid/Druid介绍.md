## Druid介绍

### Druid是什么

Druid是阿里巴巴开源平台上的一个项目，整个项目由数据库连接池、插件框架和SQL解析器组成。该项目主要是为了扩展JDBC的一些限制，可以让程序员实现一些特殊的需求，比如向密钥服务请求凭证、统计SQL信息、SQL性能收集、SQL注入检查、SQL翻译等，程序员可以通过定制来实现自己需要的功能。

Druid是一个JDBC组件，包括下面三部分：

- DruidDriver：代理Driver，能够提供基于Filter-Chain模式的插件体系
- DruidDataSource：高效可管理的数据库连接池
- SQLParser

### Druid能做什么

- 可以监控数据库访问性能：Druid内置提供了一个功能强大的StatFilter插件，能够详细统计SQL的执行性能，可以帮助分析线上数据库访问性能
- 替换DBCP和C3P0：Druid提供了一个高效、功能强大、可扩展性好的数据库连接池
- 数据库密码加密：直接将数据库密码写在配置文件中，是不好的行为，容易导致安全问题。DruidDriver和DruidDataSource都支持PasswordCallBack
- 记录SQL执行日志：Druid提供了不同的LogFilter，能够支持Common-Logging、Log4j、JDKLog，可以根据需要选择相应的LogFilter，来监控数据库的访问情况

### 如何使用Druid







