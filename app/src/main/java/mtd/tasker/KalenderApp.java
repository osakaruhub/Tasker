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
    private static Calendar test;
    private static Calendar aktuellesDatum;
    private static Object[][] tagesDaten = new Object[6][7];
    private JTable table = null;

    public KalenderApp(){
        aktuellesDatum = Calendar.getInstance();
        aktuellesDatum.clear(Calendar.MILLISECOND);
        aktuellesDatum.clear(Calendar.SECOND);
        aktuellesDatum.clear(Calendar.MINUTE);
        aktuellesDatum.clear(Calendar.HOUR);
        test = Calendar.getInstance();
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
            Calendar temp = (Calendar) test.clone();
            temp.add(Calendar.MONTH, -1);
            System.out.println("Temp Datum: " + temp.getTime());
            System.out.println("Test Datum: " + test.getTime());
            System.out.println("aktuelles Datum: " + Calendar.getInstance().getTime());
            System.out.println(temp.before(aktuellesDatum));
            if(!temp.before(aktuellesDatum)){
                System.out.println("Changed test date");
                test.add(Calendar.MONTH, -1);

                updateKalender(monatJahrLabel);
            }

        });

        JButton nextMonthButton = new JButton("Monat vor >>");
        nextMonthButton.setBounds(500, 30, 150, 30);
        nextMonthButton.addActionListener(e -> {
            test.add(Calendar.MONTH, +1);

            updateKalender(monatJahrLabel);
        });

        table = new JTable(tagesDaten, Wochentage);
        table.setFont(new Font("Arial", Font.PLAIN, 18));
        table.setRowHeight(60);
        table.setEnabled(false);


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 100, 600, 383);



        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int row = table.rowAtPoint(e.getPoint());
                int column = table.columnAtPoint(e.getPoint());


            }
        });

        panel.add(monatJahrLabel);
        panel.add(prevMonthButton);
        panel.add(nextMonthButton);
        panel.add(scrollPane);


        frame.add(panel);
        frame.setVisible(true);

        updateKalanderTable();
        updateKalender(monatJahrLabel);
    }

    public void updateKalanderTable() {
        for (int i = 0; i < table.getColumnCount(); i++) {
            for (int j = 0; j < table.getRowCount(); j++) {
                if(i == 0){
                   j = getFirstDayOfMonth();
                }
            }

        }
    }

    public static void updateKalender(JLabel monatJahrLabel) {
        updateMonatJahrLabel(monatJahrLabel);
    }



    // Monat und Jahr Label aktualisieren
    private static void updateMonatJahrLabel(JLabel monatJahrLabel) {
        String monatName = new SimpleDateFormat("MMMM").format(new GregorianCalendar(test.get(Calendar.YEAR), test.get(Calendar.MONTH), 1).getTime());
        monatJahrLabel.setText(monatName + " " + test.get(Calendar.YEAR));
    }

    // Erster Wochentag des Monats
    private static int getFirstDayOfMonth() {
        Calendar cal = new GregorianCalendar(test.get(Calendar.YEAR), test.get(Calendar.MONTH), 1);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;  // Wochentag als Index (0 = Montag, 6 = Sonntag)
    }

    // Anzahl der Tage im Monat
    private static int getDaysInMonth() {
        Calendar cal = new GregorianCalendar(test.get(Calendar.YEAR), test.get(Calendar.MONTH), 1);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static void main(String[] args) {

        KalenderApp kalenderApp = new KalenderApp();
        kalenderApp.createKalenderUI();
    }


}
