import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Subject extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    
    // Subject text fields
    public JTextField textField_4; // Subject 1
    public JTextField textField; // Subject 2
    public JTextField textField_1; // Subject 3
    public JTextField textField_2; // Subject 4
    public JTextField textField_5; // Subject 5
    public JTextField textField_3; // Subject 6
    public JTextField textField_6; // Subject 7
    
    // Total hours text fields for each subject
    public JTextField textField_9; // Subject 1 total hours
    public JTextField textField_10; // Subject 2 total hours
    public JTextField textField_11; // Subject 3 total hours
    public JTextField textField_12; // Subject 4 total hours
    public JTextField textField_13; // Subject 5 total hours
    public JTextField textField_14; // Subject 6 total hours
    public JTextField textField_15; // Subject 7 total hours
    
    // Public Next button to allow listener attachment
    public JButton btnNext;
    private JButton btnEdit;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Subject frame = new Subject();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Subject() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Subject");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(37, 150, 160, 60);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1_1_2 = new JLabel("SUBJECT 1:");
        lblNewLabel_1_1_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1_1_2.setBounds(179, 52, 100, 25);
        contentPane.add(lblNewLabel_1_1_2);
        
        JLabel lblNewLabel_1_1_3 = new JLabel("SUBJECT 2:");
        lblNewLabel_1_1_3.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1_1_3.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1_1_3.setBounds(179, 92, 100, 25);
        contentPane.add(lblNewLabel_1_1_3);
        
        JLabel lblNewLabel_1 = new JLabel("SUBJECT 3:");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1.setBounds(179, 132, 100, 25);
        contentPane.add(lblNewLabel_1);
        
        JLabel lblNewLabel_1_1 = new JLabel("SUBJECT 4:");
        lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1_1.setBounds(179, 172, 100, 25);
        contentPane.add(lblNewLabel_1_1);
        
        JLabel lblNewLabel_1_2 = new JLabel("SUBJECT 5:");
        lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1_2.setBounds(179, 212, 100, 25);
        contentPane.add(lblNewLabel_1_2);
        
        JLabel lblNewLabel_1_1_1 = new JLabel("SUBJECT 6:");
        lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1_1_1.setBounds(179, 252, 100, 25);
        contentPane.add(lblNewLabel_1_1_1);
        
        JLabel lblNewLabel_1_3 = new JLabel("SUBJECT 7:");
        lblNewLabel_1_3.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1_3.setBounds(179, 292, 100, 25);
        contentPane.add(lblNewLabel_1_3);
        
        // Subject Text Fields
        textField_4 = new JTextField(); // Subject 1
        textField_4.setColumns(10);
        textField_4.setBounds(289, 52, 100, 25);
        contentPane.add(textField_4);
        
        textField = new JTextField(); // Subject 2
        textField.setColumns(10);
        textField.setBounds(289, 92, 100, 25);
        contentPane.add(textField);
        
        textField_1 = new JTextField(); // Subject 3
        textField_1.setColumns(10);
        textField_1.setBounds(289, 132, 100, 25);
        contentPane.add(textField_1);
        
        textField_2 = new JTextField(); // Subject 4
        textField_2.setColumns(10);
        textField_2.setBounds(289, 172, 100, 25);
        contentPane.add(textField_2);
        
        textField_5 = new JTextField(); // Subject 5
        textField_5.setColumns(10);
        textField_5.setBounds(289, 212, 100, 25);
        contentPane.add(textField_5);
        
        textField_3 = new JTextField(); // Subject 6
        textField_3.setColumns(10);
        textField_3.setBounds(289, 252, 100, 25);
        contentPane.add(textField_3);
        
        textField_6 = new JTextField(); // Subject 7
        textField_6.setColumns(10);
        textField_6.setBounds(289, 292, 100, 25);
        contentPane.add(textField_6);
        
        // Total Hours Labels
        JLabel lblNewLabel_1_1_2_1 = new JLabel("TOTAL HOURS");
        lblNewLabel_1_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1_1_2_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1_1_2_1.setBounds(440, 10, 100, 25);
        contentPane.add(lblNewLabel_1_1_2_1);
        
        // Total Hours Text Fields for each subject
        textField_9 = new JTextField(); // Subject 1 total hours
        textField_9.setColumns(10);
        textField_9.setBounds(440, 52, 100, 25);
        contentPane.add(textField_9);
        
        textField_10 = new JTextField(); // Subject 2 total hours
        textField_10.setColumns(10);
        textField_10.setBounds(440, 92, 100, 25);
        contentPane.add(textField_10);
        
        textField_11 = new JTextField(); // Subject 3 total hours
        textField_11.setColumns(10);
        textField_11.setBounds(440, 132, 100, 25);
        contentPane.add(textField_11);
        
        textField_12 = new JTextField(); // Subject 4 total hours
        textField_12.setColumns(10);
        textField_12.setBounds(440, 172, 100, 25);
        contentPane.add(textField_12);
        
        textField_13 = new JTextField(); // Subject 5 total hours
        textField_13.setColumns(10);
        textField_13.setBounds(440, 213, 100, 25);
        contentPane.add(textField_13);
        
        textField_14 = new JTextField(); // Subject 6 total hours
        textField_14.setColumns(10);
        textField_14.setBounds(440, 253, 100, 25);
        contentPane.add(textField_14);
        
        textField_15 = new JTextField(); // Subject 7 total hours
        textField_15.setColumns(10);
        textField_15.setBounds(440, 293, 100, 25);
        contentPane.add(textField_15);
        
        // Next Button
        btnNext = new JButton("NEXT");
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SubjectTimetableInsertion subjectInsertion = new SubjectTimetableInsertion(Subject.this);
                subjectInsertion.insertSubjectTimetable();
            }
        });
        btnNext.setFont(new Font("Tahoma", Font.BOLD, 10));
        btnNext.setBounds(304, 327, 85, 25);
        contentPane.add(btnNext);
        
        btnEdit = new JButton("EDIT");
        btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editSubjectTimetable();
            }
        });
        btnEdit.setFont(new Font("Tahoma", Font.BOLD, 10));
        btnEdit.setBounds(440, 328, 85, 25);
        contentPane.add(btnEdit);
    }

    // Method to edit existing subjects
    private void editSubjectTimetable() {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            // Establish database connection
            connection = DriverManager.getConnection(
                SubjectTimetableInsertion.DB_URL, 
                SubjectTimetableInsertion.DB_USERNAME, 
                SubjectTimetableInsertion.DB_PASSWORD
            );

            // Prepare SQL statement for updating subjects and total hours
            String sql = "UPDATE timetable SET total = ? WHERE subjects = ?";
            pstmt = connection.prepareStatement(sql);

            // Subjects and corresponding total hours to update
            String[][] subjectsData = {
                {textField_4.getText(), textField_9.getText()},
                {textField.getText(), textField_10.getText()},
                {textField_1.getText(), textField_11.getText()},
                {textField_2.getText(), textField_12.getText()},
                {textField_5.getText(), textField_13.getText()},
                {textField_3.getText(), textField_14.getText()},
                {textField_6.getText(), textField_15.getText()}
            };

            // Flag to track if any subjects were updated
            boolean subjectsUpdated = false;

            // Update each subject
            for (String[] subjectData : subjectsData) {
                // Skip empty subjects
                if (subjectData[0] == null || subjectData[0].trim().isEmpty() || 
                    subjectData[1] == null || subjectData[1].trim().isEmpty()) {
                    continue;
                }

                // Set parameters for the prepared statement
                pstmt.setInt(1, Integer.parseInt(subjectData[1])); // Total hours
                pstmt.setString(2, subjectData[0]); // Subjects

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    subjectsUpdated = true;
                }
            }

            // Check if any subjects were updated
            if (subjectsUpdated) {
                JOptionPane.showMessageDialog(this, 
                    "Subjects Updated Successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No Subjects Updated", 
                    "Warning", 
                    JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Database Error: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Invalid Number Format: Please enter valid numeric hours", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            // Close resources
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Existing SubjectTimetableInsertion nested class remains the same
    public static class SubjectTimetableInsertion {
        // Database connection details (as in the previous implementation)
        private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
        private static final String DB_USERNAME = "system";
        private static final String DB_PASSWORD = "tiger";

        private Subject subjectFrame;

        public SubjectTimetableInsertion(Subject subjectFrame) {
            this.subjectFrame = subjectFrame;
        }

        public void insertSubjectTimetable() {
            Connection connection = null;
            PreparedStatement pstmt = null;

            try {
                // Establish database connection
                connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

                // Check total number of subjects
                String countSql = "SELECT COUNT(*) FROM timetable";
                try (PreparedStatement countStmt = connection.prepareStatement(countSql);
                     ResultSet rs = countStmt.executeQuery()) {
                    
                    if (rs.next()) {
                        int currentSubjectCount = rs.getInt(1);
                        if (currentSubjectCount >= 7) {
                        	Timetable timetableFrame = new Timetable();
                            timetableFrame.setVisible(true);
                            subjectFrame.dispose();
                        }
                    }
                }

                // Prepare SQL statement for inserting subjects and total hours
                String sql = "INSERT INTO timetable (subjects, total, monday, tuesday, wednesday, thursday, friday, saturday) " +
                             "VALUES (?, ?, 0, 0, 0, 0, 0, 0)";

                pstmt = connection.prepareStatement(sql);

                // Subjects and corresponding total hours to insert
                String[][] subjectsData = {
                    {subjectFrame.textField_4.getText(), subjectFrame.textField_9.getText()},
                    {subjectFrame.textField.getText(), subjectFrame.textField_10.getText()},
                    {subjectFrame.textField_1.getText(), subjectFrame.textField_11.getText()},
                    {subjectFrame.textField_2.getText(), subjectFrame.textField_12.getText()},
                    {subjectFrame.textField_5.getText(), subjectFrame.textField_13.getText()},
                    {subjectFrame.textField_3.getText(), subjectFrame.textField_14.getText()},
                    {subjectFrame.textField_6.getText(), subjectFrame.textField_15.getText()}
                };

                // Flag to track if any subjects were inserted
                boolean subjectsInserted = false;

                // Insert each subject as a separate row
                for (String[] subjectData : subjectsData) {
                    // Skip empty subjects
                    if (subjectData[0] == null || subjectData[0].trim().isEmpty() || 
                        subjectData[1] == null || subjectData[1].trim().isEmpty()) {
                        continue;
                    }

                    // Set parameters for the prepared statement
                    pstmt.setString(1, subjectData[0]); // Subjects
                    pstmt.setInt(2, Integer.parseInt(subjectData[1])); // Total hours

                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        subjectsInserted = true;
                    }
                }

                // Check if any subjects were inserted
                if (subjectsInserted) {
                    JOptionPane.showMessageDialog(subjectFrame, 
                        "Subjects Updated Successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Open Timetable page
                    Timetable timetableFrame = new Timetable();
                    timetableFrame.setVisible(true);
                    subjectFrame.dispose(); // Close current frame
                } else {
                    JOptionPane.showMessageDialog(subjectFrame, 
                        "No Subjects Inserted", 
                        "Warning", 
                        JOptionPane.WARNING_MESSAGE);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(subjectFrame, 
                    "Database Error: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(subjectFrame, 
                    "Invalid Number Format: Please enter valid numeric hours", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } finally {
                // Close resources
                try {
                    if (pstmt != null) pstmt.close();
                    if (connection != null) connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}