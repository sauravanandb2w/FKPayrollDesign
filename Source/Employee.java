	package employee;

	import java.util.Calendar;
	import java.text.SimpleDateFormat;
	import java.sql.*;
	import java.util.Scanner;

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
	   	    System.out.println("INFO UPDATED"+employeeName+" "+currentPaymentMode+" "+employeeId+" "+currentEarnedMoney+" "+joiningDate);
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

		 try{
	    	Class.forName("com.mysql.jdbc.Driver");
	    	Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employee","root","root");

			// the mysql insert statement
	  		String query = " insert into weekly_table (id, rate, hourscount)" 
	  			+ " values (?, ?, ?)";

		    // create the mysql insert preparedstatement
		    PreparedStatement preparedStmt = con.prepareStatement(query);
		    preparedStmt.setInt (1, employeeId);
		    preparedStmt.setInt (2, rateAssigned);
		    preparedStmt.setInt (3, unitHours);

			// execute the preparedstatement
		    preparedStmt.execute();	
		    con.close();
		   }
		   catch (Exception e)
		   {
		      System.err.println("Got an exception!");
		      System.err.println(e.getMessage());
		   }

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

		try{
	    	Class.forName("com.mysql.jdbc.Driver");
	    	Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employee","root","root");

			// the mysql insert statement
	  		String query = " insert into monthly_table (id, rate, commission)" 
	  			+ " values (?, ?, ?)";

		    // create the mysql insert preparedstatement
		    PreparedStatement preparedStmt = con.prepareStatement(query);
		    preparedStmt.setInt (1, employeeId);
		    preparedStmt.setInt (2, rateAssigned);
		    preparedStmt.setInt (3, commission);

			// execute the preparedstatement
		    preparedStmt.execute();	
		    con.close();
		   }
		   catch (Exception e)
		   {
		      System.err.println("Got an exception!");
		      System.err.println(e.getMessage());
		   }
	}
	}

	final class HandleEmployee{
	public static void add(){
		Scanner input = new Scanner(System.in);
		
		System.out.println("Do you want to join on weekly pay or monthly pay(w for weekly|m for monthly)");
		char choice = input.next().charAt(0);
		

			String name;
			int workex;
			Mode payment;
			input.nextLine();  //takes the dummy new line
			System.out.print("Enter your name : ");
			name = input.nextLine();
			

			System.out.println("Enter your payment accepting mode : ");
			System.out.println("for MailedPayCheck enter 0");
			System.out.println("for PickByOwn enter 1");
			System.out.println("for DirectBankTransfer enter 2");
			int option = input.nextInt();

			switch(option){
				case 0:payment = Mode.MailedPayCheck;
					   break;
				case 1:payment = Mode.PickByOwn;
					   break;
			    case 2:payment = Mode.DirectBankTransfer;
			    	   break;
			    default:payment = Mode.PickByOwn;	   	   
			}

			System.out.println("Enter your working experience(in years) : ");
			if(choice=='w'||choice=='W'){
				int rate;
				
				workex = input.nextInt();
				if(workex<5)
					rate = 3;
				else if(workex<10)
					rate = 5;
				else 
					rate = 7;

				Employees employee = new PayMeWeekly(rate,name,payment);				
			}
			else{
				int monthlySalary;
				workex = input.nextInt();
				if(workex<5)
					monthlySalary = 30000;
				else if(workex<10)
					monthlySalary = 50000;
				else 
					monthlySalary = 70000;

				Employees employee = new PayMeMonthly(monthlySalary,name,payment);	
			}
			input.close();	
		}

		public static void delete(){
			System.out.println("You are leaving the job then, Enter your idNumber");
			int id;
			Scanner sc = new Scanner(System.in);
			id = sc.nextInt();

			try{
	    	Class.forName("com.mysql.jdbc.Driver");
	    	Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employee","root","root");

			// the mysql insert statement
	  		String query = "delete from master where id=?";

		    // create the mysql insert preparedstatement
		    PreparedStatement preparedStmt = con.prepareStatement(query);
		    preparedStmt.setInt (1, id);

			// execute the preparedstatement
		    preparedStmt.execute();	



		    query = "select * from weekly_table where id=?";
		    preparedStmt = con.prepareStatement(query);
		    preparedStmt.setInt (1, id);
		    ResultSet rs = preparedStmt.executeQuery();

		    if(rs.next()==true){
		    	query = "delete from weekly_table where id=?";
		    }	
		    else{
		    	query = "delete from monthly_table where id=?";
		    }

		    preparedStmt = con.prepareStatement(query);
		    preparedStmt.setInt (1, id);
		    preparedStmt.execute();
		    con.close();
		   }
		   catch (Exception e)
		   {
		      System.err.println("Got an exception!");
		      System.err.println(e.getMessage());
		   }
		}
	}


	public class Employee{
		private static int option = -1;
		public Employee(int option){
			this.option = option;
			System.out.println("constructor called...");
			if(option==0){
				HandleEmployee.add();
				System.out.println("You are added...");
			}
			else{
				HandleEmployee.delete();
				System.out.println("You are unregistered...");
			}
		}
		public static void main(String args[]){
			
		}
	}



