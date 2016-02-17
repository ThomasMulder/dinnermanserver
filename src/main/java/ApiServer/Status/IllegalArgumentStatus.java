package ApiServer.Status;

import org.restlet.data.Status;

public class IllegalArgumentStatus extends StatusAbstract {
    public IllegalArgumentStatus(String message) {
        super(message);
    }

    @Override
    public Status getStatus() {
        return Status.CLIENT_ERROR_BAD_REQUEST;
    }

    @Override
    public String getDefaultMessage() {
        return "Illegal argument";
    }
}
