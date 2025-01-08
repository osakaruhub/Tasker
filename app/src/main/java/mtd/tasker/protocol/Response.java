package mtd.tasker.protocol;

import java.io.Serializable;

/**
 * Response - class for handling Responses
 */
public class Response implements Serializable {
    private static final long serialVersionUID = 1L; // Recommended for Serializable classes
    private StatusCode code;
    private String content;

 /**
   Creates the OK Response with content 
 * @param code the StatusCode OK
 * @param the content the server reponds with
 */
    public Response(StatusCode code, String msg) {
        if (code != StatusCode.OK) {
            throw new IllegalArgumentException("Response Contructor call with message has to be enum value OK");
        }
        this.code = code;
        this.content = msg;
    }

 /**
   Creates a Response with the normal StatusCode
 * @param code the StatusCode
 * */
    public Response(StatusCode code) {
        this.code = code;
        this.content = code.getMessage();
    }

 /**
   gets the content inside the Response
   @return content the content inside the Response
 * */
    public String getContent() {
        return this.content;
    }

 /**
   gets the content inside the Response
   @return code the StatusCode instance
 * */
    public StatusCode getStatusCode() {
        return this.code;
    }

 /**
   gets the StatusCode for the Response
 * @return code the code of the StatusCode
 * */
    public String getCode() {
        return this.code.toString();
    }
}

