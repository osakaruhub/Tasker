package mtd.tasker;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import mtd.tasker.protocol.RequestCode;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.*;
import java.text.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

public class KalenderApp {

    private static final String[] WOCHENTAGE = {
            "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag"
    };
    private static Calendar currentGUIDate;
    private static Calendar currentDate;
    private JTable table;
    private boolean admin = false;
    private String selected;

    public KalenderApp() {

        currentDate = Calendar.getInstance();
        clearTimeFields(currentDate);
        currentGUIDate = (Calendar) currentDate.clone();
        createKalenderUI();
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

        JButton AdminView = new JButton("Admin");
        AdminView.setBounds(50, 500, 70, 30);
        AdminView.addActionListener(e -> loginAdminView(AdminView));

        table = new JTable(new Object[6][7], WOCHENTAGE);
        table.setFont(new Font("Arial", Font.PLAIN, 10));
        table.setRowHeight(60);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 100, 600, 383);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (admin) {
                    handleTableClickAdmin(e);
                } else {
                    handleTableClick(e);
                }
            }
        });

        panel.add(monatJahrLabel);
        panel.add(prevMonthButton);
        panel.add(nextMonthButton);
        panel.add(AdminView);
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

    private void loginAdminView(JButton adminView) {
        JPopupMenu popupMenu = new JPopupMenu();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Oberer Bereich: Label und Passwortfeld nebeneinander
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel infoLabel = new JLabel("Geben Sie das Passwort ein:");
        JPasswordField passwordField = new JPasswordField(10);
        topPanel.add(infoLabel);
        topPanel.add(passwordField);

        // Unterer Bereich: Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loginButton = new JButton("Login");
        JButton closeButton = new JButton("Close");
        buttonPanel.add(loginButton);
        buttonPanel.add(closeButton);

        // Login-Logik
        loginButton.addActionListener(event -> {
            String password = new String(passwordField.getPassword());
            if ("1234".equals(password)) {
                infoLabel.setText("Login Erfolgreich");
                admin = true;
            } else {
                infoLabel.setText("Login Fehlgeschlagen");
            }
        });

        // Close-Button-Logik
        closeButton.addActionListener(e -> popupMenu.setVisible(false));

        // Panels hinzufügen
        mainPanel.add(topPanel);
        mainPanel.add(buttonPanel);
        popupMenu.add(mainPanel);

        // Popup-Menü anzeigen
        popupMenu.show(adminView, adminView.getWidth() / 2, adminView.getHeight() / 2);
    }

    private void handleTableClick(MouseEvent e) {
        int row = table.rowAtPoint(e.getPoint());
        int column = table.columnAtPoint(e.getPoint());
        Object value = table.getValueAt(row, column);

        if (value != null && !value.toString().isEmpty() && column < 5) {
            // Neues JDialog erstellen
            JDialog dialog = new JDialog();
            dialog.setTitle("Details für " + value.toString()); // Titel des Dialogs
            dialog.setModal(true); // Modal: Benutzer kann den Hauptframe nicht interagieren, solange der Dialog
                                   // geöffnet ist
            dialog.setSize(400, 300); // Größe des Dialogs
            dialog.setLocationRelativeTo(table); // Dialog erscheint in der Mitte des Fensters
            dialog.setLayout(new BorderLayout());

            // Information Panel
            JPanel informationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JTextField title = new JTextField("Beschreibung", 10);
            JTextField name = new JTextField("Name", 10);
            informationPanel.add(new JLabel("Titel:"));
            informationPanel.add(title);
            informationPanel.add(new JLabel("Name:"));
            informationPanel.add(name);

            // Button Panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton closeButton = new JButton("Abbrechen");
            closeButton.addActionListener(event -> dialog.setVisible(false));

            // JTable im Dialog

            // Option = Zeit
            // Details = frei / nict frei
            String[] columnNames = { "Zeit", "Verfügbarkeit" };

            ArrayList<Object[]> dataList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                // Check ob Zeit beriets vergeben ist
                String option = (i + 7) + "";
                String details = "Frei";

                // Erstelle ein Objekt-Array für jede Zeile
                Object[] dataObject = { option, details };

                // Füge die Zeile zur Liste hinzu
                dataList.add(dataObject);
            }

            Object[][] data = dataList.toArray(new Object[0][0]);

            JTable menuTable = new JTable(data, columnNames);
            menuTable.setFocusable(false); // Verhindert Fokusprobleme
            menuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            menuTable.setRowHeight(25);

            // Interaktion mit der JTable
            menuTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int selectedRow = menuTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        String option = menuTable.getValueAt(selectedRow, 0).toString();
                        String details = menuTable.getValueAt(selectedRow, 1).toString();
                        if (details.equals("Frei")) {
                            selected = option;
                        } else {
                            menuTable.clearSelection();
                        }
                        System.out.println("Selected: " + option + " - " + details);

                    }
                }
            });

            JButton actionButton = new JButton("Buchen");
            actionButton.addActionListener(event -> {
                String date = value.toString() + "." + selected + ".00";
                if (!selected.equals(null)) {
                    Handler.addEvent(date + ":" + name.getText() + ":" + title.getText());
                }
                dialog.setVisible(false); // Dialog schließen nach Buchung
            });

            // JScrollPane für die JTable
            JScrollPane scrollPane = new JScrollPane(menuTable);
            scrollPane.setPreferredSize(new Dimension(300, 150));

            buttonPanel.add(actionButton);
            buttonPanel.add(closeButton);

            // Komponenten zum Dialog hinzufügen
            dialog.add(informationPanel, BorderLayout.NORTH);
            dialog.add(scrollPane, BorderLayout.CENTER);
            dialog.add(buttonPanel, BorderLayout.SOUTH);

            // Dialog anzeigen
            dialog.setVisible(true);
        }
    }

    private void handleTableClickAdmin(MouseEvent e) { // admin view for the selected day
        int row = table.rowAtPoint(e.getPoint());
        int column = table.columnAtPoint(e.getPoint());
        Object value = table.getValueAt(row, column);
        String[] dateValues = value.toString().split("\\.");
        String date = "" + dateValues[0] + "-" + dateValues[1] + "-" + dateValues[2];
        // String[] dates = Handler.addEvent(RequestCode.GET, date);
        if (value != null && !value.toString().isEmpty() && column < 5) {
            // Neues JDialog erstellen
            JDialog dialog = new JDialog();
            dialog.setTitle("Details für " + value.toString()); // Titel des Dialogs
            dialog.setModal(true); // Modal: Benutzer kann den Hauptframe nicht interagieren, solange der Dialog
                                   // geöffnet ist
            dialog.setSize(400, 300); // Größe des Dialogs
            dialog.setLocationRelativeTo(table); // Dialog erscheint in der Mitte des Fensters
            dialog.setLayout(new BorderLayout());

            // Option = Zeit
            // Details = frei / nict frei
            String[] columnNames = { "Zeit", "Verfügbarkeit" };

            ArrayList<Object[]> dataList = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                // Check ob Zeit beriets vergeben ist
                String option = "Zeit" + (i + 1);
                String details = "Frei";

                // Erstelle ein Objekt-Array für jede Zeile
                Object[] dataObject = { option, details };

                // Füge die Zeile zur Liste hinzu
                dataList.add(dataObject);
            }

            Object[][] data = dataList.toArray(new Object[0][0]);

            JTable menuTable = new JTable(data, columnNames);
            menuTable.setFocusable(false); // Verhindert Fokusprobleme
            menuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            menuTable.setRowHeight(25);

            // Interaktion mit der JTable
            menuTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int selectedRow = menuTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        String option = menuTable.getValueAt(selectedRow, 0).toString();
                        String details = menuTable.getValueAt(selectedRow, 1).toString();
                        System.out.println("Selected: " + option + " - " + details);

                        // Hier Aktionen ausführen, wenn eine Zeile ausgewählt wurde
                    }
                }
            });

            // JScrollPane für die JTable
            JScrollPane scrollPane = new JScrollPane(menuTable);
            scrollPane.setPreferredSize(new Dimension(300, 150));

            dialog.add(scrollPane, BorderLayout.CENTER);

            // Dialog anzeigen
            dialog.setVisible(true);
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

}
