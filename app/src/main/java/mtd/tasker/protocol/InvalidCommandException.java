package mtd.tasker.protocol;

public class InvalidCommandException extends RuntimeException {
    private String code;
    public InvalidCommandException(String code) {
        super("Invalid Command" + code);
    }

    public String getCode() {
        return this.code;
    }
}
