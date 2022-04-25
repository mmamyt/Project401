import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;


public class BrowseDB extends JFrame {
    public JTable table;
    public DefaultTableModel model;

    static Connection connection = null;

    public BrowseDB() {
        setPreferredSize(new Dimension(700,500));
        setResizable(false);
        setTitle("Browse Employee Data");
        pack();
        setLocationByPlatform(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container cp = getContentPane();

        //adding title
        JLabel payHeroTitle = new JLabel("                           PayHero");
        payHeroTitle.setFont(new Font("Dark Future", Font.BOLD, 34));
        payHeroTitle.setBounds(230,30, 200, 40);
        cp.add(payHeroTitle, BorderLayout.CENTER);
        cp.add(payHeroTitle, BorderLayout.NORTH);
        try
        {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Dark_Future.ttf")));
        }
        catch(IOException |FontFormatException e)
        {
            e.printStackTrace();
        }

        DefaultTableModel model=new DefaultTableModel();
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setPreferredScrollableViewportSize(new Dimension(300, 300));
        table.setFillsViewportHeight(true);
        cp.add(new JScrollPane(table));
        model.addColumn("ID");
        model.addColumn("First_Name");
        model.addColumn("Last_Name");
        model.addColumn("Address");
        model.addColumn("StartDate");
        model.addColumn("EndDate");
        model.addColumn("Salary");
        model.addColumn("Role");
        model.addColumn("Department");
        model.addColumn("Email");
        cp.setLayout(new GridLayout(3, 1));
        setVisible(true);
        try {
            Connection c = getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM Employees ");
            while (rs.next()){
                String id = rs.getString("ID");
                String firstname = rs.getString("First_Name");
                String lastname = rs.getString("Last_Name");
                String address = rs.getString("Address");
                String startdate = rs.getString("StartDate");
                String enddate = rs.getString("EndDate");
                String salary = rs.getString("Salary");
                String role = rs.getString("Role");
                String department = rs.getString("Department");
                String email = rs.getString("Email");
                model.addRow(new Object[]{id, firstname, lastname, address, startdate, enddate, salary, role, department, email});

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    public static void main(String[] args) {
        new BrowseDB();
    }

    public static Connection getConnection() throws SQLException {
        String dbUrl = "jdbc:sqlite:C:\\SQLiteStudio\\PayrollSystem";
        Connection connection = DriverManager.getConnection(dbUrl);
        return connection;
    }

}
