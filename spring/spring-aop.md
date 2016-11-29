## Spring中的AOP
> AOP：Aspect Oriented Programming(面向切面编程)

### AOP术语

1. *通知(Advice)*
 切面要执行的工作以及什么时候执行
 spring切面有*5* 中类型的通知：
 - *前置通知(Before)*：目标方法被调用之前调用通知功能
 - *后置通知(After)*： 目标方法完成之后调用通知(正常执行或抛异常都会执行)
 - *返回通知(After-returning)*：目标方法成功执行之后调用
 - *异常通知(After-throwing)*：目标方法抛出异常后调用(如果异常被catch了则不会执行)
 - *环绕通知(Around)*：通知包裹了被通知的方法，在被通知的方法调用之前和之后执行自定义的行为
2. *连接点(Join point)*
 应用执行过程能够插入的一个点(某个方法)
3. *切点(Pointcut)*
 切点的定义会匹配一个或多个连接点(匹配连接点的规则)
4. *切面(Aspect)*
 通知和切点的结合，在何时何处完成什么功能
5. *织入(Weaving)*
 把切面应用到目标对象并创建代理的过程，织入的时间点：
 - *编译器*：切面在目标类编译器被织入
 - *类加载器*：切面在目标类加载到JVM时被织入
 - *运行期*：切面在应用运行的某个时刻被织入(Spring Aop是这种方法)

### Spring对AOP的支持

1. Spring AOP构建在动态代理之上，因此只局限于方法拦截，Spring提供4种类型的AOP支持：
 - 基于代理的经典Spring AOP
 - 纯POJO切面
 - @AspectJ注解驱动的切面
 - 注入式Aspect切面(XML配置，适合Spring各版本)
2. Spring在运行时通知对象
通过在代理类中包裹切面，Spring在运行期把切面织入到Spring管理的bean中。代理类封装了目标类并拦截被通知方法的调用，再把调用转发给真正的目标bean；当代理拦截到方法调用时，在调用目标bean之前会执行切面逻辑。

### Spring 切点

1. 指示器
 <br/>Spring AOP仅支持AspectJ部分的指示器，下面是Spring AOP支持的指示器：

|AspectJ指示器|描述|
|-|-|
|arg() |限制连接点匹配参数为指定类型的方法|
|@args() |限制连接点匹配参数由指定注解的方法|
|execution()|用于匹配连接点|
|this()||
|target||
|@target()||
|within()||
|@within()||
|@annotation|限定匹配带有指定注解的连接点|

>备注：execution指示器是实际执行匹配的，而其他只是器是限制匹配的

2. 编写切点
<br/>execution(* com.wangdh.spring.aop.test(..))

*：表示返回值的类型为任意或空
<br/>只匹配test方法
<br/>..：表示匹配任意参数
### XML中配置AOP

	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xmlns="http://www.springframework.org/schema/beans" 
		xmlns:aop="http://www.springframework.org/schema/aop" <!-- 引入AOP命名空间 -->
		xsi:schemaLocation="http://www.springframework.org/schema/beans 
	     http://www.springframework.org/schema/beans/spring-beans-4.1.xsd
	     http://www.springframework.org/schema/aop  <!-- 引入AOP -->
	     http://www.springframework.org/schema/aop/spring-aop.xsd">
		<bean id="studentService" class="com.wangdh.spring.aop.demo.StudentServiceImp"></bean>		

		<!-- 启动AspectJ自动代理 -->
		<aop:aspectj-autoproxy proxy-target-class="true" expose-proxy="true" />  
		<!-- 切面类 -->
		<bean id="studentServiceAspect" class="com.wangdh.spring.aop.demo.StudentServiceAspect"></bean>
		<aop:config proxy-target-class="true">
			<aop:aspect id="studentServiceAspect" ref="studentServiceAspect">
				<!-- 定义切点,指定切入点表达式 -->
				<aop:pointcut expression="execution(* com.wangdh.spring.aop.demo.*.*(..))"
					id="businessService" />
				<!-- 在连接点之前执行 -->
				<aop:before method="doBefore" pointcut-ref="businessService" />
				<!-- 在连接点正常完成后执行 returning的值必须与方法的参数名一致-->
				<aop:after-returning method="doAfter"
					pointcut-ref="businessService" returning="returnValue" />
				<!-- 最终返回后执行(不管是正常执行还是抛异常) -->
				<aop:after method="after" pointcut-ref="businessService"/>
				<!-- 抛出异常后执行 throwing的值必须与方法的参数名一致-->
				<aop:after-throwing method="doException" pointcut-ref="businessService" throwing="e"/>
				<!-- 环绕通知 
				<aop:around method="doAround" pointcut-ref="businessService"/>-->
			</aop:aspect>
		</aop:config>
	</beans>

上面是一个使用xml配置spring aop的示例。首先需要引入AOP命名空间，其次当需要返回值时需要指定returning的值为形参的名字，throwing也是一样，下面是Spring AOP的配置元素：

|AOP配置元素|用途|
|-|-|
|<aop:advisor>|定义AOP通知器|
|<aop:after>|定义AOP后置通知，不管被通知的方法是否执行成功
|<aop:aftet-returning>|定义AOP返回通知，被通知的方法成功执行|
|<aop:after-throwing>|定义AOP异常通知|
|<aop:around>|定义AOP环绕通知|
|<aop:aspect>|定义一个切面|
|<aop:aspectj-autoproxy>|启动@AspectJ注解驱动的切面|
|<aop:before>|定义AOP前置通知|
|<aop:config>|顶层的AOP配置元素，大多数<aop:*>元素必须包含在该元素内|
|<aop:declare-parents>|以透明的方式为被通知的对象引入额外的接口|
|<aop:pointcut>|定义一个切点|

### 注解编写Spring AOP
Spring AOP有如下的注解：
>http://blog.csdn.net/evankaka/article/details/45394409

|注解|说明|
|-|-|
|@Aspect|标注一个类为切面类|