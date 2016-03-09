package ApiServer.Resource;

import Model.Test;
import ApiServer.Serializer.TestSerializer;
import org.restlet.Request;
import org.restlet.Response;

/**
 * Created by Thomas on 17-2-2016.
 */
public class TestResource extends ApiResource {
    @Override
    protected void handleGet(Request request, Response response) throws IllegalStateException {
        String aux = String.valueOf(request.getAttributes().get("data"));
        String[] data = aux.split(",");
        this.returnResponse(response, new Test(), new TestSerializer());
    }
}
