
// This code file makes a SQL Connection and runs a SQL Query returning the results

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLExecQuery {
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
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT top 100 ID, Name, Dept_Name from Instructor";
            resultSet = statement.executeQuery(selectSql);
            // Print results from select statement
            while (resultSet.next()) {
                System.out.println(resultSet.getString(2) + " " +
                        resultSet.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
