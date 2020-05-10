import java.util.Scanner;
import company.*;
import union.*;
import employee.*;
import java.sql.*;

class GoodMorning{
	GoodMorning(){
		MainPayroll.daysCount++;
	}
}

class UpdateWorkBook{
	void updateHourly(){
		Scanner sc = new Scanner(System.in);
		while(true){
			System.out.println("Enter new Employee(id and number of hours he worked today) [0 if no more Employees]");
			int id = sc.nextInt();
			if(id==0)
				break;
			int hours = sc.nextInt();
			new Company(id,hours).payHimHourly();
		}
		sc.close();
	} 
	void updateCommission(){
		Scanner sc = new Scanner(System.in);
		while(true){
			System.out.println("Enter new Employee(id and Commission he earned today for Sales) [0 if no more Employees]");
			int id = sc.nextInt();
			if(id==0)
				break;
			int commission = sc.nextInt();
			new Company(id,commission).payHimCommission();
		}
		sc.close();
	}
}


class UnionRelated{
	void activityCharge(){
		new Union().takeActivityCharge(); 
	}
	void festiveCharge(){
		new Union().takeFestiveCharge();
	}
	void add(){
		Scanner sc = new Scanner(System.in);
		while(true){
			System.out.println("Enter new Employee who wants to be a member of union[0 if no more Employees]");
			int id = sc.nextInt();
			if(id==0)
				break;
			Union.AddMember(id);
		}
		sc.close();
	}
	void remove(){
		Scanner sc = new Scanner(System.in);
		while(true){
			System.out.println("Enter new Employee who wants no more to be a member of union[0 if no more Employees]");
			int id = sc.nextInt();
			if(id==0)
				break;
			Union.RemoveMember(id);
		}
		sc.close();
	}
}

class ShowPayroll{
	void printPayroll(){

		//weekly paid guys
		if(MainPayroll.daysCount%7==5){
			try{
				Class.forName("com.mysql.jdbc.Driver");
	    		Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employee","root","root");
				String sql = "select * from weekly_table";
				PreparedStatement statement = con.prepareStatement(sql);
				ResultSet result = statement.executeQuery();

				while(result.next()){
					int id = result.getInt("id");
					int rate = result.getInt("rate");
					int overtime = result.getInt("overtime");
					int hourscount = result.getInt("hourscount");
					int answer = rate*hourscount + (rate*3*overtime)/2;
					int unionAmmount=0;
					sql = "select unionAmmount from master where id = ? and member = 1";
					statement = con.prepareStatement(sql);
					statement.setInt(1,id);
					ResultSet res = statement.executeQuery();
					if(res.next()){
						unionAmmount = res.getInt("unionAmmount");
						answer = answer - unionAmmount;
					}
					sql = " UPDATE weekly_table SET hourscount = 0 and overtime = 0 where id=?";
					statement = con.prepareStatement(sql);
					statement.setInt(1,id);
					statement.execute();

					sql = " UPDATE weekly_table SET unionAmmount = 0 and earned = earned + ? where id=?";
					statement = con.prepareStatement(sql);
					statement.setInt(1,answer);
					statement.setInt(2,id);
					statement.execute();

					System.out.println("idNumber "+id+" Amount to be Paid "+ answer + " Amount deducted From union" + unionAmmount);
					con.close();
				}
			}
			catch(Exception e)
			{
				System.out.println("Error in payroll for weekly members...");
			}
		}

		// monthly paid guys
		if(MainPayroll.daysCount%7==5){
			try{
				Class.forName("com.mysql.jdbc.Driver");
	    		Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employee","root","root");
				String sql = "select id,commission from monthly_table";
				PreparedStatement statement = con.prepareStatement(sql);
				ResultSet result = statement.executeQuery();

				while(result.next()){
					int id = result.getInt("id");
					int commission = result.getInt("commission");

					sql = " UPDATE monthly_table SET commission = 0 and earned = earned + ? where id=?";
					statement = con.prepareStatement(sql);
					statement.setInt(1,commission);
					statement.setInt(2,id);
					statement.execute();

					System.out.println("idNumber "+id+" Commission to be paid" + commission);
					con.close();
				}
			}
			catch(Exception e)
			{
				System.out.println("Error in payroll for weekly members...");
			}
		}
		if(MainPayroll.daysCount%31==30){
			try{
				Class.forName("com.mysql.jdbc.Driver");
	    		Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employee","root","root");
				String sql = "select * from monthly_table";
				PreparedStatement statement = con.prepareStatement(sql);
				ResultSet result = statement.executeQuery();

				while(result.next()){
					int id = result.getInt("id");
					int rate = result.getInt("rate");
					int answer = rate;
					int unionAmmount=0;
					sql = "select unionAmmount from master where id = ? and member = 1";
					statement = con.prepareStatement(sql);
					statement.setInt(1,id);
					ResultSet res = statement.executeQuery();
					if(res.next()){
						unionAmmount = res.getInt("unionAmmount");
						answer = answer - unionAmmount;
					}

					sql = " UPDATE weekly_table SET unionAmmount = 0 and earned = earned + ? where id=?";
					statement = con.prepareStatement(sql);
					statement.setInt(1,answer);
					statement.setInt(2,id);
					statement.execute();

					System.out.println("idNumber "+id+" Amount to be Paid "+ answer + " Amount deducted From union" + unionAmmount);
					con.close();
				}
			}
			catch(Exception e)
			{
				System.out.println("Error in payroll for weekly members...");
			}
		}
	}


		void totalMoney(){
			try{
				Class.forName("com.mysql.jdbc.Driver");
	    		Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employee","root","root");
				String sql = "select name,earned from master";
				PreparedStatement statement = con.prepareStatement(sql);
				ResultSet result = statement.executeQuery();

				while(result.next()){
					String name = result.getString("name");
					int earned = result.getInt("earned");

					System.out.println("Name "+name+" Money Earned till Now" + earned);

				}
				con.close();
			}
			catch(Exception e)
			{
				System.out.println("Error in payroll for weekly members...");
			}
		}
	
	}






public class MainPayroll{

	public static int daysCount=-1;

	public static void main(String args[]){
		System.out.println(" Payroll System Menu :: Functionalities :- \n  1)Add a New Employee.\n  2)Unregister a Employee.\n  3)Run Today's payment.");
		System.out.println("  4)Change Employee Details. \n  5)Add a New Employee to union.\n  6)Festive Charge from union.\n  7)FunActivity Charge From union.");
		System.out.println("  8)Provide commission to an employee.\n  9)Add the number of Hours for(daily waged)Employee.  \n  10)Remove any Employee From Union.  \n  11)Is it a NewDay(GoodMorning...)");
		System.out.println("  12)Name Wise Money exprnditure by company for Employee ");

		Scanner input = new Scanner(System.in);
		int option = input.nextInt();
		System.out.println(option);
		switch(option)
		{
			case 1:new Employee(0);
					break;
			case 2:new Employee(1);
					break;
			case 3:new ShowPayroll().printPayroll();
					break;
			case 4:new Employee(1);
					new Employee(0);
					break;		
			case 5:new UnionRelated().add();
					break;
			case 6:new UnionRelated().festiveCharge();
					break;
			case 7:new UnionRelated().activityCharge();
					break;
			case 8:new UpdateWorkBook().updateCommission();
					break;
			case 9:new UpdateWorkBook().updateHourly();
					break;
			case 10:new UnionRelated().remove();
					break;		
			case 11:new GoodMorning();
					break;
			case 12:new ShowPayroll().totalMoney();
					break;	
			default:System.out.println("Please Choose correct option(1-11)...");			
		}
	}
}