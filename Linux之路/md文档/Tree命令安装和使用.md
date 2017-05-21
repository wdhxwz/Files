## Tree命令安装和使用

tree命令以树状图列出目录的内容，某些linux没有tree命令，所以先介绍安装

### Tree命令安装

1. 下载安装包：wget ftp://mama.indstate.edu/linux/tree/tree-1.7.0.tgz
2. 解压：tar -zxvf tree-1.7.0.tgz
3. make
4. cp -af tree /usr/bin

### Tree命令使用

1. tree -d 只显示目录。
2. tree -L n 只显示第n层目录。