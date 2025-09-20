package view;
import controller.MainController;
import model.Student;
import model.Subject;
import javax.swing.*;
import java.awt.*;
import java.util.List;
public class RegistrationView {
    private final MainController controller;
    private final Student student;
    private JFrame frame;
    private JList<String> list;
    private java.util.List<Subject> subjects;

    public RegistrationView(MainController c, Student s){ controller = c; student = s; init(); }

    private void init(){
        frame = new JFrame("Register - " + student.getFullName());
        frame.setSize(700,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        subjects = controller.getAvailableSubjectsForStudent(student.id);
        DefaultListModel<String> m = new DefaultListModel<>();
        for(Subject s: subjects) m.addElement(s.id + " - " + s.name + " (" + s.credits + " cr) [" + s.getCurrentRegistered() + "/" + (s.maxCapacity==-1?"∞":s.maxCapacity) + "]");
        list = new JList<>(m);
        frame.getContentPane().add(new JScrollPane(list), BorderLayout.CENTER);

        JPanel btns = new JPanel();
        JButton detail = new JButton("Details");
        JButton reg = new JButton("Register");
        JButton back = new JButton("Back");
        btns.add(detail); btns.add(reg); btns.add(back);
        frame.getContentPane().add(btns, BorderLayout.SOUTH);

        detail.addActionListener(e -> {
            int idx = list.getSelectedIndex(); if(idx<0) return;
            Subject s = subjects.get(idx);
            new SubjectDetailView(controller, s, student).show();
        });
        reg.addActionListener(e -> {
            int idx = list.getSelectedIndex(); if(idx<0) return;
            Subject s = subjects.get(idx);
            String res = controller.registerStudentTo(student.id, s.id);
            if("OK".equals(res)){
                JOptionPane.showMessageDialog(frame, "Registration successful");
                frame.setVisible(false);
                // go back to profile
                new StudentProfileView(controller, student).show();
            } else {
                JOptionPane.showMessageDialog(frame, "Cannot register: " + res);
            }
        });
        back.addActionListener(e -> {
            frame.setVisible(false);
            new StudentProfileView(controller, student).show();
        });
    }

    public void show(){ frame.setVisible(true); }
}
