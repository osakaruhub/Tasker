package mtd.tasker.protocol;

/**
 * An Exception that trips when an Invalid command has been used.
 *
 */
public class InvalidCommandException extends RuntimeException {
    private String code;

    /**
     * create Exception with code.
     *
     * @param code 
     */
    public InvalidCommandException(String code) {
        super("Invalid Command: " + code);
    }

    public String getCode() {
        return this.code;
    }
}
