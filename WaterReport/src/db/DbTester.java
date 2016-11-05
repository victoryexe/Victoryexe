import java.sql.*;
import javax.sql.*;
import java.util.*;

public class  DbTester {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            //String dbName = "Water_Tester";
            String url = "jdbc:mysql://104.131.110.247:3306/";
            conn = DriverManager.getConnection (url, "Victoryexe", "PwD");
            System.out.println("Database connection established");
            Statement stmt = null;
            ResultSet rs = null;
            //try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("USE Water_Tester");
                rs = stmt.executeQuery("DESCRIBE AccountTester");   
                System.out.println(rs.first());
                while(!rs.isAfterLast()) {
                    System.out.println(rs.getString(2));
                    rs.next();

                }
            } catch (Exception e) {
                System.out.println("Something Wrong at Statement");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}