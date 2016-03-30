package ApiTest.Facility;


import ApiTest.ApiResourceTest.ApiResourceTestAbstract;

/**
 * Created by s124392 on 30-3-2016.
 */
public class ApiTestFacility {
    private HttpConnection connection;

    public ApiTestFacility() {
        this.connection = new HttpConnection();
    }

    public boolean testEndpoint(String url, String method, String data, ApiResourceTestAbstract resourceTest) {
        String[] result = null;
        if (method.equals("GET")) { // Test a handleGet function.
            try {
                result = this.connection.sendGet(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (method.equals("POST")) { // Test a handlePost function.
            try {
                result = this.connection.sendPost(url, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else { // Test a handleDelete function.
            try {
                result = this.connection.sendDelete(url, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (result != null) { // Test the response.
            return resourceTest.test(result[0], result[1]);
        }
        return false;
    }

}
