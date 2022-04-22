package payslip_generator;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;


class payroll_data {
	String id;
    String f_name;
    String l_name;
    String role;
    Double sal;
    String dep;
    String email;
    String address;
    
}

@SuppressWarnings("serial")
public class payslip_generator extends JFrame implements ActionListener {
	private JButton addBTN = new JButton("ADD");
	private JDateChooser Date1;
	private JDateChooser Date2;
	private JButton generateBTN = new JButton("Generate Payslip for all");
	private payroll_data[] payslipdata;
	private int Num_weeks;
	
	public static void main(String[] args) {
		new payslip_generator();		

	}
	public payslip_generator(){
	
		Container cp = getContentPane();
		JPanel titlePanel = new JPanel();
		JPanel bodyPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10,10,10,10);
		cp.setLayout(new BorderLayout());
		setMinimumSize(new Dimension(700,450));
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel payperiodinfo = new JLabel("Input Payperiod Range");
		JLabel payslipgenTitle = new JLabel("Payslip Generator");
		payslipgenTitle.setFont(new Font("Serif", Font.BOLD, 34));
		titlePanel.add(payslipgenTitle);
			c.fill = GridBagConstraints.HORIZONTAL;
		Date1 = new JDateChooser();
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		c.ipady = 20;
		bodyPanel.add(Date1, c);
		c.weightx = 0.3;
		c.gridx = 3;
		c.gridy = 0;
		c.ipady = 20;
		addBTN = new JButton("ADD");
		
		addBTN.addActionListener(new ActionListener(){
		            public void actionPerformed(ActionEvent e){
		            	Date pay_period1 = Date1.getDate();
		            	Date pay_period2 = Date2.getDate();
		            	Date1.setDate(null);
		            	Date2.setDate(null);
		            	int numweeks = calculatepayperiod(pay_period1, pay_period2);
		            	Num_weeks = numweeks;
		            	System.out.println(numweeks);
		            }});
    	
		bodyPanel.add(addBTN, c);
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 2;
		c.gridwidth = 2;
		generateBTN = new JButton("Generate Payslip for all");
		generateBTN.addActionListener(new ActionListener(){
		            public void actionPerformed(ActionEvent e){
		            	try {
							writepayslip(getemployeeinfo());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		            	
		            	
		            }});
		bodyPanel.add(generateBTN, c);
		Date2 = new JDateChooser();
		c.gridx = 1;
		c.gridy = 1;
		c.ipady = 20;
		bodyPanel.add(Date2,c);
		c.gridx = 3;
		c.gridy = 1;
		bodyPanel.add(payperiodinfo, c);
		cp.add(titlePanel, BorderLayout.NORTH);
		cp.add(bodyPanel, BorderLayout.CENTER);

		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	public payroll_data[] getemployeeinfo(){
		payslip_generator connect1=new payslip_generator();
        payslipdata = new payroll_data[100];
		try {
			Connection c1 = connect1.getConnection();
			System.out.println("Connection to SQLite has been established");
			Statement s1 = c1.createStatement();
			ResultSet rs = s1.executeQuery("Select * from Employees ");
			int i = 0;
			while (rs.next()) {
		         String id = rs.getString("ID");
		         String f_name = rs.getString("First_Name");
		         String l_name = rs.getString("Last_Name");
		         String role = rs.getString("Role");
		         Double sal  = rs.getDouble("Salary");
		         String dep = rs.getString("Department");
		         String add = rs.getString("Address");
		         String email = rs.getString("Email");
		         payroll_data payroll = new payroll_data();
		         payroll.id = id;
		         payroll.address = add;
		         payroll.f_name = f_name;
		         payroll.l_name = l_name;
		         payroll.role = role;
		         payroll.sal = sal;
		         payroll.dep = dep;
		         payroll.email = email;
		         payslipdata[i] = payroll;
		         System.out.println( "ID = " + id);
		         System.out.println( "First Name = " + f_name);
		         System.out.println( "Last Name = " + l_name);
		         System.out.println( "Role = " + role);
		         System.out.println( "Salary = " + sal);
		         System.out.println( "Department = " + dep);
		         System.out.println( "Email = " + email);
		         System.out.println(i);
		         i++;
		      }
			rs.close();
			s1.close();
			c1.commit();
			c1.close();
		
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return payslipdata;

	}
	private Connection getConnection() throws SQLException {
		String dbUrl = "jdbc:sqlite:C:\\SQLiteStudio\\PayrollSystem";
		Connection connection=DriverManager.getConnection(dbUrl);
		return connection;
	}
	public int calculatepayperiod(Date date1, Date date2) {
		long date1InMs = date1.getTime();
		long date2InMs = date2.getTime();
		long timeDiff = 0;
		if(date1InMs > date2InMs) {
			timeDiff = date1InMs - date2InMs;
		} else {
			timeDiff = date2InMs - date1InMs;
		}
		int daysDiff = (int) (timeDiff / (1000 * 60 * 60* 24));
		int weeksbetween = daysDiff/7;
		return weeksbetween;
	}
	void writepayslip(payroll_data[] param) throws IOException {
		for(int i = 0; i < param.length; i++) {
		if (param[i] != null) {
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		System.out.println("Current relative path is: " + s);
		String dirString=s+"/data";
		Path dirPath=Paths.get(dirString);
		if (Files.notExists(dirPath)){
		Files.createDirectories(dirPath);
		}
		
		String fileString= param[i].l_name + param[i].f_name + "Payslip.txt";
		Path filePath=Paths.get(dirString,fileString);
		if (Files.notExists(filePath)){
			Files.createFile(filePath);
		}
		if (Files.exists(dirPath)&&Files.isDirectory(dirPath)){
			System.out.println("Directory: "
					+dirPath.toAbsolutePath());
			System.out.println("Files: ");
			DirectoryStream<Path> dirStream=
					Files.newDirectoryStream(dirPath);
			for (Path p:dirStream){
				System.out.println("    "+p.getFileName());
			}
		}
		Path payslipPath=Paths.get(dirString, fileString);
		File payslipFile=payslipPath.toFile();
		System.out.println(payslipFile);
		PrintWriter accwriter=new PrintWriter(
				new BufferedWriter(
				new FileWriter(payslipFile)));
		accwriter.println("");
		accwriter.println("Company: Disney Gold Mining and Co");
		accwriter.println("12345 Mainstreet, Chicago IL");
		accwriter.println("");
		accwriter.println("Payslip for: " + param[i].f_name + " " + param[i].l_name);
		accwriter.println("Employee ID: " + param[i].id);
		accwriter.println("Role: " + param[i].role);
		accwriter.println("Address: " + param[i].address);
		accwriter.println("");
		accwriter.println("Pay Type: BiWeekly");
		accwriter.println("Salary to be paid: " + param[i].sal * (Num_weeks/2) + "Gold Nuggets");
		accwriter.println("");
	accwriter.close();
		}
		else {
			break;
		}
	}
	}
	}
