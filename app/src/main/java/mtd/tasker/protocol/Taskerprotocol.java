package mtd.tasker.protocol;

/**
 * TaskerProtocol
 */ 
// Statuscodes for the protocol. Use these to send preapplied or custom messages to the client. theres probably a better way, i just found it to work.
public enum StatusCode {
    OK(1, "Ok"),
    NOT_FOUND(404, "Not Found"),
    SERVER_ERROR(500, "Internal Server Error"),
    BAD_REQUEST(400, "Bad Request");

    private final int code;
    private final String msg;

    StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static StatusCode SEND(String msg) {
        return new StatusCode(2, msg);
    }

    @Override
    public String toString() {
        return this.code + " " + this.msg;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

/**
 * Response - class for handling Responses
 *
 * @param a the first integer to add
 * @param b the second integer to add
 * @return the sum of a and b
 */
public class Response {
 /*
   Creates the OK Response with content 
 * @param code the StatusCode OK
 * @param the content the server reponds with
 */
    public Response(StatusCode.OK code, String msg) {
        this.code = code;
    }

 /*
   Creates a Response with the normal StatusCode
 * @param code the StatusCode
 * */
    public Response(StatusCode code) {
        this.code = code;
        this.msg = code.getMessage();
    }

 /*
   gets the content inside the Response
 * @return code the StatusCode
 * */
    public String getContent() {
        return this.msg;
    }

 /*
   gets the StatusCode for the Response
 * @return code the StatusCode
 * */
    public StatusCode getStatusCode() {
        return this.code;
    }
}

// Requestcodes for the protocol. Use these to send requests to the server, which decodes to what the client wants.
public enum RequestCode {
    ADD("200"),
    DELETE("201"),
    GET("202"),
    SYNC("203");

    private final String code;

    RequestCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code;
    }
}

public class Request {
    public Request(RequestCode code, String msg) {}
}
