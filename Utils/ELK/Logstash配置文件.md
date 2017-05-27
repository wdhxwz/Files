## Logstash配置文件

### 配置文件的结构

Logstash配置文件的结构如下：

	# 这是注释
	input {
	  ...
	}
	
	filter {
	  ...
	}
	
	output {
	  ...
	}

### 插件类型

	input插件：输入插件，配置日志内容的来源
	output插件：输出插件，配置处理后日志的保存位置
	filter插件：过滤插件
	codec插件：日志的格式化

### 数据类型

配置文件可以有下面的数据类型：

	数组 ： user => [{id => 1,name = bob},{id = 2,name = jane}]
	列表 : path =>["/home/wangdh/logs","/data/apps/dubboadmin"]
	布尔值 ： ssl_enable = true
	字符串：codec => "json"
	字典 ： match => { 
				"name" => "wangdh"
				"age" => "25"
			}
	数值 ： port => 33
	密码 ： my_password => "password"
	URI ： uri => "http://www.baidu.com"
	路径 ： path => "/tmp/logstash"
	注释 ：  # 这是个注释

### 输入插件

支持的插件列表见：https://www.elastic.co/support/matrix#show_logstash_plugins，下面是常用的插件：















