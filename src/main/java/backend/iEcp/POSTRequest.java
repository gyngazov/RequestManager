package backend.iEcp;

import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public final class POSTRequest {
    public static final int MIN_REQUEST_ID = 28631;

    public static final String LIST_REQUEST = "https://apinew.iecp.ru/api/external/v2/request/list";
    public static final String CREATE_REQUEST = "https://apinew.iecp.ru/api/external/v2/request/create";
    public static final String CHANGE_REQUEST = "https://apinew.iecp.ru/api/external/v2/request/change";
    public static final String VIEW_REQUEST = "https://apinew.iecp.ru/api/external/v2/request/view";
    public static final String ATTACH_FILE = "https://apinew.iecp.ru/api/external/v2/request/attach_file";
    public static final String CLIENT_DOCUMENT = "https://apinew.iecp.ru/api/external/v2/request/client_docs";
    public static final String RESULT = "https://apinew.iecp.ru/api/external/v2/request/result";

    private static final int CONNECT_TIME_OUT = 10_000;
    private static final int READ_TIME_OUT = 60_000;

    private final int responseCode;
    private final String response;

    public POSTRequest(String url, @NotNull String json) throws IOException {
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(url).openConnection();

        httpsURLConnection.setDoInput(true);
        httpsURLConnection.setDoOutput(true);
        httpsURLConnection.setConnectTimeout(CONNECT_TIME_OUT);
        httpsURLConnection.setReadTimeout(READ_TIME_OUT);
        httpsURLConnection.setRequestMethod("POST");
        httpsURLConnection.setRequestProperty("Accept", "application/json");
        httpsURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        httpsURLConnection.setUseCaches(false);
        httpsURLConnection.connect();

        try (OutputStream outputStream = httpsURLConnection.getOutputStream()) {
            outputStream.write(json.getBytes(StandardCharsets.UTF_8));
        }

        responseCode = httpsURLConnection.getResponseCode();

        if (responseCode == HttpsURLConnection.HTTP_OK) {
            response = getData(httpsURLConnection.getInputStream());
        } else {
            response = getData(httpsURLConnection.getErrorStream())
                    .replaceAll("\s+", " ")
                    .replace("№", "№ ")
                    .replace("\"", "")
                    .concat(".");
        }
    }

    private @NotNull String getData(InputStream inputStream) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        }
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponse() {
        return response;
    }
}
