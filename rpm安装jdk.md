rpm安装jdk
rpm -ivh jdk-8u181-linux-x64.rpm

通过文件/etc/profile修改环境变量
JAVA_HOME=/usr/java/jdk-8u181-linux-x64
PATH=$JAVA_HOME/bin:$PATH
CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export JAVA_HOME
export PATH
export CLASSPATH

配置好让修改生效
source /etc/profile

