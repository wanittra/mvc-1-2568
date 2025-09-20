package view;
import controller.MainController;
import model.Subject;
import model.Student;
import javax.swing.*;
import java.awt.*;
public class SubjectDetailView {
    private final MainController controller;
    private final Subject subject;
    private final Student student;
    private JFrame frame;

    public SubjectDetailView(MainController c, Subject s, Student student){ controller = c; subject = s; this.student = student; init(); }

    private void init(){
        frame = new JFrame("Subject: " + subject.name);
        frame.setSize(400,250);
        frame.setLayout(new GridLayout(6,1));
        frame.add(new JLabel("ID: " + subject.id));
        frame.add(new JLabel("Name: " + subject.name));
        frame.add(new JLabel("Credits: " + subject.credits));
        frame.add(new JLabel("Instructor: " + subject.instructor));
        frame.add(new JLabel("Capacity: " + (subject.maxCapacity==-1? "No limit" : subject.maxCapacity) + " | Registered: " + subject.getCurrentRegistered()));
        frame.add(new JLabel("Prerequisite: " + (subject.prereqId==null||subject.prereqId.isEmpty()? "None": subject.prereqId)));
    }

    public void show(){ frame.setVisible(true); }
}
