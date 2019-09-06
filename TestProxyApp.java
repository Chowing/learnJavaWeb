public class TestProxyApp {

    public interface Person {
        void sing(String name);
        void dance(String name);
    }

    public static class XiaoMing implements Person {

        @Override
        public void sing(String name) {
            System.out.println("小明唱" + name);
        }

        @Override
        public void dance(String name) {
            System.out.println("小明跳" + name);
        }
    }

    public static class XiaoMingProxy {
        //代理只是一个中介，实际干活的还是小明，于是需要在代理类上维护小明这个变量
        XiaoMing xiaoMing = new XiaoMing();


        //返回代理对象
        public Person getProxy() {


            InvocationHandler handler = new InvocationHandler() {

                /**
                 * proxy : 把代理对象自己传递进来
                 * method：把代理对象当前调用的方法传递进来
                 * args:把方法参数传递进来
                 */
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                    //如果别人想要让小明唱歌
                    if (method.getName().equals("sing")) {

                        System.out.println("给1000万来再唱");

                        //实际上唱歌的还是小明
                        method.invoke(xiaoMing, args);
                    }
                    return null;
                }
            };

            /**
             * 参数一：代理类的类加载器
             * 参数二：被代理对象的接口
             * 参数三：InvocationHandler实现类
             */
            return (Person) Proxy.newProxyInstance(XiaoMingProxy.class.getClassLoader(), xiaoMing.getClass().getInterfaces(), handler);

        }
    }


    public static void main(String[] args) {
        //外界通过代理才能让小明唱歌
        XiaoMingProxy xiaoMingProxy = new XiaoMingProxy();
        Person proxy = xiaoMingProxy.getProxy();
        proxy.sing("我爱你");
       
    }
}
