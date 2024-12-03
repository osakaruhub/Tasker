package mtd.tasker;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class KalenderApp {

    private static final String[] Wochentage = {
        "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag"
    };

    private static int monat = Calendar.JANUARY; 
    private static int jahr = 2024;  
    private static Object[][] tagesDaten = new Object[6][7];  

    public KalenderApp(){
        monat = Calendar.getInstance().get(Calendar.MONTH);
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
            if (monat > 0) {
                monat--;
            } else {
                monat = Calendar.DECEMBER;
                jahr--;
            }
            updateKalender(monatJahrLabel);
        });

        JButton nextMonthButton = new JButton("Monat vor >>");
        nextMonthButton.setBounds(500, 30, 150, 30);
        nextMonthButton.addActionListener(e -> {
            if (monat < Calendar.DECEMBER) {
                monat++;
            } else {
                monat = Calendar.JANUARY;
                jahr++;
            }
            updateKalender(monatJahrLabel);
        });

        JTable table = new JTable(tagesDaten, Wochentage);
        table.setFont(new Font("Arial", Font.PLAIN, 18));
        table.setRowHeight(60);  
        table.setEnabled(false);  
    

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 100, 600, 383);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               
                System.out.println(table.rowAtPoint(e.getPoint()));
                System.out.println(table.columnAtPoint(e.getPoint()));
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

       
        updateKalender(monatJahrLabel);
    }

    public static void updateKalender(JLabel monatJahrLabel) {
      
        updateMonatJahrLabel(monatJahrLabel);

        int firstDayOfMonth = getFirstDayOfMonth();
        int daysInMonth = getDaysInMonth();

       
    }

    // Monat und Jahr Label aktualisieren
    private static void updateMonatJahrLabel(JLabel monatJahrLabel) {
        String monatName = new SimpleDateFormat("MMMM").format(new GregorianCalendar(jahr, monat, 1).getTime());
        monatJahrLabel.setText(monatName + " " + jahr);
    }

    // Erster Wochentag des Monats
    private static int getFirstDayOfMonth() {
        Calendar cal = new GregorianCalendar(jahr, monat, 1);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;  // Wochentag als Index (0 = Montag, 6 = Sonntag)
    }

    // Anzahl der Tage im Monat
    private static int getDaysInMonth() {
        Calendar cal = new GregorianCalendar(jahr, monat, 1);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static void main(String[] args) {
       
        KalenderApp kalenderApp = new KalenderApp();
        kalenderApp.createKalenderUI();
    }
}