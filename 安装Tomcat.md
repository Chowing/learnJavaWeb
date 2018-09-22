
解压Tomcat
找到安装包所在路径，解压在usr/java/下边
```
tar -xzvf apache-tomcat-8.5.11.tar.gz  -C /usr/java/
```

配置Tomcat
找到Tomcat安装目录下的bin目录，修改catalina.sh，添加两行
```
export JAVA_HOME=/usr/java/jdk1.8.0_121
export JRE_HOME=/usr/java/jdk1.8.0_121/jre
```
配置好保存后启动测试(注意在bin目录下边)
```
./startup.sh
```

##搞个脚本，用service管理
> #! /bin/bash
# chkconfig: 35 85 15
export JRE_HOME=/usr/local/jre
case "$1" in
  start)
     sudo –E -u nobody /usr/local/tomcat/bin/startup.sh
  ;;
  stop)
     /usr/local/tomcat/bin/shutdown.sh
  ;;
  restart)
     $0 stop
     $0 start
  ;;
esac

##为脚本设置执行权限，设置开机启动，然后启动
```
chmod +x /etc/init.d/tomcat
chkconfig --add tomcat
service tomcat start
```
开启8080端口监听
```
iptables -I INPUT -p tcp --dport 8080 -j ACCEPT
service iptables save
```

