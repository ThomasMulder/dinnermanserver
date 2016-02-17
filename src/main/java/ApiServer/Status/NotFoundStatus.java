package ApiServer.Status;

import org.restlet.data.Status;

public class NotFoundStatus extends StatusAbstract {
    public NotFoundStatus(String message) {
        super(message);
    }

    @Override
    public Status getStatus() {
        return Status.CLIENT_ERROR_NOT_FOUND;
    }

    @Override
    public String getDefaultMessage() {
        return "Resource not found";
    }
}
