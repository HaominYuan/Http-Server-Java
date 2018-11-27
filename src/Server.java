import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private ServerSocket serverSocket;
    private boolean isShutDown = false;

    public static void main(String[] args) {
        Server server = new Server();
        int port = 8886;
        server.start(port);
    }

    private void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            receive();
        } catch (IOException e) {
            stop();
        }
    }

    private void receive() {
        try {
            while (!isShutDown) {
                new Thread(new Dispatcher(serverSocket.accept())).start();
            }
        } catch (IOException e) {
            stop();
        }
    }


    private void stop() {
        isShutDown = true;
        CloseUtil.close(serverSocket);
    }
}
