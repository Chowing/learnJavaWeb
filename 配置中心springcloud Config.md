#### 一个微服务 config-client 使用 config-server 的配置yml
+ 配置 config-server 
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
          search-locations: classpath:/shared # 读取配置的路径为 classpath 下的 shared 目录
  profiles: 
     active: native # 通过 spring. profiles.active=native 来配置 Config Server 从本地读取配置
  application:
    name: config-server
```
在工程的 Resources 目录下建一个 shared 文件夹，用于存放本地配置文件。在 shared 目录下，新建一个 config-client-dev.yml 文件，用作 eureka-client 工程的 dev （开发环境〉的配置文件。在 config-client-dev.yml 配置文件中，指定程序的端口号为 8762，并定义一个变量foo，该变量的值为 foo version 1 
```yml
server:
  port: 8762
  
foo: foo version 1
```
+ 新建一个工程，取名为 config-client ， 该工程作为 Config Client 从 Config Server 读取配置文件
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
      uri: http://localhost:8769 # 向 Uri 地址为 http://localhost:8769 的 Config Server 读取配置文件
      fail-fast: true # 如果没有读取成功，则执行快速失败（ fail-fast ），读取的是 dev 文件
  profiles:
    active: dev # 变量 ｛ spring. application.name｝和变量 ｛ spri吨.profiles.active｝，两者以“·”相连，构成 了 向 Config Server 读取的配 置文件名，所以本案例在配置 中心读取的配 置文件名为config-client-dev.yml 文件
```
eureka-server 工程启动后，启动 eureka-client 工程，你会在控制台的日志中发现 eureka-client 向 Uri 地址为 http://localhost:8769 的 Config Server 读取了配置文件。最终程序启
动的端口为 8762，这个端口 是在 eureka-server 的Resouces/shared目录中的 eureka-client-dev.yml的配置文件中配置的，可见 eureka-client 成功地向 eureka-server 读取了配置文件 。打开浏览器，访问 http://localhost:8762/foo ，浏览器显示：
```html
foo version 1
```
可见 eureka-client 工程成功地向 eureka-server 工程读取了配置文件中 foo 变量的值。