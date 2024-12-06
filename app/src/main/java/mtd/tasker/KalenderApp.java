package mtd.tasker;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.time.Year;

public class KalenderApp {

    private static final String[] Wochentage = {
            "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag"
    };
    private static Calendar currentGUIDate;
    private static Calendar currentDate;
    private static Object[][] dayData = new Object[6][7];
    private JTable table = null;


    public KalenderApp(){
        currentDate = Calendar.getInstance();
        currentDate.clear(Calendar.MILLISECOND);
        currentDate.clear(Calendar.SECOND);
        currentDate.clear(Calendar.MINUTE);
        currentDate.clear(Calendar.HOUR);
        currentGUIDate = Calendar.getInstance();
    }

    public void createKalenderUI() {
        // JFrame initialisieren
        JFrame frame = new JFrame("Kalender");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setLocationRelativeTo(null);


        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Monat/ Jahr Text
        JLabel monatJahrLabel = new JLabel();
        monatJahrLabel.setBounds(250, 30, 200, 30);
        monatJahrLabel.setFont(new Font("Arial", Font.BOLD, 20));
        monatJahrLabel.setHorizontalAlignment(SwingConstants.CENTER);
        updateMonatJahrLabel(monatJahrLabel);

        // Buttons für Navigation der Monate und Jahre
        JButton prevMonthButton = new JButton("<< Monat zurück");
        prevMonthButton.setBounds(50, 30, 150, 30);
        prevMonthButton.addActionListener(e -> {
            Calendar temp = (Calendar) currentGUIDate.clone();
            temp.add(Calendar.MONTH, -1);
            System.out.println("Temp Datum: " + temp.getTime());
            System.out.println("Test Datum: " + currentGUIDate.getTime());
            System.out.println("aktuelles Datum: " + Calendar.getInstance().getTime());
            System.out.println(temp.before(currentDate));
            if(!temp.before(currentDate)){
                System.out.println("Changed test date");
                currentGUIDate.add(Calendar.MONTH, -1);

                updateMonatJahrLabel(monatJahrLabel);
                updateKalanderTable();
            }

        });

        JButton nextMonthButton = new JButton("Monat vor >>");
        nextMonthButton.setBounds(500, 30, 150, 30);
        nextMonthButton.addActionListener(e -> {
            currentGUIDate.add(Calendar.MONTH, +1);

            updateMonatJahrLabel(monatJahrLabel);
            updateKalanderTable();
        });

        table = new JTable(dayData, Wochentage);
        table.setFont(new Font("Arial", Font.PLAIN, 10));
        table.setRowHeight(60);
        table.setEnabled(false);


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 100, 600, 383);



        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int row = table.rowAtPoint(e.getPoint());
                int column = table.columnAtPoint(e.getPoint());
                System.out.println("column: " + column + " row: " + row + " Value: " + table.getValueAt(row, column));
                JPopupMenu jPopupMenu = new JPopupMenu();
                jPopupMenu.add( "" +table.getValueAt(row, column));
                JButton closeButton = new JButton("Close");
                closeButton.addActionListener(event ->{
                    jPopupMenu.setVisible(false);
                });
                jPopupMenu.add(closeButton);
                jPopupMenu.setVisible(true);
            }
        });

        panel.add(monatJahrLabel);
        panel.add(prevMonthButton);
        panel.add(nextMonthButton);
        panel.add(scrollPane);


        frame.add(panel);
        frame.setVisible(true);

        updateKalanderTable();
        updateMonatJahrLabel(monatJahrLabel);
    }

    public void updateKalanderTable() {
        Calendar date = (Calendar) currentGUIDate.clone();
        date.set(Calendar.DAY_OF_MONTH, 1);
        boolean firstWeak = false;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        System.out.println(sdf.format(date.getTime()));
        // req data for the current month
        System.out.println(getFirstDayOfMonth(date));
        for (int i = 0; i < table.getColumnCount()-1; i++) {
            int startJ = (i == 0) ? getFirstDayOfMonth(date) : 0;
            for (int j = startJ; j < 7; j++) {
                table.setValueAt("",i,j);
                if (currentDate.get(Calendar.MONTH) != date.get(Calendar.MONTH) ){
                    break;
                }
                else{

                    table.setValueAt(sdf.format(date.getTime()),i,j);

                    date.add(Calendar.DAY_OF_MONTH, 1);
                }
            }

        }
    }


    // Monat und Jahr Label aktualisieren
    private static void updateMonatJahrLabel(JLabel monatJahrLabel) {
        String monatName = new SimpleDateFormat("MMMM").format(new GregorianCalendar(currentGUIDate.get(Calendar.YEAR), currentGUIDate.get(Calendar.MONTH), 1).getTime());
        monatJahrLabel.setText(monatName + " " + currentGUIDate.get(Calendar.YEAR));
    }

    // Erster Wochentag des Monats
    private static int getFirstDayOfMonth(Calendar calendar) {
        Calendar cal = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        switch(cal.get(Calendar.DAY_OF_WEEK)){
            case 1:
                return 6; // Sonntag
            case 2:
                return 0; // Montag
            case 3:
                return 1; // Dienstag
            case 4:
                return 2;   //Mittwoch
            case 5:
                return 3; //Donnerstag
            case 6:
                return 4; // Freitag
            case 7:
                return 5; // Samstag
            default:
                return -99;
        }
       // Wochentag als Index (1 = Sonntag, 7 = Samstag)

    }

    // Anzahl der Tage im Monat
    private static int getDaysInMonth() {
        Calendar cal = new GregorianCalendar(currentGUIDate.get(Calendar.YEAR), currentGUIDate.get(Calendar.MONTH), 1);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static void main(String[] args) {

        KalenderApp kalenderApp = new KalenderApp();
        kalenderApp.createKalenderUI();
    }


}

