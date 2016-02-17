package ApiServer.Status;

import org.restlet.data.Status;

public class SuccessStatus extends StatusAbstract {

    public SuccessStatus(String message) {
        super(message);
    }

    @Override
    public Status getStatus() {
        return Status.SUCCESS_OK;
    }

    @Override
    public String getDefaultMessage() {
        return "success";
    }
}
