### 1-2 Spring的核心容器
Spring 框架的主要功能是通过其核心容器来实现的。
Spring 框架提供了两种核心容器,分别为 BeanFactory 和 ApplicationContext。

### 1.2.1 BeanFactory
BeanFactory 由 org.springframework.beans.facytory.BeanFactory接口定义, 是基础类型的loC容器,它提供了完整的loC服务支持。 简单来说, BeanFactory 就是一个管理Bean的工厂, 它主要负责初始化各种Bean, 并调用它们的生命周期方法。BeanFactory 接口提供了几个实现类, 其中最常用的是 org.springframework.beans.factory.xml.XmlBeanFactory,该类会根据XML配置文件中的定义来装配Bean。
创建 BeanFactory实例时,需要提供Spring所管理容器的详细配置信息,这些信息通常采用XML文件形式来管理, 其加载配置信息的语法如下。
```java
BeanFactory beanFactory = new XmlBeanFactory(new FileSystemResource("F:/applicationContext.xml"));
```
这种加载方式在实际开发中并不多用。

### 1.2.2 ApplicationContext
ApplicationContext是BeanFactory 的子接口,也被称为应用上下文, 是另一种常用的Spring核心容器。 它由 org.springframework.context.ApplicationContext 接口定义, 不仅包含了BeanFactory的所有功能,还添加了对国际化、资源访问、事件传播等方面的支持。
创建 ApplicationContext接口实例,通常采用两种方法,具体如下。
1. 通过ClassPathXmlApplicationContext 创建
ClassPathXmlApplicationContext会从类路径classPath中寻找指定的XML配置文件,找
