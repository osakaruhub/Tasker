package main.java.org.example;

/**
 * Requests
 */
public enum StatusCode {
    OK(1, "Ok"),
    OCCUPIED(404, "Not Found"),
    SERVER_ERROR(500, "Internal Server Error"),
    BAD_REQUEST(400, "Bad Request");

    private final int code;
    private final String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

public enum Request {
    ADD("200"),
    DELETE("201"),
    GET("202"),
    SYNC("203"),
}
