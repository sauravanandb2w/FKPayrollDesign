package employee;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.sql.*;

enum Mode{
	MailedPayCheck,PickByOwn,DirectBankTransfer;
}

class Employees{
	private static int identificationNo=0;
	protected int employeeId;
	private String employeeName;
	private int currentEarnedMoney;
	private Mode currentPaymentMode;
	Employees(String employeeName,Mode currentPaymentMode){
		this.employeeName = employeeName;
		this.currentPaymentMode = currentPaymentMode;
		identificationNo = identificationNo + 1;
		this.employeeId = identificationNo;
		this.currentEarnedMoney = 0;
	}

	public void assignMeWork()
	{
		String joiningDate;
		Calendar calender = Calendar.getInstance();
 		SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
        joiningDate = dateOnly.format(calender.getTime());

        try{
        	Class.forName("com.mysql.jdbc.Driver");
        	Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employee","root","root");


        	// the mysql insert statement
      		String query = " insert into master (id, date, name, earned,member,unionAmmount, mode)"
        		+ " values (?, ?, ?, ?, ?,?,?)";

		       // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = con.prepareStatement(query);
		      preparedStmt.setInt (1, employeeId);
		      preparedStmt.setString (2, joiningDate);
		      preparedStmt.setString (3, employeeName);
		      preparedStmt.setInt(4,currentEarnedMoney);
		      preparedStmt.setInt(5, 0);
		      preparedStmt.setInt(6, 0);
		      preparedStmt.setString(7, currentPaymentMode.name());


		      // execute the preparedstatement
		      preparedStmt.execute();	
		      con.close();
		   }
		   catch (Exception e)
		    {
		      System.err.println("Got an exception!");
		      System.err.println(e.getMessage());
		    }
       // System.out.println(employeeName+" "+currentPaymentMode+" "+employeeId+" "+currentEarnedMoney+" "+joiningDate);
	}
}

class PayMeWeekly extends Employees{
	private int rateAssigned;
	private int unitHours;
	PayMeWeekly(int rateAssigned,String ename,Mode m){
		super(ename,m);
		this.assignMeWork();
		this.rateAssigned = rateAssigned;
		this.unitHours = 0;
		System.out.println(employeeId+" "+rateAssigned+" "+unitHours);
	}
}

class PayMeMonthly extends Employees{
	private int rateAssigned;
	private int commission;
	PayMeMonthly(int rateAssigned,String ename,Mode m){
		super(ename,m);
		this.assignMeWork();
		this.rateAssigned = rateAssigned;
		this.commission = 0;
		System.out.println(employeeId+" "+rateAssigned+" "+commission);
	}
}

public class Employee{
	public static void main(String args[]){
		Employees e = new PayMeMonthly(3,"abc",Mode.MailedPayCheck);
		Employees f = new PayMeWeekly(4,"aaa",Mode.MailedPayCheck);
	}
}

