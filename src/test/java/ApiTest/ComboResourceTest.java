package ApiTest;

import ApiTest.ApiResourceTest.RecipeListTest;
import ApiTest.Facility.ApiTestFacility;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by s124392 on 30-3-2016.
 */
public class ComboResourceTest {

    @Test
    public void testHandleGet() throws Exception {
        String token = ApiAuthenticator.getInstance().getToken();
        ApiTestFacility apiTestFacility = new ApiTestFacility();
        assertTrue(apiTestFacility.testEndpoint("http://localhost:8008/api/recipe/test/" + token + "/combo/chicken/0/200/0/200/0/200/0/200", "GET", "", new RecipeListTest()));
    }
}