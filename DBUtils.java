import java.sql.*;
public class DBUtils 
{
    static Connection con;
    static Statement stmt;

    static
    {
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	
            String url = "jdbc:mysql://localhost:3306/student_management";
            String user ="root";
            String password = "root";
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
        } 
        catch (SQLException e) 
        {
            System.out.println("Connection Failed: " + e.getMessage());
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }
    public static void executeQuery(String query)
    {
        try {
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static ResultSet executeQueryGetResult(String query)
    {
        ResultSet rs = null;
        
        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }        
        return rs;
    }
}