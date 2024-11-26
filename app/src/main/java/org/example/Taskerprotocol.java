package main.java.org.example;

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

// Requestcodes for the protocol. Use these to send requests to the server, which decodes to what the client wants.
public enum Request {
    ADD("200"),
    DELETE("201"),
    GET("202"),
    SYNC("203");

    private final String code;

    Request(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code;
    }
}
