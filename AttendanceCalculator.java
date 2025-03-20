import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AttendanceCalculator extends JFrame {

    private HashMap<String, Integer> totalClasses = new HashMap<>();
    private HashMap<String, Integer> absences = new HashMap<>();
    private ArrayList<String> subjects = new ArrayList<>();
    private JTextArea displayArea;

    public AttendanceCalculator() {
        super("Attendance Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Add Subject Panel
        JPanel addSubjectPanel = new JPanel();
        JTextField subjectField = new JTextField(15);
        JButton addSubjectButton = new JButton("Add Subject");
        addSubjectPanel.add(new JLabel("Subject:"));
        addSubjectPanel.add(subjectField);
        addSubjectPanel.add(addSubjectButton);

        // Add Total Classes Panel
        JPanel addClassesPanel = new JPanel();
        JTextField classesField = new JTextField(5);
        JButton addClassesButton = new JButton("Add Classes");
        addClassesPanel.add(new JLabel("Total Classes:"));
        addClassesPanel.add(classesField);
        addClassesPanel.add(addClassesButton);


        // Mark Absent Panel
        JPanel markAbsentPanel = new JPanel();
        JComboBox<String> subjectDropdown = new JComboBox<>(); // Dropdown for subjects
        JButton markAbsentButton = new JButton("Mark Absent");
        markAbsentPanel.add(new JLabel("Subject:"));
        markAbsentPanel.add(subjectDropdown); 
        markAbsentPanel.add(markAbsentButton);

        // View Attendance Panel
        JPanel viewAttendancePanel = new JPanel();
        displayArea = new JTextArea(10, 40);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        JButton refreshButton = new JButton("Refresh");
        viewAttendancePanel.add(scrollPane);
        viewAttendancePanel.add(refreshButton);
        JButton canLeaveButton=new JButton("Classes I can leave");
        viewAttendancePanel.add(canLeaveButton);



        // Add panels to the frame
        add(addSubjectPanel, BorderLayout.NORTH);
        add(addClassesPanel, BorderLayout.CENTER);
      add(markAbsentPanel,BorderLayout.SOUTH);

        add(viewAttendancePanel, BorderLayout.EAST);
        //Action Listeners


        addSubjectButton.addActionListener(e -> {
            String subject = subjectField.getText().trim();
            if (!subject.isEmpty()&&!subjects.contains(subject)) {
                subjects.add(subject);
                subjectDropdown.addItem(subject); // Add to dropdown
                subjectField.setText("");
            }else if(subjects.contains(subject)) {
            	JOptionPane.showMessageDialog(this,"Subject already exists!","Error",JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Enter a subject name!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        addClassesButton.addActionListener(e -> {
            String subject = subjectDropdown.getSelectedItem().toString();
            try {
                int classes = Integer.parseInt(classesField.getText().trim());
                if (classes > 0) {
                    totalClasses.put(subject, classes);
                    classesField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Enter a valid number of classes!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input for classes!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        markAbsentButton.addActionListener(e -> {
            String subject = subjectDropdown.getSelectedItem().toString();            
             absences.put(subject, absences.getOrDefault(subject, 0) + 1);
            
        });

        refreshButton.addActionListener(e -> displayAttendance());

        canLeaveButton.addActionListener(e->displayLeave());
        setVisible(true);
    }

    private void displayAttendance() {
        displayArea.setText("");
        for (String subject : subjects) {
            int total = totalClasses.getOrDefault(subject, 0);
            int absent = absences.getOrDefault(subject, 0);
          
            if(total!=0) {
            double percentage = (total - absent) * 100.0 / total;
            if(percentage<0) {
            	percentage=0;
            }
            displayArea.append(subject + ": Total Classes - " + total + ", Absent - " + absent + ", Percentage - " + String.format("%.2f", percentage) + "%\n");
            }else {
            	displayArea.append("Please enter the number of classes for "+subject+"\n");
            }
        }
    }


	private void displayLeave() {
		displayArea.setText("");
		for(String subject: subjects) {
			int total = totalClasses.getOrDefault(subject, 0);
			int absent=absences.getOrDefault(subject,0);
			
			if(total!=0) {
				int leave=(int)(total*0.20)-absent;
				if(leave<0) {
					leave=0;
					displayArea.append(subject+": You can't leave anymore classes\n");
				}else
				displayArea.append(subject+": You can leave "+leave+" more classes\n");
			}else {
				displayArea.append("Please enter the number of classes for "+subject+"\n");
			}
		}
		
	}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(AttendanceCalculator::new);
    }
}
