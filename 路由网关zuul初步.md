+ 在 pom 文件中引入相关依赖
```xml
<dependency>
<groupId>org.springframework.cloud</groupId>
<artifactId>spring-cloud-starter-eureka</artifactId>
</dependency>
<dependency>
<groupId>org.springframework.cloud</groupId>
<artifactId>spring-cloud-starter-zuul</artifactId>
</dependency>
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
+ 启动类加上＠EnableZuulProxy 注解，开启 Zuul 的功能
+ 配置文件
```yml
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
server:
  port: 5000
spring:
  application:
    name: service-zuul
zuul:
  routes:
    hiapi:
      path: /hiapi/**
      serviceId: eureka-client
#      url: http://localhost:8762  #这样写不会做负载均衡
#      serviceId: hiapi-v1
    ribbonapi:
      path: /ribbonapi/**
      serviceId: eureka-ribbon-client
    feignapi:
      path: /feignapi/**
      serviceId: eureka-feign-client
```
+ 如果想给每一个服务的API加前缀，例如 http://localhost:5000/v1/hiapi/hi?name=forezp/，即在所有的API上加一个v1作为版本号。这时需要用到zuul.prefix的配置
```yml
zuul.prefix: /v1  #加个前缀
```