package view;
import controller.MainController;
import model.Student;
import model.Subject;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StudentProfileView {
    private final MainController controller;
    private final Student student;
    private JFrame frame;
    private JList<String> regList;

    public StudentProfileView(MainController c, Student s){ controller = c; student = s; init(); }

    private void init(){
        frame = new JFrame("Profile: " + student.getFullName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,400);
        JPanel top = new JPanel(new GridLayout(2,2));
        top.add(new JLabel("ID: " + student.id));
        top.add(new JLabel("Email: " + student.email));
        top.add(new JLabel("School: " + student.school));
        top.add(new JLabel("Age: " + student.getAge()));
        frame.getContentPane().add(top, BorderLayout.NORTH);

        regList = new JList<>();
        refreshRegs();
        frame.getContentPane().add(new JScrollPane(regList), BorderLayout.CENTER);

        JButton toReg = new JButton("Register for a subject");
        toReg.addActionListener(e -> {
            new RegistrationView(controller, student).show();
            frame.setVisible(false);
        });
        frame.getContentPane().add(toReg, BorderLayout.SOUTH);
    }

    public void refreshRegs(){
        List<model.Registration> regs = controller.getRegistrationsOf(student.id);
        DefaultListModel<String> m = new DefaultListModel<>();
        for(var r: regs){
            Subject s = controller.getSubject(r.subjectId);
            m.addElement(r.subjectId + " - " + (s==null? "Unknown": s.name) + " | Grade: " + (r.grade==null||r.grade.isEmpty()?"-":r.grade));
        }
        regList.setModel(m);
    }

    public void show(){ frame.setVisible(true); }
}
