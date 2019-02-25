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
#### 1. 通过ClassPathXmlApplicationContext 创建
ClassPathXmlApplicationContext会从类路径classPath中寻找指定的XML配置文件,找到并装载完成ApplicationContext 的实例化工作, 其使用语法如下。
```JAVA
ApplicationContext applicationContexES = new ClassPathXmlApplicationContext(String configLocation)
```
上述代码中, configLocation 参数用于指定 Spring 配置文件的名称和位置。 如果其值为"applicationContext.xml",则Spring会去类路径中查找名称为 applicationContext.xml的配置文件。
#### 2.通过 FileSystemXmlApplicationContext 创建
FileSystemXmlApplicationContext会从指定的文件系统路径(绝对路径)中寻找指定的XML配置文件, 找到并装载完成ApplicationContext的实例化工作,其使用语法如下。
```JAVA
ApplicationContext applicationContext = new FilesystemXmlApplicationContext(String configLocation);
```
与 ClassPathXmlApplicationContext有所不同的是, 在读取 Spring 的配置文件时,
FileSystemXmlApplicationContext不再从类路径中读取配置文件,而是通过参数指定配置文件的位置, 例如 “D:/workspaces/applicationContext.xml"。 如果在参数中写的不是绝对路径, 那么方法调用的时候,会默认用绝对路径来找。这种采用绝对路径的方式,会导致程序的灵活性变差,所以这个方法一般不推荐使用。
在使用 Spring 框架时, 可以通过实例化其中任何一个类来创建 ApplicationContext
容器。通常在 Java 项目中,会采用通过 ClassPathXmlApplicationContext 类来实例化ApplicationContext 容器的方式, 而在Web 项目中, ApplicationContext 容器的实例化工作会交由Web 服务器来完成。Web 服务器实例化 ApplicationContext 容器时, 通常会使用基于ContextLoaderListener实现的方式,此种方式只需要在web.xml中添加如下代码。
```xml
<!-指定Spring配置文件的位置,多个配置文件时, 以逗号分隔-->
<context-param>
<param-name>contextConfigLocation</param-name>
<1-- Spring将加载 spring目录下的 applicationContext.xml文件 -->
<param-value>
classpath:spring/applicationContext.xml
</param-value>
</context-param>
<!-- 指定以 ContextLoaderListener方式启动 Spring容器一
<listener>
<listener-class>
org.springframework.web.context.ContextLoaderListener
</listener-class>
</listener>
```
在本书后面章节讲解三大框架整合以及项目时,将采用基于 ContextLoaderListener的方式由Web服务器实例化ApplicationContext容器。
创建Spring 容器后,就可以获取 Spring 容器中的Bean.Spring获取Bean 的实例通常采用以下两种方法。
+ Object getBean(String name): 根据容器中Bean的id或name 来获取指定的Bean, 获取之后需要进行强制类型转换。

