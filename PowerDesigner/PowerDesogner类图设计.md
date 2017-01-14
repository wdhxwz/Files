### PowerDesigner类图设计

- 新建类图

		新建-->选择模型类型为：Object-Oriented Model-->设置名称并选择对应语音(用于代码生成)

#### SSO客户端提供的功能

- 登陆，验证(自定义ticket设置，获取，如何共享ticket)
- 注销
- 验证登陆状态
- 第三方登陆(在第三方登陆完成后，将loginName传到sso中生成相应ticket)


#### SSO过滤器

过滤请求，根据配置的ignoreUrl决定是否跳过

向服务端验证登陆状态

#### 配置文件sso.properties

cookie_domain:cookie保存的域

sso_server_url:sso服务端地址，绝对路径

sso_ignore_urls:不进行sso验证的url(相对路径)，逗号分隔

app_id:系统应用编号

app_login_url:应用的登陆验证地址，相对地址

app_logout_url:应用的登出地址，相对地址

app_login_page_url:应用的登陆页面地址，相对路径

third_auth_url:第三方登陆验证地址

loginActionClass:登录前后操作类，需继承ILoginAction接口，可不配置

logoutActionClass:登出前后操作类，需继承ILogoutAction接口，可不配置

ticketStoreClass:ticket存储操作类，需继承ITicketStore接口，不配置时存储在Cookie中，需配置ticketStoreLocationClass一起使用

ticketStoreLocationClass:ticket存储类，不配置时为HttpServletResponse















