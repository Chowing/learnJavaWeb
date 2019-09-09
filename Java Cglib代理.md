#### 需要被代理的对象
```java
public interface Person {
    void sing(String name);
    void dance(String name);
}
```
```java
public class XiaoMing implements Person {

    @Override
    public void sing(String name) {
        System.out.println("小明唱" + name);
    }

    @Override
    public void dance(String name) {
        System.out.println("小明跳" + name);
    }
}
```
#### 代理
```java
public class LearnCglibProxy implements MethodInterceptor {

    // 维护目标对象
    private Object target;
    public LearnCglibProxy(Object target){
        this.target = target;
    }

    // 给目标对象创建代理对象
    public Object getProxyInstance(){
        //1. 工具类
        Enhancer en = new Enhancer();
        //2. 设置父类
        en.setSuperclass(target.getClass());
        //3. 设置回调函数
        en.setCallback(this);
        //4. 创建子类(代理对象)
        return en.create();
    }


    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        System.out.println("开始事务.....");
        // 执行目标对象的方法
        Object returnValue = method.invoke(target, args);
        System.out.println("提交事务.....");

        return returnValue;
    }
}
```
#### 调试
```java
    public static void main(String[] args) {
        XiaoMing bean = new XiaoMing();
        XiaoMing cglibBean = (XiaoMing) new LearnCglibProxy(bean).getProxyInstance();
        cglibBean.sing("1234567890");
    }
```
