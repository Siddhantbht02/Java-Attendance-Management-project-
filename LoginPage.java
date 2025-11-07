import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

public class LoginPage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField; // SAPID field
    private JTextField textField_1; // Name field
    private JPasswordField passwordField; // Password field
    private JPasswordField passwordField_1; // Confirm Password field

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginPage frame = new LoginPage();
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
    public LoginPage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("REGISTER");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
        lblNewLabel.setBounds(37, 150, 160, 60);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("SAPID:");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1.setBounds(295, 90, 100, 25);
        contentPane.add(lblNewLabel_1);
        
        textField = new JTextField();
        textField.setBounds(395, 90, 100, 25);
        contentPane.add(textField);
        textField.setColumns(10);
        
        JLabel lblNewLabel_1_1 = new JLabel("NAME:");
        lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1_1.setBounds(295, 130, 100, 25);
        contentPane.add(lblNewLabel_1_1);
        
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(395, 130, 100, 25);
        contentPane.add(textField_1);
        
        JLabel lblNewLabel_1_1_1 = new JLabel("PASS:");
        lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1_1_1.setBounds(295, 170, 100, 25);
        contentPane.add(lblNewLabel_1_1_1);
        
        JLabel lblNewLabel_1_1_1_1 = new JLabel("CONFIRM PASS:");
        lblNewLabel_1_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1_1_1_1.setBounds(295, 210, 100, 25);
        contentPane.add(lblNewLabel_1_1_1_1);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(395, 170, 100, 25);
        contentPane.add(passwordField);
        
        passwordField_1 = new JPasswordField();
        passwordField_1.setBounds(395, 210, 100, 25);
        contentPane.add(passwordField_1);
        
        JButton btnNext = new JButton("Next");
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        btnNext.setFont(new Font("Tahoma", Font.BOLD, 10));
        btnNext.setBounds(410, 294, 85, 25);
        contentPane.add(btnNext);
        
        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open Login Page
                login loginPage = new login();
                loginPage.setVisible(true);
                dispose(); // Close current registration window
            }
        });
        btnLogin.setFont(new Font("Tahoma", Font.BOLD, 10));
        btnLogin.setBounds(295, 294, 85, 25);
        contentPane.add(btnLogin);
    }

    /**
     * Register user in the database
     */
    private void registerUser() {
        // Get input values
        String sapid = textField.getText().trim();
        String name = textField_1.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(passwordField_1.getPassword());

        // Validate inputs
        if (sapid.isEmpty() || name.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Database connection and user registration
        try {
            // Database connection parameters for Oracle
            String url = "jdbc:oracle:thin:@localhost:1521:xe"; 
            String username = "system"; 
            String dbPassword = "tiger";

            // Load Oracle JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish connection
            Connection con = DriverManager.getConnection(url, username, dbPassword);

            // Prepare SQL statement
            String sql = "INSERT INTO users (sapid, name, password) VALUES (?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, sapid);
            pstmt.setString(2, name);
            pstmt.setString(3, password);

            // Execute the insert
            int rowsAffected = pstmt.executeUpdate();

            // Check if insertion was successful
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "User registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Clear input fields after successful registration
                textField.setText("");
                textField_1.setText("");
                passwordField.setText("");
                passwordField_1.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Close resources
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