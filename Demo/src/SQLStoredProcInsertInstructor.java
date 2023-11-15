
// Demo for invoking a stored procedure within code
// Assumption: The university database has a stored proc named
// insertInstructor and dbuser
// has execute permission on the stored proc. See the Canvas presentation
// "MxLx-SQLPgmStoredProc.pdf"
// for information on how to do this.
/* The following is the code to run in Microsoft's SSMS Query Window to
create the stored proc.
* but again, please see the above document on other steps needed.
create or alter procedure insertInstructor
@ID as varchar(5),
@name as varchar(20),
@dept_name as varchar(20),
@salary as numeric(8,2)
as
insert into instructor (id, name, dept_name, salary)
values (@ID, @name, @dept_name, @salary);
//To execute in SSMS in the SQL Query Editor
exec insertInstructor @id = '10001',
@name = 'Sally',
@dept_name = 'Physics',
@salary=100000;
// The following is necessary to give permissions to dbuser to
// execute the stored proc. Run the following three lines in
Microsoft's SSMS SQL Query Window
USE university;
GRANT EXECUTE ON OBJECT::insertInstructor
TO dbuser;
*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.sql.ResultSet; // need for select queries to get result set
//import java.sql.Statement; // if writing a sql statement directly
import java.util.Scanner;

//import java.math.BigDecimal; // need for SQL numeric datatypes
public class SQLStoredProcInsertInstructor {
    // Connect to your database.
    // Replace server name, username, and password with your credentials
    public static void main(String[] args) {
        String connectionUrl = "jdbc:sqlserver://cxp-sql-02\\DRF68;"
                + "database=university;"
                + "user=dbuser;"
                + "password=csds341143sdsc;"
                + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=15;";
        Scanner myObj = new Scanner(System.in);
        String inpID, inpName, inpDept;
        // float inpSalary;
        double inpSalary;
        // Get user input
        System.out.println("Enter the ID of instructor (5 digits) then enter. ");
        inpID = myObj.nextLine();
        System.out.println("Enter the name of instructor then enter. ");
        inpName = myObj.nextLine();
        System.out.println("Enter department of the instructor then enter.");
        inpDept = myObj.nextLine();
        System.out.println("Enter salary > 29000.00 as numeric (8,2) then enter.");
        inpSalary = myObj.nextDouble(); // myObj.nextFloat();
        myObj.close();
        System.out.println("instructor ID: " + inpID + "instructor name: "
                + inpName + " dept: "
                + inpDept + " salary: " + inpSalary);
        // 4 ? because 4 parameters to stored proc needed. Index for paramters start at
        // 1.
        String callStoredProc = "{call dbo.insertInstructor(?,?,?,?)}";
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                PreparedStatement prepsInsertInstructor = connection.prepareStatement(callStoredProc);) {
            // 3 parameters to stored proc start with a parameter index of 1
            prepsInsertInstructor.setString(1, inpID);
            prepsInsertInstructor.setString(2, inpName);
            prepsInsertInstructor.setString(3, inpDept);
            prepsInsertInstructor.setDouble(4, inpSalary);
            prepsInsertInstructor.execute();
        }
        // Handle any errors that may have occurred.clear
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
