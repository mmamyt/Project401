
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
	private JFrame frame;
	private JButton addBTN = new JButton("ADD");
	private JButton backBTN = new JButton("Back");
	private JDateChooser Date1;
	private JDateChooser Date2;
	private JButton generateBTN = new JButton("Generate Payslip for all");
	private payroll_data[] payslipdata;
	private int Num_weeks;
	HomePageGUI hpgui;
	
	public static void main(String[] args) {
		new payslip_generator();

	}
	public payslip_generator(){
		frame = new JFrame();
		JPanel titlePanel = new JPanel();
		JPanel bodyPanel = new JPanel(new GridBagLayout());

		try
		{
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Dark_Future.ttf")));
		}
		catch(IOException |FontFormatException e)
		{
			e.printStackTrace();
		}

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10,10,10,10);
		frame.setLayout(new BorderLayout());
		frame.setMinimumSize(new Dimension(700,450));
		frame.setLocationByPlatform(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel payperiodfrom = new JLabel("From:");
		JLabel payperiodto = new JLabel("To:");
		JLabel payslipgenTitle = new JLabel("PayHero");
		JLabel payslipgenselect = new JLabel("Select Pay Period:");
		payslipgenTitle.setFont(new Font("Dark Future", Font.BOLD, 34));
		titlePanel.add(payslipgenTitle);
			c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		bodyPanel.add(payslipgenselect,c);
		
			c.fill = GridBagConstraints.HORIZONTAL;
		Date1 = new JDateChooser();
		c.gridx = 1;
		c.gridy = 2;
		c.ipady = 20;
		c.weightx = 0.4;
		bodyPanel.add(Date1, c);
		
		c.gridx = 2;
		c.gridy = 3;
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
		c.gridy = 4;
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
		
		backBTN.setBounds(10,1,100,25);
		backBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				frame.dispose();
				new HomePageGUI();
			}
		});
		frame.add(backBTN);
		
		Date2 = new JDateChooser();
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 2;
		c.ipady = 20;
		bodyPanel.add(Date2,c);
		
		c.gridx = 1;
		c.gridy = 1;
		bodyPanel.add(payperiodfrom, c);
		
		c.gridx = 2;
		c.gridy = 1;
		bodyPanel.add(payperiodto, c);
		frame.add(titlePanel, BorderLayout.NORTH);
		frame.add(bodyPanel, BorderLayout.CENTER);

		frame.setVisible(true);
		frame.setResizable(false);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	public payroll_data[] getemployeeinfo(){
		payslip_generator connect1=new payslip_generator();
		frame.dispose();
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
		accwriter.println("Salary to be paid: " + param[i].sal * (Num_weeks/2) + " Gold Nuggets");
		accwriter.println("");
	accwriter.close();
		}
		else {
			break;
		}
	}
	}
	}
