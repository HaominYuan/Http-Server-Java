import java.util.HashMap;
import java.util.Map;

public class ServletContext {
    private Map<String, Servlet> servlet;
    private Map<String, String> mapping;

    ServletContext() {
        servlet = new HashMap<>();
        mapping = new HashMap<>();
    }

    Map<String, Servlet> getServlet() {
        return servlet;
    }

    Map<String, String> getMapping() {
        return mapping;
    }


    public void setServlet(Map<String, Servlet> servlet) {
        this.servlet = servlet;
    }

    public void setMapping(Map<String, String> mapping) {
        this.mapping = mapping;
    }
}
