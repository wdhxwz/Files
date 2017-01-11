### Mybatis概要

Mybatis是一个开源的，轻量级的持久化框架。能自动的在sql数据库和java对象之间进行映射；通过将sql语句打包到配置文件中来对程序逻辑进行解耦。

Mybatis对几乎所有的JDBC代码进行了封装，并减轻了手动设置参数和获取结果的工作；通过提供一组简单的API来与数据库交互，并且对自定义sql/存储过程和高级映射提供了支持。

Mybatis与其他持久化框架相比较，mybatis着重强调sql的使用，其他框架如Hibernate更多的是使用自己的查询语言HQL（hibernate query language）。

#### Mybatis的特点

- 简单
- 快速开发
- 轻便
- 独立接口
- 开源

#### Mybatis的优点

- 支持存储过程
- 支持内联sql
- 支持动态sql：基于参数动态的构建sql语句
- 支持ORM：延迟加载，连接获取，缓存，运行时代码生成和继承