import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

class Request {
    private String method;
    private String relativePath;
    private Map<String, List<String>> parameterMapValues;

    private final static String CRLF = "\r\n";
    private String requestInfo;

    private Request() {
        method = "";
        relativePath = "";
        parameterMapValues = new HashMap<>();
        requestInfo = "";
    }

    Request(InputStream inputStream) {
        this();
        try {
            byte[] data = new byte[20480];
            int length = inputStream.read(data);
            requestInfo = new String(data, 0, length);
            System.out.println(requestInfo);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        parseRequestInfo();
    }

    private void parseRequestInfo() {
        // 如果请求内容为空
        if (requestInfo == null || (requestInfo = requestInfo.trim()).equals("")) {
            return;
        }

        String paramString = "";

        // 获取请求方式
        String firstLine = requestInfo.substring(0, requestInfo.indexOf(CRLF));
        int index = requestInfo.indexOf("/");
        method = firstLine.substring(0, index).trim();
        String urlStr = firstLine.substring(index, firstLine.indexOf("HTTP/")).trim();
        if (this.method.equals("POST")) {
            this.relativePath = urlStr;
            paramString = requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
        } else if (this.method.equals("GET")) {
            if (urlStr.contains("?")) {
                String[] urlArray = urlStr.split("\\?");
                relativePath = urlArray[0];
                paramString = urlArray[1];
            } else {
                relativePath = urlStr;
            }
        }

        if (paramString.equals("")) {
            return;
        }

        parseParams(paramString);
    }

    private void parseParams(String paramString) {
        StringTokenizer tokenizer = new StringTokenizer(paramString, "&");
        while (tokenizer.hasMoreTokens()) {
            String keyValue = tokenizer.nextToken();
            String[] keyValues = keyValue.split("=");
            if (keyValues.length == 1) {
                keyValues = Arrays.copyOf(keyValues, 2);
                keyValues[1] = null;
            }

            String key = keyValues[0].trim();
            String value = null == keyValues[1] ? null : decode(keyValues[1]);

            if (!parameterMapValues.containsKey(key)) {
                parameterMapValues.put(key, new ArrayList<>());
            }

            List<String> values = parameterMapValues.get(key);
            values.add(value);
        }
    }

    // 目前是UTF-8
    private String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    private String[] getParameterValues(String name) {
        List<String> values = null;
        if ((values = parameterMapValues.get(name)) == null) {
            return null;
        }
        return values.toArray(new String[0]);
    }


    String getParameter(String name) {
        String[] values = getParameterValues(name);
        if (null == values) {
            return null;
        }
        return values[0];
    }

    String getRelativePath() {
        return relativePath;
    }
}
