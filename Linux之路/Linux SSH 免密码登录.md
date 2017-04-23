## Linux SSH 免密码登录


- 生成密码

		ssh-keygen -t rsa
		
>  默认会在当前用户的home目录下生成.ssh文件夹并存放公钥和私钥

-  复制公钥

		将id_rsa.pub复制到远程机子的.ssh目录并命名为authorized_keys	


			

