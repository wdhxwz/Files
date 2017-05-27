## Logstash安装

### 前期准备

	所需环境：JDK 1.8 及以上
	安装版本：5.4.0
	下载安装包：wget https://artifacts.elastic.co/downloads/logstash/logstash-5.4.0.tar.gz

### 安装

	tar zxvf logstash-5.4.0.tar.gz -C /usr/local
	cd /usr/local/logstash-5.4.0
    bin/logstash -f config/logstash-sample.conf 


### 配置文件logstash-sample.conf

	input {
	    stdin {
	
	    }
	}
	
	output {
	    stdout {
	       codec => "json"
	 }
	}

### 常用参数

	-f : 指定配置文件
	-e : 后面跟着字符串，被当做logstash的配置，如果是"" 则默认使用stdin作为输入，stdout作为输出
	-I : 日志输出的地址，默认是stdout直接在控制台中输出
	-t ： 测试配置文件是否正确，然后退出：bin/logstash -f config/logstash-sample.conf -t

	logstash stop 停止
