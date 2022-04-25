import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.time.temporal.TemporalAdjusters;

import static java.time.LocalDate.now;

public class PageFour extends javax.swing.JFrame{
    public JFrame frame;
    public JPanel panel;
    public JLabel labelID, labelFN, labelLN, labelRole, labelSDate, labelEDate, labelSalary, labelDepartment, labelAddress, labelEmail, msgGenerated, msgUpdated, payHeroTitle;
    public JTextField ID, firstName, lastName, role, startDate, endDate, salary, department, Address, email;
    static String dBID, dBFirstName, dBLastName, dBRole, dBStartDate, dBEndDate, dBDepartment, dBAddress, dBEmail;
    static double dBSalary;
    public javax.swing.JButton buttonUpdate, buttonGenerate, buttonBack;
    HomePageGUI hpgui;

    public PageFour(){
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
        ID.setText(dBID);
        panel.add(ID);

        //First Name
        labelFN = new JLabel("First Name:");
        labelFN.setBounds(20, 130, 100, 25);
        panel.add(labelFN);
        firstName = new JTextField();
        firstName.setBounds(100, 130, 150, 25);
        firstName.setText(dBFirstName);
        panel.add(firstName);

        //Last Name
        labelLN = new JLabel("Last Name:");
        labelLN.setBounds(20, 160, 100, 25);
        panel.add(labelLN);
        lastName = new JTextField();
        lastName.setBounds(100, 160, 150, 25);
        lastName.setText(dBLastName);
        panel.add(lastName);

        //Address
        labelAddress = new JLabel("Address:");
        labelAddress.setBounds(20, 190, 100, 25);
        panel.add(labelAddress);
        Address = new JTextField();
        Address.setBounds(100, 190, 150, 25);
        Address.setText(dBAddress);
        panel.add(Address);

        //Start date
        labelSDate = new JLabel("Start Date:");
        labelSDate.setBounds(20, 220, 100, 25);
        panel.add(labelSDate);
        startDate = new JTextField();
        startDate.setBounds(100, 220, 150, 25);
        startDate.setText(dBStartDate);
        panel.add(startDate);

        //End Date
        labelEDate = new JLabel("End Date:");
        labelEDate.setBounds(300, 100, 100, 25);
        panel.add(labelEDate);
        endDate = new JTextField();
        endDate.setBounds(380, 100, 150, 25);
        endDate.setText(dBEndDate);
        panel.add(endDate);

        //Role
        labelRole = new JLabel("Role:");
        labelRole.setBounds(300, 130, 100, 25);
        panel.add(labelRole);
        role = new JTextField();
        role.setBounds(380, 130, 150, 25);
        role.setText(dBRole);
        panel.add(role);

        //Department
        labelDepartment = new JLabel("Department:");
        labelDepartment.setBounds(300, 160, 100, 25);
        panel.add(labelDepartment);
        department = new JTextField();
        department.setBounds(380, 160, 150, 25);
        department.setText(dBDepartment);
        panel.add(department);

        //Salary
        labelSalary = new JLabel("Salary:");
        labelSalary.setBounds(300, 190, 100, 25);
        panel.add(labelSalary);
        salary = new JTextField();
        salary.setBounds(380, 190, 150, 25);
        salary.setText(dBSalary + "");
        panel.add(salary);

        //email
        labelEmail = new JLabel("email:");
        labelEmail.setBounds(300, 220, 100, 25);
        panel.add(labelEmail);
        email = new JTextField();
        email.setBounds(380, 220, 150, 25);
        email.setText(dBEmail);
        panel.add(email);

        //Record is updated message
        msgUpdated = new JLabel(" ");
        msgUpdated.setBounds(250,280,200,25);
        panel.add(msgUpdated);

        //button
        buttonUpdate = new JButton("Update Record");
        buttonUpdate.setBounds(380, 280, 150, 25);
        panel.add(buttonUpdate);
        buttonUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    String query = "UPDATE Employees " +
                            "SET First_Name = '"+ firstName.getText() + "', Last_Name = '" + lastName.getText() + "', role ='" + role.getText() +
                            "', StartDate = '" + startDate.getText() + "', EndDate = '" + endDate.getText() + "', salary = "+
                            salary.getText() + ", department = '" + department.getText() + "', Address = '" + Address.getText() + "', email ='" + email.getText() +"' WHERE id ="+ ID.getText();
                    Connection c4 = getConnection();
                    Statement s4 = c4.createStatement();
                    int rowCount = s4.executeUpdate(query);
                    msgUpdated.setText("Record is Updated");
                }catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        //Payslip is created message
        msgGenerated = new JLabel(" ");
        msgGenerated.setBounds(250,310,200,25);
        panel.add(msgGenerated);

        //button to generate Payslip
        buttonGenerate = new JButton("Create Payslip");
        buttonGenerate.setBounds(380, 310, 150, 25);
        panel.add(buttonGenerate);
        buttonGenerate.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try{
                    String path = "C:\\Users\\mmamyt2\\Desktop\\"+firstName.getText()+".pdf";
                    String text = "                  PAYSLIP \n" +
                            "\n"+
                            "\nCompany: Disney Gold Mining and Co" +
                            "\n12345 Mainstreet, Chicago IL" +
                            "\nPay Date: "+ now() +
                            "\nPay Period: "+ now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth())+ " - " + now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()) + "\n"+
                            "\nFull Name: "+ firstName.getText() + " " + lastName.getText() +
                            "\nAddress: " + Address.getText() +
                            "\nSalary: " + salary.getText() + " Gold Nuggets";
                    Paragraph par = new Paragraph(text);
                    PdfWriter pdfWriter = new PdfWriter(path);
                    PdfDocument pdfDoc = new PdfDocument(pdfWriter);
                    pdfDoc.addNewPage();
                    Document document = new Document(pdfDoc);
                    document.add(par);
                    document.close();
                    msgGenerated.setText("Payslip is Created");
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        });
        
        buttonBack = new JButton("Back");
        buttonBack.setBounds(10,1,100,25);
        panel.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e)
        	{
        		frame.dispose();
        		new HomePageGUI();
        	}
        });

        frame.setVisible(true);
        frame.setResizable(false);
    }

    public static Connection getConnection() throws SQLException {
        String dbUrl = "jdbc:sqlite:C:\\SQLiteStudio\\PayrollSystem";
        Connection connection = DriverManager.getConnection(dbUrl);
        return connection;
    }

    public static void main(String[] args) throws FileNotFoundException {
        PageFour pf = new PageFour();
    }
}