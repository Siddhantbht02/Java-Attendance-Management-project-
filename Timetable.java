import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Timetable extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Connection conn;
    private Map<String, JToggleButton> presentButtons = new HashMap<>();
    private Map<String, JToggleButton> absentButtons = new HashMap<>();
    private Map<String, LocalDateTime> lastEntryTime = new HashMap<>();
    private String currentDay;
    private List<String> subjects = new ArrayList<>();
    private static final String[] DAYS = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Timetable frame = new Timetable();
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
    public Timetable() {
        initializeDatabase();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblToday = new JLabel("TODAY");
        lblToday.setHorizontalAlignment(SwingConstants.CENTER);
        lblToday.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblToday.setBounds(38, 135, 160, 60);
        contentPane.add(lblToday);
        
        // Get current day of week and set as default selection
        int currentDayIndex = LocalDate.now().getDayOfWeek().getValue() - 1;
        if (currentDayIndex > 5) currentDayIndex = 0; // If Sunday, default to Monday
        currentDay = DAYS[currentDayIndex];
        
        JComboBox<String> dayComboBox = new JComboBox<>();
        dayComboBox.setFont(new Font("Tahoma", Font.BOLD, 12));
        dayComboBox.setModel(new DefaultComboBoxModel<>(DAYS));
        dayComboBox.setSelectedIndex(currentDayIndex);
        dayComboBox.setBounds(71, 193, 100, 25);
        contentPane.add(dayComboBox);
        
        // Add day selection listener
        dayComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentDay = (String) dayComboBox.getSelectedItem();
                loadAttendanceForDay(currentDay);
            }
        });
        
        // Fetch subject names from database and create UI elements
        loadSubjectsAndCreateUI();
        
        JLabel lblPresent = new JLabel("Present");
        lblPresent.setHorizontalAlignment(SwingConstants.CENTER);
        lblPresent.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblPresent.setBounds(375, 10, 100, 25);
        contentPane.add(lblPresent);
        
        JLabel lblAbsent = new JLabel("Absent");
        lblAbsent.setHorizontalAlignment(SwingConstants.CENTER);
        lblAbsent.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblAbsent.setBounds(476, 10, 100, 25);
        contentPane.add(lblAbsent);
        
        JButton btnDone = new JButton("DONE");
        btnDone.setFont(new Font("Tahoma", Font.BOLD, 10));
        btnDone.setBounds(267, 330, 85, 25);
        contentPane.add(btnDone);
        
        // Add Done button listener
        btnDone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAttendance();
            }
        });
        
        JButton btnNext = new JButton("RESET");
        btnNext.setFont(new Font("Tahoma", Font.BOLD, 10));
        btnNext.setBounds(380, 330, 85, 25);
        contentPane.add(btnNext);
        
        // Add Reset button listener
        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetButtons();
            }
        });
        
        JButton btnViewStats = new JButton("STATS");
        btnViewStats.setFont(new Font("Tahoma", Font.BOLD, 10));
        btnViewStats.setBounds(496, 330, 85, 25);
        contentPane.add(btnViewStats);
        
        // Add Stats button listener
        btnViewStats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStatistics();
            }
        });
        
        // Load initial data
        loadAttendanceForDay(currentDay);
    }
    
    private void loadSubjectsAndCreateUI() {
        try {
            // Clear previous data if any
            subjects.clear();
            
            // Query database for subject names
            String sql = "SELECT subjects FROM timetable ORDER BY subjects";
            try (Statement stmt = conn.createStatement(); 
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                int rowIndex = 0;
                while (rs.next()) {
                    String subject = rs.getString("subjects");
                    subjects.add(subject);
                    createSubjectRow(subject, rowIndex);
                    rowIndex++;
                }
                
                // If no subjects were found, use default ones
                if (subjects.isEmpty()) {
                    String[] defaultSubjects = {"SUBJECT 1", "SUBJECT 2", "SUBJECT 3", "SUBJECT 4", "SUBJECT 5", "SUBJECT 6", "SUBJECT 7"};
                    for (int i = 0; i < defaultSubjects.length; i++) {
                        subjects.add(defaultSubjects[i]);
                        createSubjectRow(defaultSubjects[i], i);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading subjects: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            
            // Fallback to default subjects if database operation fails
            String[] defaultSubjects = {"SUBJECT 1", "SUBJECT 2", "SUBJECT 3", "SUBJECT 4", "SUBJECT 5", "SUBJECT 6", "SUBJECT 7"};
            for (int i = 0; i < defaultSubjects.length; i++) {
                subjects.add(defaultSubjects[i]);
                createSubjectRow(defaultSubjects[i], i);
            }
        }
    }
    
    private void createSubjectRow(String subject, int rowIndex) {
        int yPosition = 49 + (rowIndex * 40);
        
        JLabel lblSubject = new JLabel(subject + ":");
        lblSubject.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubject.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblSubject.setBounds(257, yPosition, 100, 25);
        contentPane.add(lblSubject);
        
        // Present button
        JToggleButton btnPresent = new JToggleButton("P");
        btnPresent.setSelected(true);
        btnPresent.setBounds(395, yPosition, 61, 22);
        contentPane.add(btnPresent);
        presentButtons.put(subject, btnPresent);
        
        // Absent button
        JToggleButton btnAbsent = new JToggleButton("F");
        btnAbsent.setBounds(496, yPosition, 61, 22);
        contentPane.add(btnAbsent);
        absentButtons.put(subject, btnAbsent);
        
        // Add exclusive toggling between P and F buttons
        btnPresent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (btnPresent.isSelected()) {
                    btnAbsent.setSelected(false);
                }
            }
        });
        
        btnAbsent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (btnAbsent.isSelected()) {
                    btnPresent.setSelected(false);
                }
            }
        });
    }
    
    private void initializeDatabase() {
        try {
            // Initialize database connection
            // Note: In a real application, you would need to provide proper connection details
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "tiger");
            
            // Check if table exists, create if not
            boolean tableExists = false;
            try (ResultSet rs = conn.getMetaData().getTables(null, null, "TIMETABLE", null)) {
                tableExists = rs.next();
            }
            
            if (!tableExists) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute("CREATE TABLE timetable (" +
                            "subjects VARCHAR2(50) NULL," +
                            "monday NUMBER," +
                            "tuesday NUMBER," +
                            "wednesday NUMBER," +
                            "thursday NUMBER," +
                            "friday NUMBER," +
                            "saturday NUMBER," +
                            "total NUMBER NOT NULL)");
                    
                    // Insert initial rows for each subject
                    String[] defaultSubjects = {"SUBJECT 1", "SUBJECT 2", "SUBJECT 3", "SUBJECT 4", "SUBJECT 5", "SUBJECT 6", "SUBJECT 7"};
                    for (String subject : defaultSubjects) {
                        try (PreparedStatement pstmt = conn.prepareStatement(
                                "INSERT INTO timetable (subjects, monday, tuesday, wednesday, thursday, friday, saturday, total) " +
                                "VALUES (?, 0, 0, 0, 0, 0, 0, 0)")) {
                            pstmt.setString(1, subject);
                            pstmt.executeUpdate();
                        }
                    }
                }
            }
            
            // Create additional table to track last entry time
            try (Statement stmt = conn.createStatement()) {
                try {
                    stmt.execute("CREATE TABLE last_entry (" +
                            "subject VARCHAR2(50) PRIMARY KEY," +
                            "day VARCHAR2(20)," +
                            "entry_time TIMESTAMP)");
                } catch (SQLException e) {
                    // Table might already exist, ignore
                }
            }
            
            // Create yourtotal column if it doesn't exist
            try (Statement stmt = conn.createStatement()) {
                try {
                    stmt.execute("ALTER TABLE timetable ADD yourtotal NUMBER DEFAULT 0");
                } catch (SQLException e) {
                    // Column might already exist, ignore
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to connect to database: " + e.getMessage(), 
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadAttendanceForDay(String day) {
        // First reset all buttons
        resetButtons();
        
        // Now load the last entry timestamps
        try {
            String sql = "SELECT subject, day, entry_time FROM last_entry";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String subject = rs.getString("subject");
                    String entryDay = rs.getString("day");
                    LocalDateTime timestamp = rs.getTimestamp("entry_time").toLocalDateTime();
                    
                    lastEntryTime.put(subject, timestamp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading attendance data: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void saveAttendance() {
        LocalDateTime now = LocalDateTime.now();
        boolean anyChanges = false;
        
        for (String subject : subjects) {
            // Get toggle buttons for this subject
            JToggleButton presentButton = presentButtons.get(subject);
            JToggleButton absentButton = absentButtons.get(subject);
            
            // Skip if the buttons don't exist (shouldn't happen, but just in case)
            if (presentButton == null || absentButton == null) {
                continue;
            }
            
            // Check if 24 hours have passed since last entry for this subject
//            if (lastEntryTime.containsKey(subject)) {
//                LocalDateTime lastTime = lastEntryTime.get(subject);
//                if (lastTime.plusHours(24).isAfter(now)) {
//                    JOptionPane.showMessageDialog(this,
//                            "Cannot update " + subject + " until " + 
//                            lastTime.plusHours(24).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
//                            "Time Restriction", JOptionPane.WARNING_MESSAGE);
//                    continue;
//                }
//            }
            
            try {
                // Determine attendance value to add
                int valueToAdd = 0;
                if (presentButton.isSelected()) {
                    valueToAdd = 1; // Set to 1 when present
                } else if (absentButton.isSelected()) {
                    valueToAdd = 0; // Set to 0 when absent
                } else {
                    // Neither button selected, skip this subject
                    continue;
                }
                
                // Get current value
                int currentValue = 0; // Default to 0

                String querySql = "SELECT " + currentDay.toLowerCase() + " FROM timetable WHERE subjects = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(querySql)) {
                    pstmt.setString(1, subject);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        currentValue = rs.getInt(1); // Ensure it gets a valid integer
                    }
                }

                // Debugging: Print values
                System.out.println("Subject: " + subject + ", Current Value: " + currentValue + ", Value to Add: " + valueToAdd);

                int newValue = (valueToAdd == 1) ? (currentValue + 1) : currentValue;
                String updateSql = "UPDATE timetable SET " + currentDay.toLowerCase() + " = ? WHERE subjects = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                    pstmt.setInt(1, newValue);
                    pstmt.setString(2, subject);
                    int rowsAffected = pstmt.executeUpdate();  // Get affected row count
                    System.out.println("Rows updated: " + rowsAffected);

                    if (rowsAffected == 0) {
                        System.out.println("⚠ No rows updated. Check if the subject \"" + subject + "\" exists in the database.");
                    }
                }
                
                // Update yourtotal (sum of all days' attendance)
                String yourTotalSql = "UPDATE timetable SET yourtotal = monday + tuesday + wednesday + thursday + friday + saturday WHERE subjects = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(yourTotalSql)) {
                    pstmt.setString(1, subject);
                    pstmt.executeUpdate();
                }
                
                // Update last entry time
                String timeSql = "MERGE INTO last_entry USING dual ON (subject = ?) " +
                        "WHEN MATCHED THEN UPDATE SET day = ?, entry_time = ? " +
                        "WHEN NOT MATCHED THEN INSERT (subject, day, entry_time) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(timeSql)) {
                    pstmt.setString(1, subject);
                    pstmt.setString(2, currentDay);
                    pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(now));
                    pstmt.setString(4, subject);
                    pstmt.setString(5, currentDay);
                    pstmt.setTimestamp(6, java.sql.Timestamp.valueOf(now));
                    pstmt.executeUpdate();
                }
                
                // Update in-memory tracking
                lastEntryTime.put(subject, now);
                anyChanges = true;
                
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving attendance for " + subject + ": " + e.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        if (anyChanges) {
            JOptionPane.showMessageDialog(this, "Attendance saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void resetButtons() {
        for (String subject : subjects) {
            JToggleButton presentButton = presentButtons.get(subject);
            JToggleButton absentButton = absentButtons.get(subject);
            
            if (presentButton != null && absentButton != null) {
                presentButton.setSelected(true);
                absentButton.setSelected(false);
            }
        }
    }
    
    private void showStatistics() {
        try {
            StringBuilder stats = new StringBuilder("Attendance Statistics:\n\n");
            stats.append(String.format("%-12s%-8s%-8s%-8s%-8s%-8s%-8s%-8s%-8s\n", 
                    "Sub","M","T","W","Th","F","S","P","TCount"));
            stats.append("----------------------------------------------------------------------------\n");
            
            final double ATTENDANCE_THRESHOLD = 0.8; // 80% attendance required
            
            String sql = "SELECT subjects, monday, tuesday, wednesday, thursday, friday, saturday, yourtotal, total FROM timetable ORDER BY subjects";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String subject = rs.getString("subjects");
                    int yourTotal = rs.getInt("yourtotal");
                    int totalLectures = rs.getInt("total");
                    
                    // Calculate minimum required attendance (80% of total lectures)
                    int minRequired = (int)Math.ceil(totalLectures * ATTENDANCE_THRESHOLD);
                    
                    stats.append(String.format("%-12s%-8d%-8d%-8d%-8d%-8d%-8d%-8d%-8d\n",
                            subject,
                            rs.getInt("monday"),
                            rs.getInt("tuesday"),
                            rs.getInt("wednesday"),
                            rs.getInt("thursday"),
                            rs.getInt("friday"),
                            rs.getInt("saturday"),
                            yourTotal,
                            totalLectures));
                            
                    // Calculate how many more lectures can be missed
                    int canMiss = Math.max(0, yourTotal - minRequired);
                    stats.append(String.format("  You can miss %d more lectures for %s (min required: %d)\n\n", 
                            canMiss, subject, minRequired));
                }
            }
            
            JOptionPane.showMessageDialog(this, stats.toString(), "Attendance Statistics", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading statistics: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}