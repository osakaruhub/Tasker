package mtd.tasker.protocol;

public enum StatusCode {
    OK("1", "Ok"),
    NOT_FOUND("404", "Not Found"),
    SERVER_ERROR("500", "Internal Server Error"),
    BAD_REQUEST("400", "Bad Request"),
    PERMISSION_ERROR("666", "Invalid permission"),
    UNKNOWN("000", "unknown error");

    private final String code;
    private final String msg;

    StatusCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return this.code + " " + this.msg;
    }

    /**
     * finds the StatusCode from a given String.
     *
     * @param code 
     * @throws InvalidCommandException(code)
     */
    static public StatusCode fromCode(String code) {
        for (StatusCode stat : StatusCode.values()) {
            if (stat.getCode().equals(code)) {
                return stat;
            }
        }
        throw new InvalidCommandException(code);
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.msg;
    }
}
