import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
class DatabaseConnection{
	public static void main(String args[]){
		try{
			System.out.println("setting up...");
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("setting up...");


			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/?","root","root");
			Statement s=con.createStatement(); 
			int Result=s.executeUpdate("CREATE DATABASE Employee");
			System.out.println("Connection established with mysql server at port 3306...");
			//s.executeUpdate("use Employee");
			//s.executeUpdate("create table t1(id int)");
			//Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Employee","root","root");
			System.out.println("Employee Database Created...");
			//Statement st = con.createStatement();
			//ResultSet rs = st.executeQuery("select * from students");
			System.out.println("database connected...");
			//rs.first();
			//System.out.println("id is"+rs.getInt(1));

		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}