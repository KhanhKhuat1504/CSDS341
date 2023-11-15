
// This code file makes a SQL Connection and runs a SQL Insert DML statement

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLInsertRow {
    // Connect to your database.
    // Replace server name, username, and password with your credentials
    public static void main(String[] args) {
        String connectionUrl = "jdbc:sqlserver://cxp-sql-03\\ltk30;"
                + "database=university;"
                + "user=dbuser;"
                + "password=csds341143sdsc;"
                + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=15;";
        // VERY BAD creating insert statements with embedded values as follows!
        String insertSql = "INSERT INTO department (dept_name, building, budget) VALUES "
                + "('Culinary', 'Gumbo', 450000);";
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                PreparedStatement prepsInsertProduct = connection.prepareStatement(insertSql,
                        Statement.RETURN_GENERATED_KEYS);) {
            prepsInsertProduct.execute();
            // Retrieve the generated key from the insert.
            resultSet = prepsInsertProduct.getGeneratedKeys();
            // Print the auto generated identity of the inserted row will be null in this
            // case.
            while (resultSet.next()) {
                System.out.println("Generated: " +
                        resultSet.getString(1));
            }
        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
