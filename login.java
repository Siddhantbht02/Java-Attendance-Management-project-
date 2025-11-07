import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

public class login extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField_1; // SAP ID field
    private JPasswordField passwordField; // Password field

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    login frame = new login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("LOGIN");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
        lblNewLabel.setBounds(37, 150, 160, 60);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Sap_Id:");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1.setBounds(295, 150, 100, 15);
        contentPane.add(lblNewLabel_1);
        
        JLabel lblNewLabel_1_1 = new JLabel("Password:");
        lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1_1.setBounds(295, 190, 100, 15);
        contentPane.add(lblNewLabel_1_1);
        
        textField_1 = new JTextField();
        textField_1.setBounds(405, 146, 100, 25);
        contentPane.add(textField_1);
        textField_1.setColumns(10);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(405, 185, 100, 25);
        contentPane.add(passwordField);
        
        JButton btnLogin = new JButton("Next");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performLogin();
                Subject subjectpage = new Subject();
                subjectpage.setVisible(true);
                dispose();
            }
        });
        btnLogin.setFont(new Font("Tahoma", Font.BOLD, 10));
        btnLogin.setBounds(295, 255, 85, 21);
        contentPane.add(btnLogin);
        
        JButton btnRegister = new JButton("Register");
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open Registration Page
                LoginPage registrationPage = new LoginPage();
                registrationPage.setVisible(true);
                dispose(); // Close current login window
            }
        });
        btnRegister.setFont(new Font("Tahoma", Font.BOLD, 10));
        btnRegister.setBounds(410, 255, 85, 21);
        contentPane.add(btnRegister);
    }

    /**
     * Perform login authentication
     */
    private void performLogin() {
        // Get input values
        String sapid = textField_1.getText().trim();
        String password = new String(passwordField.getPassword());

        // Validate inputs
        if (sapid.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Database connection and login authentication
        try {
            // Database connection parameters for Oracle
            String url = "jdbc:oracle:thin:@localhost:1521:xe"; 
            String username = "system"; 
            String dbPassword = "tiger";

            // Load Oracle JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish connection
            Connection con = DriverManager.getConnection(url, username, dbPassword);

            // Prepare SQL statement to check user credentials
            String sql = "SELECT * FROM users WHERE sapid = ? AND password = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, sapid);
            pstmt.setString(2, password);

            // Execute query
            ResultSet rs = pstmt.executeQuery();

            // Check if user exists
            if (rs.next()) {
                // Successful login
                JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                // You can open a new window or perform other actions here
                // For now, we'll just close the login window
                dispose();
            } else {
                // Login failed
                JOptionPane.showMessageDialog(this, "Invalid SAP ID or Password", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }

            // Close resources
            rs.close();
            pstmt.close();
            con.close();

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Oracle JDBC Driver not found!", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}