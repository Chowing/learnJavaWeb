### 在SpringBootAdmin中，可以很方便地集成Turbine组件  
1. 首先需要两个Eureka Client 工程，在这两个EurekaClient的工程中实现hystrix熔断器和hystrix Dashboard组件；
2. 然后需要一个Turbine工程，在这个工程中聚合两个Eureka Client的Hystrix DashBoard；
3. 最后，在SpringBootAdminServer中集成Turbine组件，这样就可以将Turbine界面显示；  
特别地，为了测试写一个API“/hi”在方法上加上＠HystrixCommand注解，该注解用于创建一个熔断器，井指明fallbackMethod（回退方法）为“hiError”方法。在hiError()方法中，直接返回一个字符串
```JAVA
@SpringBootApplication
@EnableEurekaClient
@RestController
@EnableHystrix
@EnableHystrixDashboard
public class EurekaClientOneApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaClientOneApplication.class, args);
    }
    @Value("${server.port}")
    String port;
    @GetMapping("/hi")
    @HystrixCommand(fallbackMethod = "hiError")
    public String home(@RequestParam String name) {
        return "hi " + name + ",i  am lucy and from port:" + port;
    }
    public String hiError(String name) {
        return "hi," + name + ",sorry,error!";
    }
}
```
创建另外一个EurekaClient的Module工程，该工程取名为eurekaclient-two。它的依赖同eureka-client-one，并且同样也对外也暴露一个API，在这个API接口中 也使用到了熔断器。 eureka-client-two工程与eureka-client-one工程的代码类似，不同的是，需要在eureka-client-two工程的配置文件application.yml中指明程序的名称为eureka-client-two，端口号为8763，日志输出路径为“logs/eureka-clienttwo.log”。
#### 构建Turbine工程
创建工程取名turbine-service。提供Turbine组件的功能，聚合了eureka-client-one和eureka-client-two工程的Hystrix Dashboard组件；
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-turbine</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-netflix-turbine</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
```yml
spring:
  application.name: service-turbine
server:
  port: 8769
security.basic.enabled: false
turbine:
  aggregator:
    clusterConfig: default #集群配置采用默认
  appConfig: eureka-client-one,eureka-client-two #聚合监控eureka-client-one和two工程的HystrixDashboard
  clusterNameExpression: new String("default")
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
management:
  security:
    enabled: false
```
```java
@SpringBootApplication
@EnableTurbine
public class TurbineServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(TurbineServiceApplication.class, args);
	}
}
```
### 在AdminServer中集成Turbine
最后需要在SpringBootAdmin Server中集成Turbine。在admmin-server工程的pom文件引入相关依赖:  
```xml
<dependency>
	<groupId>de.codecentric</groupId>
	<artifactId>spring-boot-admin-server-ui-turbine</artifactId>
	<version>1.5.1</version>
</dependency>
<dependency>
	<groupId>de.codecentric</groupId>
	<artifactId>spring-boot-admin-server-ui-hystrix</artifactId>
	<version>1.5.1</version>
</dependency>
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-turbine</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-hystrix-dashboard</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-hystrix</artifactId>
</dependency>
```
```yml
spring:
  application:
    name: service-admin
  boot:
    admin:
      routes:
        endpoints: env,metrics,dump,jolokia,info,configprops,trace,logfile,refresh,flyway,liquibase,heapdump,loggers,auditevents,hystrix.stream,activiti
      turbine:
        clusters: default #集群情况使用默认的即可
        location: service-turbine #通过spring.boot.admin.turbine.location来配置Turbine的服务名，本案例Turbine的服务名为service-turbine
```
```java
@SpringBootApplication
@EnableTurbine
@EnableHystrixDashboard
@EnableHystrix
@EnableAdminServer
public class AdminServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(AdminServerApplication.class, args);
	}
}
```
依次启动工程eureka-server、eureka-client-one、eureka-client-two、turbine-service和admin-server这5个工程，在浏览器上访问admin-server的主页http://localhost:5OOO/，浏览器界面相比多了一个“TURBINE”的选项按钮；