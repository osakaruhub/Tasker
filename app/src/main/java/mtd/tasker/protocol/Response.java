package mtd.tasker.protocol;

import mtd.tasker.protocol.StatusCode;

/**
 * Response - class for handling Responses
 *
 * @param a the first integer to add
 * @param b the second integer to add
 * @return the sum of a and b
 */
public class Response {
    private StatusCode code;
    private String content;
 /*
   Creates the OK Response with content 
 * @param code the StatusCode OK
 * @param the content the server reponds with
 */
    public Response(StatusCode code, String msg) {
        if (code != StatusCode.OK) {
            throw new IllegalArgumentException("Response Contructor call with message has to be enum value OK");
        }
        this.code = code;
    }

 /*
   Creates a Response with the normal StatusCode
 * @param code the StatusCode
 * */
    public Response(StatusCode code) {
        this.code = code;
        this.content = code.getMessage();
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
}

