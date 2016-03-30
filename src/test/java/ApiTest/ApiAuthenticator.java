package ApiTest;

import ApiTest.Facility.HttpConnection;

/**
 * Created by s124392 on 30-3-2016.
 */
public class ApiAuthenticator {

    private static ApiAuthenticator instance = null;

    private ApiAuthenticator() {}

    public static ApiAuthenticator getInstance() {
        if (instance == null) {
            instance = new ApiAuthenticator();
        }
        return instance;
    }

    public String getToken() {
        HttpConnection connection = new HttpConnection();
        String[] request = null;
        try {
            request = connection.sendGet("http://localhost:8008/api/authenticate/test/test123");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (request == null) {
            throw new IllegalStateException("Could not get a token!");
        }
        String token = request[1].split(":")[1];
        token = token.substring(1, token.length() - 2);
        return token;
    }
}
