package view;

import controller.MainController;
import java.awt.*;
import javax.swing.*;
import model.Student;
import model.Subject;

public class AdminView {
    private final MainController controller;
    private JFrame frame;
    private JComboBox<String> studentBox;
    private JComboBox<String> subjectBox;
    private JTextField gradeField;

    public AdminView(MainController c) {
        controller = c;
        init();
    }

    private void init() {
        frame = new JFrame("Admin");
        frame.setSize(500, 200);
        frame.setLayout(new GridLayout(4, 2));
        studentBox = new JComboBox<>();
        subjectBox = new JComboBox<>();
        gradeField = new JTextField();

        for (Student s : controller.getAllStudents()) {
            studentBox.addItem(s.id + " - " + s.getFullName());
        }

        for (Subject s : controller.getAllSubjects()) {
            subjectBox.addItem(s.id + " - " + s.name);
        }
        
        frame.add(new JLabel("Student:"));
        frame.add(studentBox);
        frame.add(new JLabel("Subject:"));
        frame.add(subjectBox);
        frame.add(new JLabel("Grade:"));
        frame.add(gradeField);
        JButton assign = new JButton("Assign Grade");
        assign.addActionListener(e -> {
            String st = ((String) studentBox.getSelectedItem()).split(" - ")[0];
            String su = ((String) subjectBox.getSelectedItem()).split(" - ")[0];
            controller.assignGrade(st, su, gradeField.getText().trim());
            JOptionPane.showMessageDialog(frame, "Grade assigned");
        });
        frame.add(assign);
    }

    public void show() {
        frame.setVisible(true);
    }
}
