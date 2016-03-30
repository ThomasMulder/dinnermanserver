package ApiTest.ApiResourceTest;

/**
 * Created by s124392 on 30-3-2016.
 */
public class AllergensDeleteResourceTest extends ApiResourceTestAbstract {

    @Override
    public boolean test(String responseCode, String responseBody) {
        return responseCode.equals("200");
        // May make this more rigorous by actually checking the database.
    }
}
