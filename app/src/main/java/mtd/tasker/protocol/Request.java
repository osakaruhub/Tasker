package mtd.tasker.protocol;

/**
 * Class for Request for the socket. A Request consists of a RequestCode and its content.
 *
 */
public class Request {
    private RequestCode code;
    private String content;

 /*
 * Creates a Request with a message attached
 * @param code the StatusCode
 */
    public Request(RequestCode code, String content) {
        this.code = code;
        this.content = content;
    }

 /*
 * Creates a Request with the default message for the request
 * @param code the StatusCode
 */
    public Request(RequestCode code) {
        this.code = code;
    }

 /*
   gets the content inside the Response
 * @return code the StatusCode
 * */
    public String getContent() {
        return this.content;
    }

 /*
   gets the StatusCode for the Response
 * @return code the StatusCode
 * */
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
