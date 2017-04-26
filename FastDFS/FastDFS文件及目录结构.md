## FastDFS文件及目录结构

### 结构约定
	  ${base_path}
	     |__data：存放数据文件
	     |__logs：存放日志文件

 其中，${base_path}由配置文件中的参数“base_path”设定。

### tracker server目录及文件结构

	  ${base_path}
	     |__data
	     |     |__storage_groups.dat：存储分组信息
	   	 |     |__storage_servers.dat：存储服务器列表
	   	 |__logs
	           |__trackerd.log：tracker server日志文件

 数据文件storage_groups.dat和storage_servers.dat中的记录之间以换行符（\n）分隔，字段之间以西文逗号（,）分隔。

storage_groups.dat中的字段依次为：

	  1. group_name：组名
	  2. storage_port：storage server端口号

storage_servers.dat中记录storage server相关信息，字段依次为：

	  1. group_name：所属组名
	  2. ip_addr：ip地址
	  3. status：状态
	  4. sync_src_ip_addr：向该storage server同步已有数据文件的源服务器
	  5. sync_until_timestamp：同步已有数据文件的截至时间（UNIX时间戳）
	  6. stat.total_upload_count：上传文件次数
	  7. stat.success_upload_count：成功上传文件次数
	  8. stat.total_set_meta_count：更改meta data次数
	  9. stat.success_set_meta_count：成功更改meta data次数
	  10. stat.total_delete_count：删除文件次数
	  11. stat.success_delete_count：成功删除文件次数
	  12. stat.total_download_count：下载文件次数
	  13. stat.success_download_count：成功下载文件次数
	  14. stat.total_get_meta_count：获取meta data次数
	  15. stat.success_get_meta_count：成功获取meta data次数
	  16. stat.last_source_update：最近一次源头更新时间（更新操作来自客户端）
	  17. stat.last_sync_update：最近一次同步更新时间（更新操作来自其他storage server的同步）

### storage server目录及文件结构

	  ${base_path}
	     |__data
	     |     |__.data_init_flag：当前storage server初始化信息
	     |     |__storage_stat.dat：当前storage server统计信息
	     |     |__sync：存放数据同步相关文件
	     |     |     |__binlog.index：当前的binlog（更新操作日志）文件索引号
	     |     |     |__binlog.###：存放更新操作记录（日志）
	     |     |     |__${ip_addr}_${port}.mark：存放向目标服务器同步的完成情况
	     |     |
	     |     |__一级目录：256个存放数据文件的目录，目录名为十六进制字符，如：00, 1F
	     |           |__二级目录：256个存放数据文件的目录，目录名为十六进制字符，如：0A, CF
	     |__logs
	           |__storaged.log：storage server日志文件

.data_init_flag文件格式为ini配置文件方式，各个参数如下：

	  # storage_join_time：本storage server创建时间
	  # sync_old_done：本storage server是否已完成同步的标志（源服务器向本服务器同步已有数据）
	  # sync_src_server：向本服务器同步已有数据的源服务器IP地址，没有则为空
	  # sync_until_timestamp：同步已有数据文件截至时间（UNIX时间戳）

storage_stat.dat文件格式为ini配置文件方式，各个参数如下：

	  # total_upload_count：上传文件次数
	  # success_upload_count：成功上传文件次数
	  # total_set_meta_count：更改meta data次数
	  # success_set_meta_count：成功更改meta data次数
	  # total_delete_count：删除文件次数
	  # success_delete_count：成功删除文件次数
	  # total_download_count：下载文件次数
	  # success_download_count：成功下载文件次数
	  # total_get_meta_count：获取meta data次数
	  # success_get_meta_count：成功获取meta data次数
	  # last_source_update：最近一次源头更新时间（更新操作来自客户端）
	  # last_sync_update：最近一次同步更新时间（更新操作来自其他storage server）

binlog.index中只有一个数据项：当前binlog的文件索引号

binlog.###，###为索引号对应的3位十进制字符，不足三位，前面补0。索引号基于0，最大为999。一个binlog文件最大为1GB。记录之间以换行符（\n）分隔，字段之间以西文空格分隔。字段依次为：

	  1. timestamp：更新发生时间（Unix时间戳）
	  2. op_type：操作类型，一个字符
	  3. filename：操作（更新）的文件名，包括相对路径，如：5A/3D/VKQ-CkpWmo0AAAAAAKqTJj0eiic6891.a

 ${ip_addr}_${port}.mark：ip_addr为同步的目标服务器IP地址，port为本组storage server端口。例如：10.0.0.1_23000.mark。文件格式为ini配置文件方式，各个参数如下：

	  # binlog_index：已处理（同步）到的binlog索引号
	  # binlog_offset：已处理（同步）到的binlog文件偏移量（字节数）
	  # need_sync_old：同步已有数据文件标记，0表示没有数据文件需要同步
	  # sync_old_done：同步已有数据文件是否完成标记，0表示未完成，1表示已完成
	  # until_timestamp：同步已有数据截至时间点（UNIX时间戳）
	  # scan_row_count：已扫描的binlog记录数
	  # sync_row_count：已同步的binlog记录数

 数据文件名由系统自动生成，包括5部分：存储服务器IP地址、当前时间（Unix时间戳）、文件大小（字节数）、随机数和文件后缀。文件名长度为33字 节。文件可以按目录顺序存放，也可以按照PJW Hash算法hash到65536（256*256）个目录中分散存储，通过配置文件控制。

















