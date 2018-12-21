1.代码生成器：基于MybatisGenerator

配置信息
1.1 数据库类型([mysql|sqlserver])
1.2 数据库连接：url/账号/密码/数据库名称/数据表
1.3 生成代码范围：model/dao/service/api/dto/vo
1.4 包名配置(支持为每个代码范围做配置)
1.5 文件生成后，打包成zip文件

-- 获取数据库、表、列
SHOW DATABASES;
SELECT table_name FROM information_schema.tables WHERE table_schema='pushsystem';
SELECT table_name,table_comment FROM information_schema.tables WHERE table_schema='pushsystem';
SELECT column_name,ordinal_position,column_default,data_type,column_comment FROM information_schema.columns WHERE table_schema ='pushsystem'  AND table_name = 'push_app';



https://blog.csdn.net/taxuexumei/article/details/80362706
https://blog.csdn.net/qq_27529917/article/details/78447865



