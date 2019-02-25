在面向对象的程序中,想要使用某个对象,就需要先实例化这个对象。同样,在Spring中,要想使用容器中的Bean, 也需要实例化Bean。  
实例化Bean 有三种方式,分别为构造器实例化、静态工厂方式实例化、实例工厂方式实例化（其中最常用的是构造器实例化）。  
#### 2.2.1 构造器实例化
构造器实例化是指Spring 容器通过 Bean 对应类中默认的无参构造方法来实例化Bean。下面通过一个案例来演示 Spring 容器是如何通过构造器来实例化Bean的。
1. 在Eclipse中,创建一个名为chapter02的Web项目, 在该项目的lib 目录中加入Spring支持和依赖的JAR包。
2. 在chapter02项目的src目录下,创建一个 com.itheima.instance.constructor包,在该包中创建Bean1类。
```java
package com.itheima.instance.constructor;
public class Bean1 {
}
```
3. 在 com.itheima.instance.constructor包中，创建Spring 的配置文件beans1.xml，在配置文件中定义一个id 为bean1的Bean, 并通过 class 属性指定其对应的实现类为Bean1
```xml
<?xml version="1.0" encoding-"UTE-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://www.springframework.org/schema/beans
http://www.springframework.org/schema/beans/spring-beans-4.3.xsd">
    
    <bean id="beanl" class="com.itheima.instance.constructor.Beanl" />
    
</beans>
```
4. 在com.itheima.instance.constructor包中,创建测试类 InstanceTest1,来测试构造器是否能实例化Bean。
```java
package com.itheima.instance.constructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class InstanceTest1 {
    public static void main(String(] args){
        //定义配置文件路径
        String xmlPath = "com/itheima/instance/constructor/beans1.xml";
        
        //ApplicationContext在加载配置文件时,对Bean进行实例化
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
        
        Beanl bean = (Beanl) applicationContext.getBean("beanl");
        System.out.println(bean);
    }
}

```
