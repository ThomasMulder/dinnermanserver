package ApiTest.Facility;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Adapted from http://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
 */

public class HttpConnection {

    private final String USER_AGENT = "Mozilla/5.0";

    // HTTP GET request
    public String[] sendGet(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        String[] result = new String[2];

        int responseCode = con.getResponseCode();
        result[0] = String.valueOf(responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        result[1] = response.toString();
        return result;
    }

    // HTTP POST request
    public String[] sendPost(String url, String data) throws Exception {
        return sendDataRequest(url, "POST", data);
    }

    // HTTP DELETE request
    public String[] sendDelete(String url, String data) throws Exception {
        return sendDataRequest(url, "DELETE", data);
    }

    private String[] sendDataRequest(String url, String method, String data) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod(method);
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        // Send request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(data);
        wr.flush();
        wr.close();

        String[] result = new String[2];

        int responseCode = con.getResponseCode();
        result[0] = String.valueOf(responseCode);


        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        result[1] = response.toString();
        return result;
    }

}
