package mtd.tasker.protocol;

// Requestcodes for the protocol. Use these to send requests to the server, which decodes to what the client wants.
public enum RequestCode {
    ADD("200"),
    DELETE("201"),
    GET("202"),
    SYNC("203"),
    LIST("204"),
    KICK("205"),
    SU("206");

    private final String code;

    RequestCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code;
    }
}

