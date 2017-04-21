## Maven配置文件

maven配置文件可位于两个地方：

	Maven安装目录: $M2_HOME/conf/settings.xml
	用户特定的Settings文件: ~/.m2/settings.xml

配置文件的结构如下：

	<settings xmlns="http://maven.apache.org/POM/4.0.0"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
			http://maven.apache.org/xsd/settings-1.0.0.xsd">
	<localRepository/>
	<interactiveMode/>
	<usePluginRegistry/>
	<offline/>
	<pluginGroups/>
	<servers/>
	<mirrors/>
	<proxies/>
	<profiles/>
	<activeProfiles/>
	</settings