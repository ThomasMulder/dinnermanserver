package ApiTest;

import ApiTest.ApiResourceTest.AuthenicationResourceTest;
import ApiTest.Facility.ApiTestFacility;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by s124392 on 30-3-2016.
 */
public class AuthenticateResourceTest {

    @Test
    public void testHandleGet() throws Exception {
        ApiTestFacility apiTestFacility = new ApiTestFacility();
        assertTrue(apiTestFacility.testEndpoint("http://localhost:8008/api/authenticate/test/test123", "GET", "", new AuthenicationResourceTest()));
    }
}