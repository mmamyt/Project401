import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import com.toedter.calendar.JDateChooser;

public class PageThree extends javax.swing.JFrame {

    public JFrame frame;
    public JPanel panel;
    public JLabel labelID, labelFN, labelLN, labelRole, labelSDate, labelSalary, labelDepartment, labelAddress, labelEmail, msgUpdated, payHeroTitle;
    public JTextField ID, firstName, lastName, role, startDate, salary, department, Address, email, mandatory;
    public javax.swing.JButton buttonAdd, buttonBack;
    private JDateChooser Date1;
    HomePageGUI hpgui;

    Connection connection = null;

    public PageThree() {

        frame = new JFrame();
        panel = new JPanel();
        frame.setSize(700, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);
        frame.setLocationByPlatform(true);

        //title
        payHeroTitle = new JLabel("PayHero");
        payHeroTitle.setFont(new Font("Dark Future", Font.BOLD, 34));
        payHeroTitle.setBounds(230,30, 200, 40);
        panel.add(payHeroTitle, BorderLayout.NORTH);

        try
        {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Dark_Future.ttf")));
        }
        catch(IOException |FontFormatException e)
        {
            e.printStackTrace();
        }

        //ID
        labelID = new JLabel("ID:");
        labelID.setBounds(20, 100, 100, 25);
        panel.add(labelID);
        ID = new JTextField();
        ID.setBounds(100, 100, 150, 25);
        panel.add(ID);

        //First Name
        labelFN = new JLabel("First Name:");
        labelFN.setBounds(20, 130, 100, 25);
        panel.add(labelFN);
        firstName = new JTextField();
        firstName.setBounds(100, 130, 150, 25);
        panel.add(firstName);

        //Last Name
        labelLN = new JLabel("Last Name:");
        labelLN.setBounds(20, 160, 100, 25);
        panel.add(labelLN);
        lastName = new JTextField();
        lastName.setBounds(100, 160, 150, 25);
        panel.add(lastName);

        //Address
        labelAddress = new JLabel("Address:");
        labelAddress.setBounds(20, 190, 100, 25);
        panel.add(labelAddress);
        Address = new JTextField();
        Address.setBounds(100, 190, 150, 25);
        panel.add(Address);

        //Start date
        labelSDate = new JLabel("Start Date:");
        labelSDate.setBounds(20, 220, 100, 25);
        panel.add(labelSDate);
        startDate = new JTextField();
        startDate.setBounds(100, 220, 150, 25);
        panel.add(startDate);

        //Role
        labelRole = new JLabel("Role:");
        labelRole.setBounds(300, 130, 100, 25);
        panel.add(labelRole);
        role = new JTextField();
        role.setBounds(380, 130, 150, 25);
        panel.add(role);

        //Department
        labelDepartment = new JLabel("Department:");
        labelDepartment.setBounds(300, 160, 100, 25);
        panel.add(labelDepartment);
        department = new JTextField();
        department.setBounds(380, 160, 150, 25);
        panel.add(department);

        //Salary
        labelSalary = new JLabel("Salary:");
        labelSalary.setBounds(300, 190, 100, 25);
        panel.add(labelSalary);
        salary = new JTextField();
        salary.setBounds(380, 190, 150, 25);
        panel.add(salary);

        //email
        labelEmail = new JLabel("email:");
        labelEmail.setBounds(300, 220, 100, 25);
        panel.add(labelEmail);
        email = new JTextField();
        email.setBounds(380, 220, 150, 25);
        panel.add(email);

        //success message
        msgUpdated = new JLabel(" ");
        msgUpdated.setBounds(380,310,200,25);
        panel.add(msgUpdated);

        frame.setVisible(true);
        frame.setResizable(false);

        //Add New Record Button
        buttonAdd = new JButton("Add New Record");
        buttonAdd.setBounds(380, 280, 150, 25);
        panel.add(buttonAdd);
        
        //Back Button
        buttonBack = new JButton("Back");
        buttonBack.setBounds(10,1,100,25);
        panel.add(buttonBack);
        
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!ID.getText().isEmpty() && !firstName.getText().isEmpty() && !lastName.getText().isEmpty() && !role.getText().isEmpty() && !startDate.getText().isEmpty() &&
                        !salary.getText().isEmpty() && !department.getText().isEmpty() && !Address.getText().isEmpty() && !email.getText().isEmpty()) {
                    try {
                        String query = "INSERT INTO Employees (ID, First_Name, Last_Name, Role, Salary, StartDate, EndDate, Department, Address, email) VALUES ('" +
                                ID.getText() + "','" + firstName.getText() + "','" + lastName.getText() + "','" + role.getText() + "'," + salary.getText()
                                + ", '" + startDate.getText() + "', 'null', '" + department.getText() + "','" + Address.getText() + "','" + email.getText() + "')";
                        Connection c3 = getConnection();
                        Statement s3 = c3.createStatement();
                        int rowCount = s3.executeUpdate(query);
                        //setting cells to null
                        ID.setText("");
                        firstName.setText("");
                        lastName.setText("");
                        role.setText("");
                        startDate.setText("");
                        salary.setText("");
                        department.setText("");
                        Address.setText("");
                        email.setText("");
                        msgUpdated.setText("New Employee details were added");
                    } catch (SQLException e) {
                        msgUpdated.setText("Please fill all required fields");
                    }
                }else if(ID.getText().isEmpty() || firstName.getText().isEmpty() || lastName.getText().isEmpty() || role.getText().isEmpty() || startDate.getText().isEmpty() ||
                        salary.getText().isEmpty() || department.getText().isEmpty() || Address.getText().isEmpty() || email.getText().isEmpty()) {
                    if(ID.getText().isEmpty()) {/*ID.setText("ID is mandatory\n");*/ ID.setBackground(Color.pink);}
                    if(firstName.getText().isEmpty()) {/*firstName.setText("First Name is mandatory\n");*/ firstName.setBackground(Color.pink);}
                    if(lastName.getText().isEmpty()) {/*lastName.setText("Last Name is mandatory\n"); */lastName.setBackground(Color.pink);}
                    if(role.getText().isEmpty()) {/*role.setText("Role is mandatory\n");*/ role.setBackground(Color.pink);}
                    if(startDate.getText().isEmpty()) {/*startDate.setText("Start Date is mandatory\n");*/ startDate.setBackground(Color.pink);}
                    if(salary.getText().isEmpty()) {/*salary.setText("Salary is mandatory\n");*/ salary.setBackground(Color.pink);}
                    if(department.getText().isEmpty()) {/*department.setText("Department is mandatory\n");*/ department.setBackground(Color.pink);}
                    if(Address.getText().isEmpty()) {/*Address.setText("Address is mandatory\n");*/ Address.setBackground(Color.pink);}
                    if(email.getText().isEmpty()) {/*email.setText("email is mandatory\n");*/ email.setBackground(Color.pink);}
                    msgUpdated.setText("Please fill all required fields");
                }
            }
        });
        
        buttonBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e)
        	{
        		frame.dispose();
        		new HomePageGUI();
        	}
        });

    }

    public static void main(String[] args) {
        PageThree pt = new PageThree();
    }

    public static Connection getConnection() throws SQLException {
        String dbUrl = "jdbc:sqlite:C:\\SQLiteStudio\\PayrollSystem";
        Connection connection = DriverManager.getConnection(dbUrl);
        return connection;
    }

}