import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.*;
import javax.swing.*;

public class Payroll_GUI extends JFrame {

    private JFrame loginPage = new JFrame("PayHero");
    private String username = "Admin";
    private String password = "Admin123";
    private JTextField usernameText = new JTextField(20);
    private JPasswordField passwordText = new JPasswordField(20);
    private JButton loginBTN = new JButton("Login");
    HomePageGUI hpgui;
    
    //I moved the public static in this class because my computer couldn't run this program when this was in the other class.
    public static void main(String[] args) {
        new Payroll_GUI();
    }

    public Payroll_GUI()
    {
    	//This try and catch block is for the custom font.
    	try
    	{
    		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    		ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Dark_Future.ttf")));
    	}
    	catch(IOException|FontFormatException e)
    	{
    		e.printStackTrace();
    	}
    	
        JPanel titlePanel = new JPanel();
        JPanel bodyPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,10,10,10);
        loginPage.setLayout(new BorderLayout());
        loginPage.setMinimumSize(new Dimension(700,450));
        loginPage.setLocationByPlatform(true);
        loginPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel payHeroTitle = new JLabel("PayHero");
        payHeroTitle.setFont(new Font("Dark Future", Font.BOLD, 34));
        titlePanel.add(payHeroTitle);

        JLabel usernameLabel = new JLabel("Username: ");
        c.gridx = 0;
        c.gridy = 0;
        bodyPanel.add(usernameLabel, c);


        usernameText.setEditable(true);
        c.gridx = 1;
        bodyPanel.add(usernameText, c);

        JLabel passwordLabel = new JLabel("Password: ");
        c.gridx = 0;
        c.gridy = 1;
        bodyPanel.add(passwordLabel, c);

        passwordText.setEditable(true);
        c.gridx = 1;
        bodyPanel.add(passwordText, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        bodyPanel.add(loginBTN, c);

        ActionListener1 aListen = new ActionListener1();
        loginBTN.addActionListener(aListen);

        loginPage.add(titlePanel, BorderLayout.NORTH);
        loginPage.add(bodyPanel, BorderLayout.CENTER);

        loginPage.setVisible(true);
    }

    class ActionListener1 implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            if(usernameText.getText().equals(username) && passwordText.getText().equals(password))
            {
                loginPage.dispose();
                new HomePageGUI();
            }
        }
    }
}

class HomePageGUI extends JFrame{

    private JFrame homePage = new JFrame("PayHero");
    private JTextField sEIDText = new JTextField(15);
    private JButton searchBTN = new JButton("Search");
    private JButton newEmpBTN = new JButton("New Employee Record");
    private JButton calcSalBTN = new JButton("Payslip Generator");
    private JButton browseBTN = new JButton("Browse");
    PageFour p4;

    public HomePageGUI()
    {
        homePage.setLayout(new BorderLayout());
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,10,10,10);
        homePage.setMinimumSize(new Dimension(700,450));
        homePage.setLocationByPlatform(true);
        homePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homePage.pack();
        
        sEIDText.setText("ENTER EMPLOYEE ID");
        sEIDText.setEditable(true);
        c.gridx = 0;
        c.gridy = 0;
        centerPanel.add(sEIDText);

        c.gridx = 1;
        centerPanel.add(searchBTN);

        c.gridx = 0;
        c.gridy = 1;
        centerPanel.add(newEmpBTN, c);

        c.gridx = 2;
        centerPanel.add(browseBTN);

        c.gridx = 1;
        c.gridy = 1;
        centerPanel.add(calcSalBTN, c);

        ActionListener2 aListen2 = new ActionListener2();
        FocusListener1 aFocus1 = new FocusListener1();
        searchBTN.addActionListener(aListen2);
        newEmpBTN.addActionListener(aListen2);
        calcSalBTN.addActionListener(aListen2);
        browseBTN.addActionListener(aListen2);
        sEIDText.addFocusListener(aFocus1);

        homePage.add(centerPanel, BorderLayout.CENTER);
        homePage.setVisible(true);
        searchBTN.requestFocusInWindow();
    }

    class ActionListener2 implements ActionListener
    {
    	SQLConnection connect = new SQLConnection();
        public void actionPerformed(ActionEvent evt)
        {
            if(evt.getSource() == searchBTN)
            {
            	try
				{
					String query = "SELECT * FROM Employees "+
								   "WHERE ID=?";
					Connection c = connect.getConnection();
					PreparedStatement ps = c.prepareStatement(query);
					ps.setString(1, sEIDText.getText());
					ResultSet rs = ps.executeQuery();
					while(rs.next())
					{
						p4.dBID = rs.getString("ID");
						p4.dBFirstName = rs.getString("First_Name");
						p4.dBLastName = rs.getString("Last_Name");
						p4.dBRole = rs.getString("Role");
						p4.dBSalary = rs.getDouble("Salary");
						p4.dBStartDate = rs.getString("StartDate");
						p4.dBEndDate = rs.getString("EndDate");
						p4.dBDepartment = rs.getString("Department");
						p4.dBAddress = rs.getString("Address");
						p4.dBEmail = rs.getString("email");
					}
				}
				catch(SQLException e)
				{
					System.out.println(e.getMessage());
				}
				
				if(p4.dBID != null)
				{
					homePage.dispose();
					new PageFour();
				}
				else
				{
					sEIDText.setText("ID Not Found");
				}
            }

            if(evt.getSource() == newEmpBTN)
            {
                homePage.dispose();
                PageThree pt = new PageThree();
            }

            if(evt.getSource() == browseBTN)
            {
                homePage.dispose();
                BrowseDB br = new BrowseDB();
            }

            if(evt.getSource() == calcSalBTN)
            {
                homePage.dispose();
                payslip_generator pg = new payslip_generator();
            }

        }
    }
    
    class FocusListener1 implements FocusListener
    {
    	public void focusGained(FocusEvent evt)
    	{
    		sEIDText.setText("");
    	}
    	public void focusLost(FocusEvent evt)
    	{
    	}
    }
}

class SQLConnection
{
	protected Connection getConnection() throws SQLException
	{
		String dbUrl = "jdbc:sqlite:C:\\SQLiteStudio\\PayrollSystem";
		Connection connection = DriverManager.getConnection(dbUrl);
		return connection;
	}
}