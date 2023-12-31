
// demo on displaying meta data from a database

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLMetaData {
    public static void main(String[] args) {
        String connectionUrl = "jdbc:sqlserver://cxp-sql-03\\ltk30;"
                + "database=university;"
                + "user=dbuser;"
                + "password=csds341143sdsc;"
                + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=15;";
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            Statement stmt = connection.createStatement();
            {
                String SQL = "SELECT TOP 10 * FROM student";
                ResultSet rs = stmt.executeQuery(SQL);
                ResultSetMetaData rsmd = rs.getMetaData();
                // Display the column name and type.
                int cols = rsmd.getColumnCount();
                for (int i = 1; i <= cols; i++) {
                    System.out.println("Attr Name: " +
                            rsmd.getColumnName(i)
                            + ", Attr Type: " + rsmd.getColumnTypeName(i));
                }
                while (rs.next()) {
                    System.out.println(rs.getString(1) + "\t"
                            + rs.getString(2) + "\t" + rs.getString(3)
                            + "\t" + rs.getInt(4));
                }
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
