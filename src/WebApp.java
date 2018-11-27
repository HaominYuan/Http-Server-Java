import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class WebApp {
    private static ServletContext context;
    static {
        context = new ServletContext();
        Map<String, String> mapping = context.getMapping();
        mapping.put("/login", "login");
        mapping.put("/log", "login");
        mapping.put("/reg", "register");

        Map<String, String> servlet = context.getServlet();
        servlet.put("login", "LoginServlet");
        servlet.put("register", "RegisterServlet");
    }

    public static Servlet getServlet(String url) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (url == null || url.trim().equals("")) {
            return null;
        }
        String servletName = context.getServlet().get(context.getMapping().get(url));
        return (Servlet) Class.forName(servletName).getDeclaredConstructor().newInstance();
    }
}
