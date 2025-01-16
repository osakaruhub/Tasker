package mtd.tasker.protocol;

import java.io.Serializable;

import mtd.tasker.Event;
import mtd.tasker.protocol.InvalidCommandException;

/**
 * Class for Request for the socket. A Request consists of a RequestCode and its
 * content.
 *
 */
public class Request implements Serializable {

    private static final long serialVersionUID = 1L; // Recommended for Serializable classes
    private RequestCode code;
    private String content;

    /*
     * Creates a Request with a message attached
     * 
     * @param code the StatusCode
     */
    public Request(RequestCode code, String content) {
        this.code = code;
        this.content = content;
    }

    /*
     * Creates a Request with the default message for the request
     * 
     * @param code the StatusCode
     */
    public Request(RequestCode code) {
        this.code = code;
    }

    public Request(RequestCode code, Event event) {
        this.code = code;
        this.content = event.getPerson() + ":" + event.getDate().toString() + ":" + event.getTag();
    }

    /*
     * gets the content inside the Response
     * 
     * @return code the StatusCode
     */
    public String getContent() {
        return this.content;
    }

    /*
     * gets the StatusCode for the Response
     * 
     * @return code the StatusCode
     */
    public String getCode() {
        return this.code.toString();
    }

    public RequestCode getRequestCode() {
        return this.code;
    }

    @Override
    public String toString() {
        return code + " " + content;
    }

}
