+ feign配置，目的是远程调用失败后重试；
```java
@Configuration
public class FeignConfig {
    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(100, SECONDS.toMillis(1), 5);
    }
}
```  
+ 根据feign的规则实现接口，并在接口上面加上＠FeignClient注解，value 为其他服务的名字；
+ 程序启动后，会进行包扫描，扫描所有的＠FeignClient的注解的类，并将这些信息注入IoC容器中。
```java
@FeignClient(value = "eureka-client", configuration = FeignConfig.class)
public interface EurekaClientFeign {

    @GetMapping(value = "/hi") //调用 eureka-client 服务的API
    String sayHiFromClientEureka(@RequestParam(value = "name") String name); 
}
```  
+ 在 Service 层的 HiService 类注入 EurekaClientFeign 的 Bean，通过 EurekaClientFeign 去调用 sayHiFromClientEureka()方法
```java
@Service
public class HiService {

    @Autowired
    EurekaClientFeign eurekaClientFeign;

    public String sayHi(String name) {
        return eurekaClientFeign.sayHiFromClientEureka(name);
    }
}
```  
+ 最后
```java
@RestController
public class HiController {
    @Autowired
    HiService hiService;

    @GetMapping("/hi")
    public String sayHi(@RequestParam(defaultValue = "forezp", required = false) String name) {
        return hiService.sayHi(name);
    }
}
```  
+ 启动eureka-server工程，端口号为8761，启动两个eureka-client工程的实例，端口号分别为8762和8763；启动eureka-feign-client工程，端口号为8765，在浏览器上多次访 问 http://localhost:8765/hi， 浏览器会轮流显示以下内容：
> hi forezp,i am from port:8763  
> hi forezp,i am from port:8762
