## Spring Boot 使用SQLServer

### 依赖包

	<sqlServer.version>4.0.2206.100</sqlServer.version>
	<druid.version>1.0.18</druid.version>
	
	<!-- JdbcTemplate依赖 -->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-data-jpa</artifactId>
	</dependency>

	<dependency>
		<groupId>com.alibaba</groupId>
		<artifactId>druid</artifactId>
		<version>${druid.version}</version>
	</dependency>

	<!-- SqlServer 驱动 -->
	<dependency>
		<groupId>com.hynnet</groupId>
		<artifactId>sqljdbc4-chs</artifactId>
		<version>${sqlServer.version}</version>
	</dependency>

### 连接配置

	########################################################
	# datasource
	# Spring Boot默认的数据源是：org.apache.tomcat.jdbc.pool.DataSource
	########################################################
	spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
	
	# 连接SQLServer
	spring.datasource.url = jdbc:sqlserver://127.0.0.1:1433;databasename=MybatisDB;
	spring.datasource.username = sa
	spring.datasource.password = 1q2w#E$R
	spring.datasource.driverClassName = com.microsoft.sqlserver.jdbc.SQLServerDriver

###  JdbcTemplate 方式操作

实体类

	private String name;
	private String email;
	private Date birthday;

数据库操作类

	@Repository
	public class StudentDao {
	
		@Autowired
		private JdbcTemplate jdbcTemplate;
	
		public Student get(String id){
			String sql = "SELECT [Id],[name],[email],[birthday] FROM [MybatisDB].[dbo].[tb_student] WHERE Id = ?";
			RowMapper<Student> rowMapper = new BeanPropertyRowMapper<>(Student.class);
			
			// 返回一条结果，相当于select one
			return jdbcTemplate.queryForObject(sql, rowMapper,id);
		}
	}

数据服务类

	@Service
	public class StudentService {

		@Autowired
		private StudentDao studentDao;
		
		
		public Student get(String id){
			return studentDao.get(id);
		}
	}




