package ApiServer.Status;

import org.restlet.data.Status;

public abstract class StatusAbstract {
    String message = null;
    Integer status;

    /**
     * Init a status response type
     * @param message Custom message to give with the status
     */
    public StatusAbstract(String message) {
        this.message = this.getDefaultMessage();
        if(message != null) this.message = message;
        this.status = getStatus().getCode();
    }

    /**
     * Get the HTTP status of this status
     *
     * @return The HTTP status of this status
     */
    public abstract Status getStatus();

    /**
     * Get the default message for this status
     *
     * @return the default message for this status
     */
    public abstract String getDefaultMessage();
}
