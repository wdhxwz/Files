## Shell基础知识

### 1.1 简介

Shell是一个C语言编写的脚本语言，它是用户与Linux的桥梁，用户输入命令交给Shell处理，Shell将相应的操作传递给内核（Kernel），内核把处理的结果输出给用户。

### 1.2第一个Shell脚本

	# vim test.sh
	#!/bin/bash
	echo "Hello world"

执行Shell脚本的方式

	1. bash test.sh     // 不需要可执行权限  
	2. source test.sh   // 不需要可执行权限
	3. chmod +x test.sh // 添加可执行权限
	4. ./test.sh        // 需要可执行权限
	5. sh test.sh       // 需要可执行权限
 

### 1.3 Shell变量

#### 系统变量

编写shell脚本常用的系统变量：

|$SHELL||
|||




































> 参考连接：http://lizhenliang.blog.51cto.com/7876557/1881437