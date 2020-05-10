package company;

public class Company{
	private CompanyPayBehavior cpb;
	private static int employeeId;
	private static int employeeAttributeValue;

	public Company(int employeeId,int employeeAttributeValue){
		this.employeeId = employeeId;
		this.employeeAttributeValue = employeeAttributeValue;
	}

	public static int getEmployeeId(){
		return employeeId;
	}

	public static int getEmployeeAttr(){
		return employeeAttributeValue;
	}

	public void payHimHourly(){
		cpb = new DailyPayment();
		cpb.pay();
	}

	public void payHimCommission(){
		cpb = new CommissionPay();
		cpb.pay();
	}
}