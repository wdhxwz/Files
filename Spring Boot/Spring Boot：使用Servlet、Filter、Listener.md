## Spring Boot：使用Servlet、Filter、Listener

Web开发使用 Controller 基本上可以完成大部分需求，但是还可能会用到 Servlet、Filter、Listener等等。

当使用Spring-Boot时，嵌入式Servlet容器通过扫描注解的方式注册Servlet、Filter和Servlet规范的所有监听器，或者手动的进行注册。   
Spring boot 的主 Servlet 为 DispatcherServlet，其默认的url-pattern为“/”。

### 手动注册

代码注册通过ServletRegistrationBean、 FilterRegistrationBean 和 ServletListenerRegistrationBean 获得控制；也可以通过实现 ServletContextInitializer 接口直接注册。

	public class MyServlet extends  HttpServlet{
		private static final long serialVersionUID = 6189879982189860497L;
	
		@Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        System.out.println(">>>>>>>>>>doGet()<<<<<<<<<<<");
	        doPost(req, resp);
	    }
	 
	    @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        System.out.println(">>>>>>>>>>doPost()<<<<<<<<<<<");
	        resp.setContentType("text/html"); 
	        PrintWriter out = resp.getWriter(); 
	        out.println("<html>"); 
	        out.println("<head>"); 
	        out.println("<title>Hello World</title>"); 
	        out.println("</head>"); 
	        out.println("<body>"); 
	        out.println("<h1>这是：MyServlet</h1>"); 
	        out.println("</body>"); 
	        out.println("</html>");
	    }
	}

	@SpringBootApplication
	public class App {
		@Bean
		public ServletRegistrationBean MyServlet(){
			// 注册Servlet,并配置该Servlet的访问路径
			return new ServletRegistrationBean(new MyServlet(),"/myservlet/*");
		}
		
		
		/**
		 * 程序扫描的包默认是执行类所在包及其子包
		 */
		public static void main(String[] args) {		
			 SpringApplication.run(App.class, args);  
		}
	}

### 注解自动注册

使用@ServletComponentScan注解后，Servlet、Filter、Listener 可以直接通过 @WebServlet、@WebFilter、@WebListener 注解自动注册，无需其他代码。

	@WebServlet(urlPatterns="/myServlet2/*", description="Servlet的说明")
	public class MyServlet extends  HttpServlet{
		private static final long serialVersionUID = 6189879982189860497L;
	
		@Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        System.out.println(">>>>>>>>>>doGet()<<<<<<<<<<<");
	        doPost(req, resp);
	    }
	 
	    @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        System.out.println(">>>>>>>>>>doPost()<<<<<<<<<<<");
	        resp.setContentType("text/html"); 
	        PrintWriter out = resp.getWriter(); 
	        out.println("<html>"); 
	        out.println("<head>"); 
	        out.println("<title>Hello World</title>"); 
	        out.println("</head>"); 
	        out.println("<body>"); 
	        out.println("<h1>这是：MyServlet</h1>"); 
	        out.println("</body>"); 
	        out.println("</html>");
	    }
	}

	@SpringBootApplication
	@ServletComponentScan
	public class App {
		/**
		 * 程序扫描的包默认是执行类所在包及其子包
		 */
		public static void main(String[] args) {		
			 SpringApplication.run(App.class, args);  
		}
	}


