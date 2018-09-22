下载NGINX，解压

安装依赖软件包
pcre-devel
openssl-devel
gcc
编译安装
./configure --prefix=/usr/local/nginx --with-http_ssl_module

make && make install
