#### 1、下载安装nodejs
检验运行成功
```
node --version
```
#### 2、首先创建个项目以及源文件目录
```
~/nodejsProjects/firstTry/src
```
#### 3、服务器代码 server.js
```js
//引入http模块
var http = require('http');
/**
 * 准备处理请求和响应的service函数，就像是servlet的doGet，doPost方法
 * 这个函数做了两件事：
 * a.设置返回代码200，以及返回格式为text/plain
 * b.返回内容是：hello node.js
 */
function service(req,resp){
    resp.writeHead(200,{
        'Content-Type':'text/plain'
    });
    resp.end('Hello Node.js');
}
//基于service函数来创建服务器
var server = http.createServer(service);
//服务器监听于8088端口
server.listen(8088);
```
#### 4、运行和访问
```
node server.js
```
http://localhost:8088/

