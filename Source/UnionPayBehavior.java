	package union;
	
	import java.sql.*;

	public interface UnionPayBehavior{
	// function to be implemented
	void pay();

	}

	//Concrete function 1
	class FestivePay implements UnionPayBehavior{

	// implementing interface function 
	@Override
	public void pay(){
		try{
		int festiveCharge = new Union().getFestiveCharge();
		System.out.print(festiveCharge);
		// DB Connection
		Class.forName("com.mysql.jdbc.Driver");

		System.out.print(festiveCharge);

	    Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employee","root","root");

	    // the mysql insert statement
	  	String query = " UPDATE master SET unionAmmount = unionAmmount + ? where member=1";

		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setInt (1, festiveCharge);
		preparedStmt.execute();	
		con.close();
		}
		catch(Exception e){
			System.err.println("Got an exception!");
		      System.err.println(e.getMessage());
		}
	}

	}

	// Concrete function 2
	class  ActivityPay implements UnionPayBehavior{

	// implementing interface function
	public void pay(){
		try{
		int activityCharge = new Union().getActivityCharge();

		// DB Connection
		Class.forName("com.mysql.jdbc.Driver");
	    Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employee","root","root");

	    // the mysql insert statement
	  	String query = " UPDATE master SET earned = unionAmmount + ? where member=1";

		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setInt (1, activityCharge);
		preparedStmt.execute();	
	}
	catch(Exception e){
		System.err.println("Got an exception!");
		      System.err.println(e.getMessage());
	}
  }
}