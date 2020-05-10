package company;

import java.sql.*;

public interface CompanyPayBehavior{
	void pay();
}

class DailyPayment implements CompanyPayBehavior{

	public void pay(){
		try{

		int employeeId = Company.getEmployeeId();
		int hoursOfWork = Company.getEmployeeAttr();	
		Class.forName("com.mysql.jdbc.Driver");
	    Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employee","root","root");
	  	
	  	int overTime = (hoursOfWork - 8>0)?hoursOfWork-8:0;;
	  	int unitHours = (hoursOfWork>8)?8:hoursOfWork;

	  	String query = " UPDATE weekly_table SET hourscount = hourscount + ? where id=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setInt (1, unitHours);
		preparedStmt.setInt (2,employeeId);
		preparedStmt.execute();	
		

		//if overtime value is not equal to 0
		query = " UPDATE weekly_table SET overtime = overtime + ? where id=?";
		preparedStmt = con.prepareStatement(query);
		preparedStmt.setInt (1, overTime);
		preparedStmt.setInt (2,employeeId);
		preparedStmt.execute();

		con.close();

		}
		catch(Exception e){
			System.err.println("Got an exception!");
		      System.err.println(e.getMessage());
		}

	}
}

class CommissionPay implements CompanyPayBehavior{
	public void pay(){
		try{

		int employeeId = Company.getEmployeeId();
		int commissionAmount = Company.getEmployeeAttr();	
		System.out.println(employeeId + " " + commissionAmount);
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println(employeeId + " " + commissionAmount);
	    Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employee","root","root");
	  	

	  	String query = " UPDATE monthly_table SET commission = commission + ? where id=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setInt (1, commissionAmount);
		preparedStmt.setInt (2,employeeId);
		preparedStmt.execute();	
		

		con.close();

		}
		catch(Exception e){
			System.err.println("Got an exception!");
		      System.err.println(e.getMessage());
		}	
	}
}