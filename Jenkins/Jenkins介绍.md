## Jenkins介绍

### 根目录

Jenkins服务是安装部署在Tomcat容器(或其他服务器)上的，先用ps命令查询运行Jenkins服务的Tomcat是由哪个用户启动的，Jenkins的根目录就在该用户家目录的.jenkins目录下，如：


	/root/.jenkins
	root@glexercloud-VirtualBox:~/.jenkins# ll
	总用量 156
	drwxr-xr-x  14 root root  4096 12月 13 14:10 ./
	drwx------  29 root root  4096 12月 15 10:15 ../
	drwxr-xr-x   3 root root  4096 10月 10 15:16 cache/
	-rw-r--r--   1 root root  5869 12月 13 14:10 config.xml
	-rw-r--r--   1 root root   957 10月 10 15:15 credentials.xml
	drwxr-xr-x 128 root root  4096 12月  9 04:52 fingerprints/
	-rw-r--r--   1 root root   159 11月 14 10:24 hudson.model.UpdateCenter.xml
	-rw-r--r--   1 root root  1119 10月 10 15:12 hudson.plugins.emailext.ExtendedEmailPublisher.xml
	-rw-r--r--   1 root root   391 10月 18 18:02 hudson.plugins.git.GitTool.xml
	-rw-r--r--   1 root root   173 10月 18 18:02 hudson.plugins.gradle.Gradle.xml
	-rw-r--r--   1 root root   321 10月 18 18:02 hudson.tasks.Ant.xml
	-rw-r--r--   1 root root   327 10月 18 18:02 hudson.tasks.Maven.xml
	-rw-------   1 root root  1712 10月 10 14:57 identity.key.enc
	-rw-r--r--   1 root root     6 11月 14 10:24 jenkins.install.InstallUtil.lastExecVersion
	-rw-r--r--   1 root root     6 10月 10 15:10 jenkins.install.UpgradeWizard.state
	-rw-r--r--   1 root root   138 12月 13 14:04 jenkins.model.DownloadSettings.xml
	-rw-r--r--   1 root root   247 10月 18 18:02 jenkins.mvn.GlobalMavenConfig.xml
	-rw-r--r--   1 root root   169 12月 13 14:04 jenkins.security.QueueItemAuthenticatorConfiguration.xml
	-rw-r--r--   1 root root   288 12月 13 14:04 jenkins.security.UpdateSiteWarningsConfiguration.xml
	drwxr-xr-x  11 root root  4096 11月  6 13:55 jobs/
	drwxr-xr-x   3 root root  4096 10月 10 14:57 logs/
	-rw-r--r--   1 root root   907 11月 14 10:24 nodeMonitors.xml
	drwxr-xr-x   2 root root  4096 10月 10 14:57 nodes/
	-rw-r--r--   1 root root   298 10月 18 18:02 org.jenkinsci.plugins.docker.commons.tools.DockerTool.xml
	-rw-r--r--   1 root root   255 10月 18 18:02 org.jenkinsci.plugins.gitclient.JGitApacheTool.xml
	-rw-r--r--   1 root root   243 10月 18 18:02 org.jenkinsci.plugins.gitclient.JGitTool.xml
	-rw-r--r--   1 root root    54 12月 15 13:30 .owner
	drwxr-xr-x  79 root root 12288 10月 27 11:46 plugins/
	-rw-r--r--   1 root root   130 10月 19 17:19 queue.xml.bak
	-rw-r--r--   1 root root    64 10月 10 14:57 secret.key
	-rw-r--r--   1 root root     0 10月 10 14:57 secret.key.not-so-secret
	drwx------   4 root root  4096 11月  2 15:51 secrets/
	drwxr-xr-x   2 root root  4096 12月 15 10:24 updates/
	drwxr-xr-x   2 root root  4096 10月 10 14:57 userContent/
	drwxr-xr-x   7 root root  4096 10月 19 15:12 users/
	drwxr-xr-x   2 root root  4096 10月 10 15:03 workflow-libs/
	drwxr-xr-x  16 root root  4096 11月  6 15:59 workspace/
