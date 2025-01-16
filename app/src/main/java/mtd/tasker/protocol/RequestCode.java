package mtd.tasker.protocol;

public enum RequestCode {
    ADD("200"),
    DELETE("201"),
    GET("202"),
    SYNC("203"),
    LIST("204"),
    KICK("205"),
    SU("206"),
    TEST("420"),
    EXIT("998"),
    CLOSE("999");

    private final String code;

    RequestCode(String code) {
        this.code = code;
    }

    /**
     * finds the RequestCode from a given String.
     *
     * @param code 
     * @throws throw new InvalidCommandException(code); 
     */
    static public RequestCode fromCode(String code) {
        for (RequestCode req : RequestCode.values()) {
            if (req.toString().equals(code)) {
                return req;
            }
        }
        throw new InvalidCommandException(code);
    }

    @Override
    public String toString() {
        return this.code;
    }
}

