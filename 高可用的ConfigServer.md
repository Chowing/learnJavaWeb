### 改造 Config Client
```yml
spring:
  application:
    name: config-client
  cloud:
    config:
      fail-fast: true
      discovery:
        enabled: true
        serviceId: config-server # 向ServiceId为config-server的配置服务读取配置文件
  profiles:
    active: dev

server:
  port: 8762

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
```