+ Feign 的起步依赖中已经引入了Hystrix的依赖，只需要在 eureka-feign-client 工程的配置文件 application.yml 中配置开启 Hystrix 的功能
```yml
server:
  port: 8765
spring:
  application:
    name: eureka-feign-client
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
feign:
  hystrix:
    enabled: true
```

+ 新增：在＠FeignClient注解的fallback配置加上快速失败的处理类。该处理类是作为Feign熔断器的逻辑处理类，必须实现被＠FeignClient修饰的接口。
```java
@FeignClient(value = "eureka-client", configuration = FeignConfig.class, fallback = HiHystrix.class)
public interface EurekaClientFeign {
    @GetMapping(value = "/hi")
    String sayHiFromClientEureka(@RequestParam(value = "name") String name);
}
```

+ HiHystrix 作为熔断器的逻辑处理类，需要实现EurekaClientFeign接口，需要在接口方法 sayHiFromC\ientEureka()里写处理熔断的具体逻辑，同时还要在 HiHystrix 类上加
@Component 注解，注入 IoC 容器中。
```java
@Component
public class HiHystrix implements EurekaClientFeign {
    @Override
    public String sayHiFromClientEureka(String name) {
        return "hi，" + name + ",sorry,error!";
    }
}
```



