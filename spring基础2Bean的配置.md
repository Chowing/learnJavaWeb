Spring 可以被看作是一个大型工厂, 这个工厂的作用就是生产和管理 Spring 容器中的Bean，使用这个工厂, 需要对Spring的配置文件进行配置。  
Spring容器支持XML和Properties两种格株式的配置文件,在实际开发中,最常使用的就是XML格式的配置方式。 这种配置方式通过XML文件来注册并管理Bean 之间的依赖关系。  
在Spring中,XML配置文件的根元素是<beans>,<beans>中包含了多个<bean>子元素,每一个<bean>子元素定义了一个Bean,并描述了该Bean 如何被装配到 Spring 容器中。  
<bean>元素中同样包含了多个属性以及子元素, 其常用属性及子元素如下所示。
  
属性或子元素名称 | 描述
---|---
id | 是一Bean的唯一标识行,Spring 容器对Bean 的配置,管理都通过该属性来完成
name | Spring 容器同样可以通过此属性对容器中的 Bean 进行配置和管理,name 属性中可以为Bean 指定多个名称, 每个名称之间用逗号或分号隔开
class | 该属性指定了 Bean 的具体实现类,它必须是一个完整的类名,使用类的全限定名
scope | 用来设定 Bean 实例的作用域,其属性值 :singleton(单例),prototype(原型),request,session,global Session,application和websocket。其默认值为 singleton
constructor-arg | <bean>元素的子元素,可以使用此元素传入构造参数进行实例化.该元素的index属性指定构造参数的序号(从0开始),type 属性指定构造参数的类型,参数值可以通过 ref 属性或value 属性直接指定, 也可以通过ref或value子元素指定
property | <bean>元素的子元素,用于调用Bean 实例中的 setter 方法完成属性赋值,从而完成依赖注入。该元素的name 属性指定 Bean 实例中的相应属性名,ref 属性或value 属性用于指定参数值
ref | <property>,<constructor-arg>等元素的属性或子元素,可以用于指定对 Bean工厂中某个Bean 实例的引用
value | <property>,<constructor-arg>等元素的属性或子元素,可以用于直接指定一个常量值
list | 用于封装 List 或数组类型的依赖注入
set | 用于封装 Set 类型属性的依赖注入
map | 用于封装 Map 类型属性的依赖注入
entry | <map>元素的子元素, 用于设置一个键值对. 其 key 属性指定字符串类型的键值, ref 或value子元素指定其值, 也可以通过value-ref或value 属性指定其值


在配置文件中, 通常一个普通的Bean 只需要定义id (或name) 和class 两个属性即可,定义 Bean的方式如下所示。
```xml
<?xml version="I.0" encoding-"UTF-8"?>
<beans xmlns-"http://www.springframework.org/schema/beans"
xmlns:xsi="http://www.w3.org/2001/XMILSchema-instance"xsi:schemaLocation-"http://www.springframework.org/schema/beans
http://www.springframework.org/schema/beans/spring-beans.xsd">
<!--使用id属性定义bean1,其对应的实现类为 com.itheima.Bean1-->
<bean id="bean1" class="com.itheima.Bean1" />
<!--使用name属性定义bean2,其对应的实现类为 com.itheima.Bean2-->
<bean name="bean2" class="com.itheima.Bean2" />
</beans>
```
在上述代码中, 分别使用id 属性和 name 属性定义了两个Bean, 并使用class元素指定其对应的实现类。

如果在Bean 中未指定id 和name, 则Spring会将dlass值当作id使用。

