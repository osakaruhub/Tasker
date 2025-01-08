// sql connection
package mtd.tasker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventHandler {
    static public Boolean addEvent(Event event) {
        String query = "SELECT COUNT(*) FROM appointments WHERE appointment_date = ?";

        try (Connection con = SQLConnection.getInstance().getConnection(); PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setDate(1, new Date(event.getDate().getTime()));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        return false;
        }
    }

    static public Boolean removeEvent(String id) {
        String query = "DELETE from appointments WHERE appointment_id = ?";

        try (Connection con = SQLConnection.getInstance().getConnection(); PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setNString(1, id);
            return pstmt.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return false;
        }
    }
}
