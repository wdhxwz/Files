## ROP安全控制

ROP框架通过下面5个方面来保证安全：

- 应用键和密钥管理：定位应用
- 会话管理：定位哪个用户
- 文件上传控制器：限制文件大小和格式
- 服务访问控制器：现在哪个应用或哪个用户可以访问服务
- 服务调用频率限制：限制访问额度

### 应用键/密钥管理器

接口 com.rop.security.AppSecretManager 负责检查 appKey/secret 的安全性，同时 SecurityManager 会在 AppSecretManager 配合下完成请求数据的签名。  
ROP默认提供了一个基于文件存储的FileBaseAppSecretManager实现类，读取类路径下的rop.appSecret.properties属性文件，该文件采用如下方式保存：

	appKey=appSecret

- 自定义AppSecretManager

通过 <rop:annotation-driver/> app-secret-manager属性进行配置，如：

	<rop:annotation-driven app-secret-manager="appSecretManager"/>
	<bean id="appSecretManager" class="com.wangdh.learner.rop.manager.SampleAppSecretManager" />

- 请求数据签名

对签名数据的验证，相当于验证应用的appSecret

### 会话管理

当一个服务需要在会话状态下调用时，需要传递参数:sessionId
