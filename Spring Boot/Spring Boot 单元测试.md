## Spring Boot 单元测试

单元测试使用Junit进行

### 添加依赖

	<!-- 单元测试依赖 -->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-test</artifactId>
		<scope>test</scope>
	</dependency>
 
### 编写测试类

	@RunWith(SpringJUnit4ClassRunner.class)
	@SpringApplicationConfiguration(classes=App.class)
	// 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
	public class StudentServiceTest {
		@Autowired
		private StudentService studentService;
		
		@Test
		public void get(){
			Student student = studentService.get("27fe5b1ab3a146c0ae7a90087c7ae236");
			
			Assert.assertNotNull(student);
		}
	}

### 常用注解

	//在所有测试方法前执行一次，一般在其中写上整体初始化的代码 
	@BeforeClass
	 
	//在所有测试方法后执行一次，一般在其中写上销毁和释放资源的代码 
	@AfterClass
	 
	// 在每个测试方法前执行，一般用来初始化方法（比如我们在测试别的方法时，类中与其他测试方法共享的值已经被改变，为了保证测试结果的有效性，我们会在@Before注解的方法中重置数据） 
	@Before
	 
	//在每个测试方法后执行，在方法执行完成后要做的事情 
	@After
	 
	// 测试方法执行超过1000毫秒后算超时，测试将失败 
	@Test(timeout = 1000)
	 
	// 测试方法期望得到的异常类，如果方法执行没有抛出指定的异常，则测试失败 
	@Test(expected = Exception.class)
	 
	 
	// 执行测试时将忽略掉此方法，如果用于修饰类，则忽略整个类 
	@Ignore(“not ready yet”) 
	
	@Test
	 
	@RunWith 
	在JUnit中有很多个Runner，他们负责调用你的测试代码，每一个Runner都有各自的特殊功能，你要根据需要选择不同的Runner来运行你的测试代码。    
	如果我们只是简单的做普通Java测试，不涉及Spring Web项目，你可以省略@RunWith注解，这样系统会自动使用默认Runner来运行你的代码。