package mtd.tasker.protocol;

// Statuscodes for the protocol. Use these to send preapplied or custom messages to the client. theres probably a better way, i just found it to work.
public enum StatusCode {
    OK("1", "Ok"),
    NOT_FOUND("404", "Not Found"),
    SERVER_ERROR("500", "Internal Server Error"),
    BAD_REQUEST("400", "Bad Request");

    private final String code;
    private final String msg;

    StatusCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    StatusCode(int code)

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
