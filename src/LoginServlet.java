public class LoginServlet extends Servlet {


    @Override
    void get(Request request, Response response) {
        String name = request.getParameter("uname");
        String pwd = request.getParameter("pwd");
        if (login(name, pwd)) {
            response.println("<h1>we我是登陆成功f1</h1>");
        } else {
            response.println("<h1>密码或用户名错误</h1>");
        }
    }

    private boolean login(String name, String pwd) {
        return name.equals("yhm") && pwd.equals("zzm");
    }

    @Override
    void post(Request request, Response response) {
    }
}
