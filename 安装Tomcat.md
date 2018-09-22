
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

