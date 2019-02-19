```js
var http        = require("http");        // 引入 http 模块
var url         = require("url");         // 引入 url  模块，帮助解析
var querystring = require("querystring"); // 引入 querystring 库，也是帮助解析用的

/**
 * 准备处理请求和响应的service函数，就像是servlet的doGet，doPost方法
 * 这个函数做了两件事：
 * a.设置返回代码200，以及返回格式为text/plain
 * b.返回内容是：hello node.js
 */
function service(req, response) {
  var arg    = url.parse(req.url).query;  //获取返回的url对象的query属性值
  var params = querystring.parse(arg);    //将arg参数字符串反序列化为一个对象

  console.log("method = " + req.method);  //请求的方式
  console.log("url    = " + req.url);     //请求的url
  console.log("id     = " + params.id);   //获取参数id

  response.writeHead(200, { "Content-Type": "text/plain" });
  response.end("Hello Node.js");
}

http.createServer(service).listen(8088);  //基于service函数来创建服务器，服务器监听于8088端口
```
访问 http://localhost:8088/?time=23:01 ，结果
```
method = GET
url    = /?time=23:01
id     = undefined
method = GET
url    = /favicon.ico
id     = undefined
```
