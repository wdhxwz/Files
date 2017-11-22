## 第二章 Shell字符串处理之${}

用${}引用变量，${}还有一个重要的功能，就是文本处理，单行文本基本上可以满足你所有需求

### 2.1 获取字符串长度

	# VAR='hello world!'
	# echo $VAR
	hello world!
	# echo ${#VAR}
	12

### 2.2 字符串切片

	格式：
	${parameter:offset}
	${parameter:offset:length}
	截取从offset个字符开始，向后length个字符。
	
	截取hello字符串：
	# VAR='hello world!'
	# echo ${VAR:0:5}
	hello
	截取wo字符：
	# echo ${VAR:6:2}
	wo
	截取world!字符串：
	# echo ${VAR:5}
	world!
	截取最后一个字符：
	# echo ${VAR:(-1)}
	!
	截取最后二个字符：
	# echo ${VAR:(-2)}
	d!
	截取从倒数第3个字符后的2个字符：
	# echo ${VAR:(-3):2}
	ld

### 2.3 替换字符串

	格式：${parameter/pattern/string}

	# VAR='hello world world!'
	将第一个world字符串替换为WORLD：
	# echo ${VAR/world/WORLD}
	hello WORLD world!
	将全部world字符串替换为WORLD：
	# echo ${VAR//world/WORLD}
	hello WORLD WORLD!

### 2.4 字符串截取

	格式：
	${parameter#word}   # 删除匹配前缀
	${parameter##word}  
	${parameter%word}   # 删除匹配后缀
	${parameter%%word}
	# 去掉左边，最短匹配模式，##最长匹配模式。
	% 去掉右边，最短匹配模式，%%最长匹配模式。

	# URL="http://www.baidu.com/baike/user.html"
	以//为分隔符截取右边字符串：
	# echo ${URL#*//}         
	www.baidu.com/baike/user.html
	以/为分隔符截取右边字符串：
	# echo ${URL##*/}
	user.html
	以//为分隔符截取左边字符串：
	# echo ${URL%%//*}     
	http:
	以/为分隔符截取左边字符串：
	# echo ${URL%/*}
	http://www.baidu.com/baike
	以.为分隔符截取左边：
	# echo ${URL%.*}
	http://www.baidu.com/baike/user
	以.为分隔符截取右边：
	# echo ${URL##*.}
	html

	# 去掉左边，从左边匹配第一个，##从右边匹配第一个。
	% 去掉右边，从右边匹配第一个，%%从左边匹配第一个。
	有*号情况下才这样。

### 2.5 变量状态赋值

	${VAR:-string}  如果VAR变量为空则返回string
	${VAR:+string}  如果VAR变量不为空则返回string
	${VAR:=string}  如果VAR变量为空则重新赋值VAR变量值为string
	${VAR:?string}  如果VAR变量为空则将string输出到stderr

	如果变量为空就返回hello world!：
	# VAR=
	# echo ${VAR:-'hello world!'}
	hello world!
	如果变量不为空就返回hello world!：
	# VAR="hello"
	# echo ${VAR:+'hello world!'}
	hello world!
	如果变量为空就重新赋值：
	# VAR=
	# echo ${VAR:=hello}
	hello
	# echo $VAR
	hello
	如果变量为空就将信息输出stderr：
	# VAR=
	# echo ${VAR:?value is null}   
	-bash: VAR: value is null






