## Spring MVC处理流程

Spring MVC 是一个基于MVC的web框架，是Spring框架的一个模块,在Spring中的位置如下：

![](http://i.imgur.com/b2aWiAI.png)

Spring MVC的执行流程如下图：


![](http://i.imgur.com/PUTcvu9.png)


1. 客户端发送一个HTTP请求，Web服务器接收到请求后，如果匹配前端控制器的请求映射路径(web.xml中配置)，Web容器会将请求转发给前端控制器；如上图的1。
2. 前端控制器接收到请求后，根据请求的信息(url、HTTP方法、请求报文头、请求参数、Cookie等)及HandlerMapping(相当于路由控制器)的配置找到处理请求的页面控制器(Controller)；如上图的2。
3. 页面控制器接收到请求后，进行功能处理；首先是收集和绑定请求参数到命令对象，并进行验证，然后将命令对象委托给业务对象进行处理；处理完成后返回相关数据给页面控制器，页面控制器封装成ModelAndView(包含视图名称和模型数据)对象返回给前端控制器，如图所示的3,4,5
4. 前端控制器会调用视图解析 器(ViewResolver)，根据视图名称和模型数据渲染出视图(可能是HTML页面、JSON数据或XML数据)，并返回给浏览器。如上图的6,7,8。





