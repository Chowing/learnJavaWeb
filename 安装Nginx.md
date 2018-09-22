下载NGINX，解压

安装依赖软件包
pcre-devel
openssl-devel
gcc
编译安装
./configure --prefix=/usr/local/nginx --with-http_ssl_module

make && make install

开发80端口
iptables -I INPUT -p tcp --dport 80 -j ACCEPT
保存
service iptables save
重启
service iptables restart

添加到环境变量
ln -s /usr/local/nginx/sbin/nginx /usr/local/sbin/nginx
