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

execution( \<scope> <return-type> <fully-qualified-class-name>.<function-name>(parameters))

execution(* com.wangdh.spring.aop.test(..))

通配符：

*：该通配符匹配任何数量的字符
<br/>+ ：该通配符匹配给定类的任何子类
<br/>..：该通配符匹配方法中任何数量的参数，此外还匹配类定义中任何数量的包(包的时候不能放在开头)

例子：

execution(* com.wangdh.learner.spring.aop.MyBean.*(..)) 匹配MyBean中的所有方法

execution(public * com.wangdh.learner.spring.aop.MyBean.*(..)) 匹配MyBean中的所有公共方法

execution(public String com.wangdh.learner.spring.aop.MyBean.*(..)) 匹配MyBean中返回String的所有公共方法

execution(public * com.wangdh.learner.spring.aop.MyBean.*(String,..)) 匹配MyBean中第一个参数被定义为String的所有公共方法




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

######相关示例代码

	/**
	 * @author 王东鸿
	 * @Copyright (c) 2016, frontpay.cn
	 * @date 2016年11月10日 下午3:36:28
	 */
	public class StudentServiceAspect {
		public void doBefore(JoinPoint joinPoint) {
			String className = joinPoint.getTarget().getClass().getName();
			System.out.println("类名：" + className);
			String methodName = joinPoint.getSignature().getName();
			System.out.println("方法名：" + methodName);
			Object[] args = joinPoint.getArgs();
			int index = 1;
			for (Object object : args) {
				System.out.println("参数" + index + ":" + object.toString());
				index++;
			}
	
			// 连接点类型
			System.out.println("连接点类型:" + joinPoint.getKind());
	
			// 执行切面逻辑的类
			System.out.println("执行切面逻辑的类:" + joinPoint.getTarget());
	
			// 执行切面的方法
			System.out.println("执行切面的方法:" + joinPoint.getSignature());
	
			// 这个不知是啥:
			System.out.println("这个不知是啥:" + joinPoint.getSourceLocation());
	
			// 这个也不知是啥
			System.out.println("这个也不知是啥:" + joinPoint.getStaticPart());
		}
	
		public void doAfter(JoinPoint joinPoint, Object returnValue) {
			String className = joinPoint.getTarget().getClass().getName();
			System.out.println("类名：" + className);
			String methodName = joinPoint.getSignature().getName();
			System.out.println("方法名：" + methodName);
			System.out.println("返回值:" + JSON.toJSON(returnValue));
		}
	
		public void after(JoinPoint joinPoint) {
			System.out.println("最终返回后执行(不管是正常执行还是抛异常)");
		}
	
		public void doException(Exception e) {
			System.out.println("抛出异常后执行:" + e.getMessage());
		}
	
		public void doAround() {
			System.out.println("环绕通知");
		}
	}

### 注解编写Spring AOP
Spring AOP有如下的注解：
>http://blog.csdn.net/evankaka/article/details/45394409

|注解|说明|
|:-|:-|
|@Aspect|标注一个类为切面类|
|@Pointcut（value="切入点表达式", argNames = "参数名列表")|声明一个切入点，该切入点可被共用，切入点作用的函数名称就是该切入点的id|
|@Before(value = "切入点表达式或命名切入点", argNames = "参数列表参数名")  |声明一个前置通知|
|@AfterReturning(value="切入点表达式或命名切入点", pointcut="切入点表达式或命名切点", argNames="参数列表参数名", returning="返回值对应参数名")|声明一个后置返回通知|
|@AfterThrowing(value="切入点表达式或命名切入点",pointcut="切入点表达式或命名切入点",    argNames="参数列表参数名",throwing="异常对应参数名")|声明一个后置异常通知|
|@After(value="切入点表达式或命名切入点",argNames="参数列表参数名") |声明一个后置最终通知|
|@Around (value="切入点表达式或命名切入点",argNames="参数列表参数名")  |声明一个后置通知|

######下面是示例代码

	/**
	 * 定义成切面，同时也是一个组件
	 * 
	 * @author 王东鸿
	 * @Copyright (c) 2016, frontpay.cn
	 * @date 2016年11月29日 下午4:17:41
	 */
	@Aspect
	@Component
	public class AnnotationAspect {
		/**
		 * 定义切点，该方法的名称就是该切点的id
		 */
		@Pointcut("execution(* com.wangdh.spring.aop.demo.*.*(..))")
		public void pointCut() {
	
		}
	
		@Before(value = "execution(* com.wangdh.spring.aop.demo.*.*(..))", argNames = "")
		public void doBefore(JoinPoint joinPoint) {
			System.out.println("开始执行：" + joinPoint.getSignature().toString());
		}
	
		@After(value = "pointCut()", argNames = "")
		public void doAfter(JoinPoint joinPoint) {
			System.out.println("执行结束：" + joinPoint.getSignature().toString());
		}
	
		@AfterReturning(pointcut = "pointCut()", returning = "result", argNames = "")
		public void doAfterReturning(JoinPoint joinPoint, Object result) {
			System.out.println("执行结束：" + joinPoint.getSignature().toString() + ",返回值：" + result);
		}
	
		@AfterThrowing(pointcut = "pointCut()",throwing = "ex")
		public void doThrowing(JoinPoint joinPoint, Throwable  ex) {
			System.out.println("执行方法：" + joinPoint.getSignature() + "，发生异常：" + ex.getMessage());
		}
	
		/**
		 * 有这个之后异常不处理了
		 */
		//	@Around(argNames = "", value = "pointCut()")
		//	public void doAround(JoinPoint joinPoint) {
		//
		//	}
	}

######相关的配置文件如下

	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xmlns="http://www.springframework.org/schema/beans" 
		xmlns:context="http://www.springframework.org/schema/context"
		xmlns:aop="http://www.springframework.org/schema/aop"
		xsi:schemaLocation="http://www.springframework.org/schema/beans 
	     http://www.springframework.org/schema/beans/spring-beans-4.1.xsd
	     http://www.springframework.org/schema/aop
	     http://www.springframework.org/schema/aop/spring-aop.xsd
	     http://www.springframework.org/schema/context 
	     http://www.springframework.org/schema/context/spring-context.xsd">
		<!-- 指定组件扫描的包 -->
		<context:component-scan base-package="com.wangdh.spring.aop.demo"/>
		<!-- 开启aop自动代理 -->
		<aop:aspectj-autoproxy proxy-target-class="true"/>
	</beans>


### Aop接口

|类型|接口|执行点|
|:-|:-|:-|
|Before|org.springframework.aop.MethodBeforeAdvice|在接合点之前通知|
|After Returning|org.springframework.aop.AfterReturningAdvice|在接合点执行完之后通知|
|After Throwing|org.springframework.aop.ThrowsAdvice|接合点抛出异常后通知|
|After(Finally)|N/A|接合点执行完毕后通知，不管是正常执行还是抛异常|
|Around|N/A|在接合点周围通知，可以在接合点之前及之后执行通知|


- ThrowsAdvice接口

ThrowsAdvice接口只是一个标记接口，并不含任何方法签名，当实现了该接口的类，必须需要实现下面所示方法的一个，所实现的方法将被反射调用：

public void afterThrowing(Exception ex);

public void afterThrowing(RemoteException);

public void afterThrowing(Method method,Object[] args,Object target,Exception ex);

public void afterThrowing(Method method,Object[] args,Object target,ServletException ex);

