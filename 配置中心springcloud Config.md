#### 一个微服务config-client使用config-server的配置yml
+ 配置config-server 
```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-config-server</artifactId>
</dependency>
```
```java
@SpringBootApplication
@EnableConfigServer 
public class ConfigServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}
}
```
```yml
server:
  port: 8769
spring:
  cloud:
    config:
      server:
        native:
          search-locations: classpath:/shared #读取配置的路径为classpath下的shared目录
  profiles: 
     active: native #通过spring.profiles.active=native来配置Config Server从本地读取配置
  application:
    name: config-server
```
在工程的Resources目录下建一个shared文件夹，用于存放本地配置文件。在shared目录下，新建一个config-client-dev.yml文件，用作eureka-client工程的dev （开发环境〉的配置文件。在 config-client-dev.yml配置文件中，指定程序的端口号为8762，并定义一个变量foo，该变量的值为foo version 1 
```yml
server:
  port: 8762
  
foo: foo version 1
```
+ 新建一个工程，取名为config-client，该工程作为Config Client从Config Server读取配置文件
```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```
```java
@SpringBootApplication
@RestController
public class ConfigClientApplication {

	public static void main(String[] args) { SpringApplication.run(ConfigClientApplication.class, args); }

	@Value("${foo}")
	String foo;
	@RequestMapping(value = "/foo")
	public String hi(){ return foo; }
}
```
```yml
spring:
  application:
    name: config-client
  cloud:
    config:
      uri: http://localhost:8769 # 向Uri地址为http://localhost:8769 的Config Server读取配置文件
      fail-fast: true #如果没有读取成功，则执行快速失败（ fail-fast ），读取的是dev文件
  profiles:
    active: dev # 变量｛spring.application.name｝和变量｛spring.profiles.active｝，两者以“·”相连，构成了向Config Server读取的配置文件名，所以本案例在配置中心读取的配置文件名为config-client-dev.yml文件
```
eureka-server工程启动后，启动eureka-client工程，你会在控制台的日志中发现eureka-client向Uri地址为http://localhost:8769 的Config Server读取了配置文件。最终程序启动的端口为 8762，这个端口是在eureka-server的Resouces/shared目录中的eureka-client-dev.yml的配置文件中配置的，可见eureka-client成功地向eureka-server读取了配置文件。打开浏览器，访问 http://localhost:8762/foo ，浏览器显示：
```html
foo version 1
```
可见eureka-client工程成功地向eureka-server工程读取了配置文件中foo变量的值。