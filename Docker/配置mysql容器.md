## 配置docker mysql容器

	1、拉取镜像:docker pull mysql
	2、启动容器:docker run -itd -p 3306:3306 mysql bash
	3、查询端口映射:docker ps -a
	4、进入mysql容器：docker exec -it hungry_northcutt bash
	5、查看mysql运行状态:service mysql status
	6、重启mysql：service mysql restart
	7、配置mysql：
	use mysql;
	update user set authentication_string = password('root') where user = 'root';
	GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'root' WITH GRANT OPTION;