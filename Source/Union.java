package union;
import java.sql.*;

public class Union{

	private UnionPayBehavior upb;
	private int festiveCharge 	= 200;
	private int membershipCharge = 150;
	private int activityCharge = 50;

	public void takeFestiveCharge(){
		upb = new FestivePay();
		upb.pay();
	}

	public void takeActivityCharge(){
		upb = new ActivityPay();
		upb.pay();
	}

	public int getFestiveCharge(){
		return festiveCharge;
	}

	public int getActivityCharge(){
		return activityCharge;
	}

	public static void Addmember(int employeeId){
		try{
		// DB Connection
		Class.forName("com.mysql.jdbc.Driver");
	    Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employee","root","root");

	    // the mysql insert statement
	  	String query = " UPDATE master SET member=1 where id = ?";

		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setInt (1, employeeId);
		preparedStmt.execute();	
		con.close();
	}
	catch(Exception e){
		System.err.println("Got an exception!");
		      System.err.println(e.getMessage());
	}
	}

	public static void RemoveMember(int employeeId){
		try{
		// DB Connection
		Class.forName("com.mysql.jdbc.Driver");
	    Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employee","root","root");

	    // the mysql insert statement
	  	String query = " UPDATE master SET member=0 where id = ?";

		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setInt (1, employeeId);
		preparedStmt.execute();	
		con.close();
	}
	catch(Exception e){
		System.err.println("Got an exception!");
		      System.err.println(e.getMessage());
	}
	}

}