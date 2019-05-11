需要继承 ZuulFilter，实现 ZuulFilter 中的抽象方法
```java
@Component
public class MyFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(MyFilter.class);
    @Override
    public String filterType() {
        return PRE_TYPE; //过滤器的类型，在前文已经讲解过了，它有 4 种类型，分别是pre，post，routing，error； 
    }

    @Override
    public int filterOrder() {
        return 0;// 过滤顺序，它为一个 Int 类型的值，值越小，越早执行该过滤器
    }

    @Override
    public boolean shouldFilter() {
        return true; //该过滤器是否过滤逻辑，如果为 true ，则执行 run()
    }

    /**
     * 在本例中，检查请求的参数中是否传了token 这个参数 ，如果没有传，则请求不被路由到具体的服务实例，直接返回响应 ，状态码为401
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        Object accessToken = request.getParameter("token");
        if(accessToken == null) {
            log.warn("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty");
            } catch (Exception e) {
            }
            return null;
        }
        log.info("ok");
        return null;
    }
}
```
访问 http: //localhost:5000/hiapi/hi?name=forezp ，浏览器显示  
```html
token is empty
```
可见， MyFilter 这个 Bean 注入 IoC 容器之后，对请求进行了过滤，并在请求路由转发之前进行了逻辑判断。在实际开发中，可以用此过滤器进行安全验证。