import java.util.HashMap;
import java.util.Map;

public class ServletContext {
    private Map<String, String> servlet;
    private Map<String, String> mapping;

    ServletContext() {
        servlet = new HashMap<>();
        mapping = new HashMap<>();
    }

    Map<String, String> getServlet() {
        return servlet;
    }

    Map<String, String> getMapping() {
        return mapping;
    }


    public void setServlet(Map<String, String> servlet) {
        this.servlet = servlet;
    }

    public void setMapping(Map<String, String> mapping) {
        this.mapping = mapping;
    }
}
