package main.java.org.example;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * CLI
 * a terminal User Interface (TUI) for the Tasker application
 */
public class CLI {
    public void printCal() {}
    public void addCal() {
        System.out.println("state your name:");
        name = sc.next();
        String timePattern = "(\\d{2}:\\d{2}) - (\\d{2}:\\d{2})";
        do {
            System.out.println("when? hh:mm - hh:mm");
            time = sc.next();
            Pattern compiledPattern = Pattern.compile(timePattern);
            Matcher matcher = compiledPattern.matcher(input);
            if (!matcher.matches()) {
                System.out.println("does not match pattern");
            }
        } while (!matcher.matches());
        String startTime = matcher.group(1);
        String endTime = matcher.group(2);
        System.out.println("tick purpose:\n" + listTags());

    }
        public String listTags() {
            str += Handler.getTagsCLI();
        return str;
        }

}
