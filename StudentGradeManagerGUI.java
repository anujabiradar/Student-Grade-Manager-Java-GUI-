import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// Student class with private fields and getters
class Student {
    private String name;
    private int grade;

    public Student(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getGrade() {
        return grade;
    }
}

// Main GUI class
public class StudentGradeManagerGUI extends JFrame {
    private JTextField nameField, gradeField;
    private JTextArea displayArea;
    private ArrayList<Student> students;

    public StudentGradeManagerGUI() {
        students = new ArrayList<>();

        // Window setup
        setTitle("Student Grade Manager");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Student Grade (0-100):"));
        gradeField = new JTextField();
        inputPanel.add(gradeField);

        JButton addButton = new JButton("Add Student");
        JButton summaryButton = new JButton("Show Summary");
        inputPanel.add(addButton);
        inputPanel.add(summaryButton);

        // Display area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Add panels to frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Add Student button action
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String gradeText = gradeField.getText().trim();

                if (name.isEmpty() || gradeText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter both name and grade!");
                    return;
                }

                try {
                    int grade = Integer.parseInt(gradeText);
                    if (grade < 0 || grade > 100) {
                        JOptionPane.showMessageDialog(null, "Grade must be between 0 and 100!");
                        return;
                    }

                    students.add(new Student(name, grade));
                    displayArea.append("Added: " + name + " - " + grade + "\n");

                    nameField.setText("");
                    gradeField.setText("");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Grade must be a valid number!");
                }
            }
        });

        // Show Summary button action
        summaryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (students.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No students added yet!");
                    return;
                }

                int sum = 0, highest = Integer.MIN_VALUE, lowest = Integer.MAX_VALUE;
                String topStudent = "", lowStudent = "";

                for (Student s : students) {
                    int grade = s.getGrade();
                    sum += grade;

                    if (grade > highest) {
                        highest = grade;
                        topStudent = s.getName();
                    }

                    if (grade < lowest) {
                        lowest = grade;
                        lowStudent = s.getName();
                    }
                }

                double average = (double) sum / students.size();

                StringBuilder report = new StringBuilder();
                report.append("===== Student Grades Summary =====\n");
                for (Student s : students) {
                    report.append(String.format("%-15s : %d\n", s.getName(), s.getGrade()));
                }
                report.append("\nClass Average: ").append(String.format("%.2f", average));
                report.append("\nHighest Score: ").append(highest).append(" (by ").append(topStudent).append(")");
                report.append("\nLowest Score: ").append(lowest).append(" (by ").append(lowStudent).append(")");

                JOptionPane.showMessageDialog(null, report.toString(), "Summary Report", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentGradeManagerGUI().setVisible(true);
        });
    }
}
