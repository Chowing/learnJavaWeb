```
yum groupinstall "X Window System"
yum groupinstall "GNOME Desktop"

```
root用户设置centos系统默认启动：
> systemctl set-default multi-user.target  //命令行
> systemctl set-default graphical.target  //图形
