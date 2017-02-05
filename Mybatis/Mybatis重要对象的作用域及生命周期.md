## Mybatis重要对象的作用域及生命周期

Mybatis包含的主要对象有：SqlSessionFactoryBuider、SqlSessionFactory、SqlSession、Mapper实例。理解它们各自的作用域，可以避免不必要的问题出现。

-  SqlSessionFactoryBuilder

该类只要用来创建SqlSessionFactory实例，创建完之后就可以不再保留它，因此该对象最好的作用域是方法体内(定义成一个方法的临时变量)。

- SqlSessionFactory 

该类只要是用来创建SqlSession对象，一旦创建，SqlSessionFactory对象就会在整个应用程序中始终存在，最好的作用域是Application，可以使用单例或静态单例模式来创建该对象。

- SqlSession

该类封装类进行sql操作的内容，并且不是线程安全的，不能被共享，最好的作用域级别是Request或者方法体内；使用完后需要关闭SqlSession.

- Mapper实例

Mapper是用于绑定映射语句的接口，mapper接口实例可以通过SqlSession来获得，其作用域与SqlSession一样，使用请求级别作用域。



















