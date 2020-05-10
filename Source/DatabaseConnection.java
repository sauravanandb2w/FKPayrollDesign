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
			
			s.executeUpdate("use Employee");
			s.executeUpdate("CREATE TABLE master (`id` INT NOT NULL,`date` VARCHAR(45) NOT NULL,`name` VARCHAR(45) NOT NULL,`earned` INT NOT NULL,`member` TINYINT NOT NULL,`unionAmmount` INT NULL,`mode` VARCHAR(45) NOT NULL,PRIMARY KEY (`id`))");
			//Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Employee","root","root");
			s.executeUpdate("CREATE TABLE weekly_table(`id` INT NOT NULL,`rate` INT NOT NULL,`overtime` INT NOT NULL,`hourscount` INT NOT NULL,PRIMARY KEY (`id`));");
			s.executeUpdate("CREATE TABLE monthly_table(`id` INT NOT NULL,`rate` INT NOT NULL,`commission` INT NOT NULL,PRIMARY KEY (`id`));");
			
			System.out.println("Employee Database Created...");
			//Statement st = con.createStatement();
			//ResultSet rs = st.executeQuery("select * from students");
			System.out.println("database connected...");
			con.close();
			//rs.first();
			//System.out.println("id is"+rs.getInt(1));

		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}