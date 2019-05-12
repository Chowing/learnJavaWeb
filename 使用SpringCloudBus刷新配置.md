SpringCloud Bus是用轻量的消息代理将分布式的节点连接起来，可以用于广播配置文件的更改或者服务的监控管理。关键的思想就是，消息总线可以为微服务做监控，也可以实现应用程序之间相通信。 SpringCloud Bus可选的消息代理组建包括RabbitMQ、AMQP和Kafka等。  
为什么需要用 Spring Cloud Bus 去刷新配置呢？  
如果有几十个微服务，而每一个服务又是多实例，当更改配置时，需要重新启动多个微服务实例，会非常麻烦。 SpringCloud Bus的一个功能就是让这个过程变得简单，当远程Git仓库的配置更改后，只需要向某一个微服务实例发送一个Post请求，通过消息组件通知其他微服务实例重新拉取配置文件。  

只需要改造 configClient 工程。首先，需要在porn文件中引入用 RabbitMQ 实现的SpringCloud Bus的起步依赖spring-cloud-starter-bus-amqp 。 如果读者需要自己实践，则需要安装 RabbitMQ 服务器。 pom文件添加的依赖如下：  
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
```
在工程的配置文件application.yml添加RabbitMQ的相关配置，host为RabbitMQ服务器的IP地址，port为RabbitMQ服务器的端口，usemame和password为RabbitMQ服务器的用户名和密码。通过消息总线更改配置，需要经过安全验证，为了方便讲述，先把安全验证屏蔽掉，也就是将management.security.enabled改为false。
```yml
spring:
  application:
    name: config-client
  cloud:
    config:
#      uri: http://localhost:8769
      fail-fast: true
      discovery:
        enabled: true
        serviceId: config-server
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
    publisher-confirms: true
    virtual-host: /
  profiles:
    active: dev
server:
  port: 8765
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
management:
  security:
    enabled: false
```
最后，需要在更新的配置类上加＠RefreshScope注解，只有加上了该注解，才会在不重启服务的情况下更新配置，如本例中更新配置文件foo变量的值。
```java
@SpringBootApplication
@RestController
@EnableEurekaClient
@RefreshScope
public class ConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
    }

    @Value("${foo}")
    String foo;

    @GetMapping(value = "/foo")
    public String hi() {
        return foo;
    }
}
```
依次启动工程，其中config-client开启两个实例，端口分别为8762和8763。启动完成后，在浏览器上访问即localhost:8762/foo 或者 localhost:8763/foo，浏览器显示：  
```html
foo version 1
```
更改远程 Git仓库，将foo的值改为“foo version 2”。通过Postman或者其他工具发送一个Post请求localhost:8762/bus/refresh ，请求发送成功，再访问 http://localhost:8762/foo 或者localhost:8763/foo，浏览器都会显示：
```html
foo version 2
```
可见，通过向8762端口的微服务实例发送Post请求http://localhost:8762/bus/refresh ，请求刷新配置，由于使用了SpringCloud Bus，其他服务实例（如案例中的 8763 端口的服务实例）会接收到刷新配置的消息，也会刷新配置。另外，“a/bus/refresh” API接口可以指定服务，即使用“destination”参数 ，例如“/bus/refresh?destination=eureka-client:**＂，即刷新服务名为
eureka-client的所有服务实例。
