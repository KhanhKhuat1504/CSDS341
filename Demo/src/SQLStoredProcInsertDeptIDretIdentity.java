//Java code to run the stored procedure and output the
//generated identity for the id column in the newly created table deptID
//For more information read
// https://learn.microsoft.com/en-us/sql/connect/jdbc/using-a-stored-
// procedure-with-output-parameters?view=sql-server-ver16

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class SQLStoredProcInsertDeptIDretIdentity {
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
        Scanner myObj = new Scanner(System.in);
        String inpDeptName, inpBuilding;
        float inpBudget;
        System.out.println("Enter dept_name then enter. ");
        inpDeptName = myObj.nextLine();
        System.out.println("Enter building then enter.");
        inpBuilding = myObj.nextLine();
        System.out.println("Enter budget as numeric (12,2) then enter. ");
        inpBudget = myObj.nextFloat();
        myObj.close();
        System.out.println("dept_name: " + inpDeptName + " building: "
                + inpBuilding + " budget: " + inpBudget);
        // 4 ? because 4 parameters to stored proc needed. Index for paramters start at
        // 1.
        String callStoredProc = "{call dbo.insertDeptID(?,?,?,?)}";
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                CallableStatement prepsStoredProc = connection.prepareCall(callStoredProc);) {
            connection.setAutoCommit(false);
            // 4 parameters to stored proc start with a parameter index of 1
            prepsStoredProc.setString(1, inpDeptName);
            prepsStoredProc.setString(2, inpBuilding);
            prepsStoredProc.setFloat(3, inpBudget);
            // the 4th parameter is an output parameter
            prepsStoredProc.registerOutParameter(4,
                    java.sql.Types.INTEGER);
            prepsStoredProc.execute();
            System.out.println("Generated Identity: " +
                    prepsStoredProc.getInt(4));
            connection.commit(); // comment this line to show the values are not "saved" i.e. committed in db
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
