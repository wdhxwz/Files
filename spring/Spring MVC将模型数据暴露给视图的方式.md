## Spring MVC将模型数据暴露给视图的方式

### 方式一：返回ModelAndView

	public ModelAndView getData() {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("home/getdata");
			modelAndView.addObject("name", "wangdh");
			modelAndView.addObject("age", "12");
	
			return modelAndView;
		}


### 方式二：@ModelAttribute注解

被@ModelAttribute注释的方法会在此controller每个方法执行前被执行,需慎用

	@ModelAttribute
	public void populateModel(@RequestParam String abc, Model model) {
		model.addAttribute("attributeName", abc);
	}

方法入参标注该注解后，入参的对象就会放到数据模型中；

	@RequestMapping("/getdata")
		public ModelAndView getData(@ModelAttribute(name = "name") String test, int age) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("home/getdata");
			modelAndView.addObject("age", age);
	
			return modelAndView;
		}

> @ModelAttribute name指绑定参数的名称和暴露出去的数据的名字，test在这里会绑定传递参数名为name的值

### 方式三：Map 及 Model

入参为org.springframework.ui.Model、org.springframework.ui.ModelMap或java.util.Map时，处理方法返回时，Map中的数据会自动添加到模型中；

	@RequestMapping("/getdata")
		public String getData(ModelMap map, @RequestParam Map<String, String> requestMap) {
			map.put("aaa", "gegeg");
			
			return "home/getdata";
		}

> 在代码中向上面三种类型的参数添加数据时，会自动添加待模型中

### 方法四：@SessionAttributes

 在默认情况下，ModelMap 中的属性作用域是 request 级别是，也就是说，当本次请求结束后，ModelMap 中的属性将销毁。如果希望在多个请求中共享 ModelMap 中的属性，必须将其属性转存到 session  中，这样 ModelMap 的属性才可以被跨请求访问;将模型中的某个属性暂存到HttpSession中，以便多个请求之间共享这个属性；

	1、names：这是一个字符串数组。里面应写需要存储到session中数据的名称。 	　 
	2、types：根据指定参数的类型，将模型中对应类型的参数存储到session中  		 　 
	3、value：其实和names是一样的。  





> 控制器方法返回字符串类型的值会被当成逻辑视图名处理。当字符串带"forward"或"redirect"前缀时，redirect会让浏览器发起一个新的请求，而forward所到的目标地址位于当前请求中。