package mtd.tasker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;
/**
 * CLI
 * a terminal User Interface (TUI) for the Tasker application
 */
public class TUI {
    Scanner sc = new Scanner(System.in);
    public void printCal() {}
    public void addCal() {
        System.out.println("state your name:");
        String name = sc.next();
        String timePattern = "(\\d{2}:\\d{2}) - (\\d{2}:\\d{2})";
        do {
            System.out.println("when? hh:mm - hh:mm");
            String time = sc.next();
            Pattern compiledPattern = Pattern.compile(timePattern);
            Matcher matcher = compiledPattern.matcher(input);
            if (!matcher.matches()) {
                System.out.println("does not match pattern");
            }
        } while (!matcher.matches());
        String startTime = matcher.group(1);
        String endTime = matcher.group(2);
        int tagsLength = -1; // change this to tags
        String tag;
        do {
            System.out.println("tick purpose:\n" + listTags());
            tag = sc.nextInt();
        } while (tag > tagsLength || tag < 0 );
        int tag = sc.nextInt();
        if (tag < tagsLength - 1) {
        } else if (tag == tabsLength){

        }
    }
        public String listTags() {
        String str = "";
            str += Handler.getTagsCLI();
        return str;
        }

}
