Zuul 作为 Netflix 组件，可以与 Ribbon 、Eureka 和 Hystrix 等组件相结合，实现负载均衡、熔断器的功能。
在默认情况下，Zuul 和 Ribbon 相结合，实现了负载均衡的功能。

下面来讲解如何在 Zuul 上实现熔断功能。
在 Zuul 中实现熔断功能需要实现 ZuulFallbackProvider 的接口。实现该接口有两个方法， 
    a. getRoute()方法，用于指定熔断功能应用于哪些路由的服务 ； 
    b. fallbackResponse()为进入熔断功能时执行的逻辑 。 
```java
@Component
class MyFallbackProvider implements ZuulFallbackProvider {
    @Override
    public String getRoute() {
//        return "eureka-client";
        return "*"; //如果需要所有的路由服务都加熔断功能，只需要在 getRoute()方法上返回“*”的匹配符
    }

    @Override
    public ClientHttpResponse fallbackResponse() {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                return "OK";
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream("oooops!error, i'm the fallback.".getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}

```
重新启动 eureka-zuul-client 工程，并且关闭 eureka-client 的所有实例，在浏览器上访问 http://localhost:5000/hiapi/hi?name=forezp，浏览器显示
```html
oooops!error, i'm the fallback.
```
