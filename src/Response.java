import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

class Response {
    private final static String CRLF = "\r\n";
    private final static String BLANK = " ";

    private StringBuilder headInfo;
    private StringBuilder content;
    private int length;
    private BufferedWriter bufferedWriter;

    private Response() {
        length = 0;
        headInfo = new StringBuilder();
        content = new StringBuilder();
    }

    Response(OutputStream outputStream) {
        this();
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    private void print(String info) {
        content.append(info);
        length = length + info.getBytes().length;
    }

    void println(String info) {
        print(info + CRLF);
    }

    void pushToClient(int code) throws IOException {
        if (headInfo == null) {
            code = 500;
        }
        createHeadInfo(code);
        bufferedWriter.append(headInfo);
        bufferedWriter.append(content);
        bufferedWriter.flush();
    }

    private void createHeadInfo(int code) {
        headInfo.append("HTTP/1.1").append(BLANK).append(code).append(BLANK);
        switch (code) {
            case 200:
                headInfo.append("OK");
                break;
            case 404:
                headInfo.append("NOT FOUND");
                break;
            case 505:
                headInfo.append("SERVER ERROR");
                length = 0;
                content = new StringBuilder();
                break;
        }
        headInfo.append(CRLF);

        // 响应头(Response Head)
        headInfo.append("Server:yhm Server/0.0.1").append(CRLF);
        headInfo.append("Data:").append(new Date(System.currentTimeMillis())).append(CRLF);
        headInfo.append("Content-type:text/html;charset=UTF-8").append(CRLF);
        headInfo.append("Content-Length:").append(length).append(CRLF);

        // 正文前的空行
        headInfo.append(CRLF);
    }
}
