package mtd.tasker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class KalenderApp {

    private static final String[] WOCHENTAGE = {
            "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag"
    };

    private static Calendar currentGUIDate;
    private static Calendar currentDate;
    private static Client c;
    private JTable table;

    public KalenderApp() {
        // Initialisiere die aktuellen Daten
        currentDate = Calendar.getInstance();
        clearTimeFields(currentDate);
        currentGUIDate = (Calendar) currentDate.clone();
    }

    private void clearTimeFields(Calendar calendar) {
        calendar.clear(Calendar.MILLISECOND);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.HOUR);
    }

    public void createKalenderUI() {
        JFrame frame = new JFrame("Kalender");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(null);

        JLabel monatJahrLabel = new JLabel();
        monatJahrLabel.setBounds(250, 30, 200, 30);
        monatJahrLabel.setFont(new Font("Arial", Font.BOLD, 20));
        monatJahrLabel.setHorizontalAlignment(SwingConstants.CENTER);
        updateMonatJahrLabel(monatJahrLabel);

        JButton prevMonthButton = new JButton("<< Monat zurück");
        prevMonthButton.setBounds(50, 30, 150, 30);
        prevMonthButton.addActionListener(e -> navigateMonth(monatJahrLabel, -1));

        JButton nextMonthButton = new JButton("Monat vor >>");
        nextMonthButton.setBounds(500, 30, 150, 30);
        nextMonthButton.addActionListener(e -> navigateMonth(monatJahrLabel, 1));

        table = new JTable(new Object[6][7], WOCHENTAGE);
        table.setFont(new Font("Arial", Font.PLAIN, 10));
        table.setRowHeight(60);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 100, 600, 383);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleTableClick(e);
            }
        });

        panel.add(monatJahrLabel);
        panel.add(prevMonthButton);
        panel.add(nextMonthButton);
        panel.add(scrollPane);

        frame.add(panel);
        frame.setVisible(true);

        updateKalenderTable();
    }

    private void navigateMonth(JLabel monatJahrLabel, int delta) {
        Calendar temp = (Calendar) currentGUIDate.clone();
        temp.add(Calendar.MONTH, delta);

        if (delta < 0 && temp.before(currentDate)) {
            return;
        }

        currentGUIDate.add(Calendar.MONTH, delta);
        updateMonatJahrLabel(monatJahrLabel);
        updateKalenderTable();
    }

    private void handleTableClick(MouseEvent e) {
        int row = table.rowAtPoint(e.getPoint());
        int column = table.columnAtPoint(e.getPoint());
        Object value = table.getValueAt(row, column);

        if (value != null && !value.toString().isEmpty()) {
            JPopupMenu popupMenu = new JPopupMenu();

            JMenuItem infoItem = new JMenuItem("Information: " + value.toString());
            popupMenu.add(infoItem);

            JTextField nameField = new JTextField();
            JTextField tagField = new JTextField();
            Object[] message = {
                "Name:", nameField,
                "Grund:", tagField,
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Register", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION && true) {
                JOptionPane.showMessageDialog(table, "Termin gebucht!"); // TODO: add functionality
            }

            popupMenu.show(table, e.getX(), e.getY());
        }
    }

    private void updateKalenderTable() {
        Calendar date = (Calendar) currentGUIDate.clone();
        date.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayIndex = getFirstDayOfMonth(date);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                table.setValueAt("", i, j);
            }
        }

        int row = 0;
        for (int day = 1; day <= date.getActualMaximum(Calendar.DAY_OF_MONTH); day++) {
            int column = (firstDayIndex + day - 1) % 7;
            row = (firstDayIndex + day - 1) / 7;
            table.setValueAt(sdf.format(date.getTime()), row, column);
            date.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    private void updateMonatJahrLabel(JLabel monatJahrLabel) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
        monatJahrLabel.setText(sdf.format(currentGUIDate.getTime()));
    }

    private int getFirstDayOfMonth(Calendar calendar) {
        Calendar firstDay = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        int dayOfWeek = firstDay.get(Calendar.DAY_OF_WEEK);
        return (dayOfWeek + 5) % 7; // Montag als erster Tag
    }

    public static void main(String[] args) {
        try {
            c = (args.length == 2 && args[0] != null && args[1] != null)?new Client(args[0], Integer.parseInt(args[1])):new Client();
        } catch (NumberFormatException e) {
            System.out.println("not a port: " + args[1]);
            System.exit(1);
        }
        new KalenderApp().createKalenderUI();
    }
}
