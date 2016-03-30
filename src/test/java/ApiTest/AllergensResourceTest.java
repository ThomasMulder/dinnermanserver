package ApiTest;

import ApiTest.ApiResourceTest.AllergensAddResourceTest;
import ApiTest.ApiResourceTest.AllergensDeleteResourceTest;
import ApiTest.Facility.ApiTestFacility;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by s124392 on 30-3-2016.
 */
public class AllergensResourceTest {
    private ApiTestFacility apiTestFacility = new ApiTestFacility();

    @Test
    public void testHandlePostAndDelete() throws Exception {
        String token = ApiAuthenticator.getInstance().getToken();
        assertTrue(this.apiTestFacility.testEndpoint("http://localhost:8008/api/user/test/" + token + "/addAllergens", "POST", "cheese", new AllergensAddResourceTest()));
        assertTrue(this.apiTestFacility.testEndpoint("http://localhost:8008/api/user/test/" + token + "/deleteAllergens", "DELETE", "cheese", new AllergensAddResourceTest()));
    }
}