public class RegisterServlet extends Servlet {

    @Override
    void get(Request request, Response response) {

    }

    @Override
    void post(Request request, Response response) {
        response.println("<html><head><title>返回注册</title>");
        response.println("</head><body>");
        response.println("你的用户名为:" + request.getParameter("uname"));
        response.println("</body></html>");
    }
}
