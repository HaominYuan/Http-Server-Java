abstract class Servlet {
    void service(Request request, Response response) {
        get(request, response);
        post(request, response);
    }

    abstract void get(Request request, Response response);

    abstract void post(Request request, Response response);

}
