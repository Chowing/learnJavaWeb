编辑/etc/my.cnf
在[mysqld]下面添加
```
skip-grant-tables
```
重启Mysql
```
service mysqld restart
```
登录,直接回车
```
mysql -u root -p
```
然后

```
flush privileges;
```
修改密码
```
ALTER USER 'root'@'localhost' IDENTIFIED BY 'NewPassword';
```
回到/etc/my.cnf，去掉skip-grant-tables
重启Mysql
