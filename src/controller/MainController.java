package controller;

import java.nio.file.*;
import java.util.*;
import model.*;

public class MainController {
    private final StudentModel studentModel;
    private final SubjectModel subjectModel;
    private final RegistrationModel regModel;
    private final AuthModel authModel;

    private view.LoginView loginView;
    private view.StudentProfileView profileView;
    private view.RegistrationView regView;
    private view.SubjectDetailView detailView;
    private view.AdminView adminView;

    public MainController(Path dataDir) {
        studentModel = new StudentModel(dataDir.resolve("students.csv"));
        subjectModel = new SubjectModel(dataDir.resolve("subjects.csv"));
        regModel = new RegistrationModel(dataDir.resolve("registrations.csv"));
        authModel = new AuthModel(dataDir.resolve("users.csv"));
    }

    public void start() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            loginView = new view.LoginView(this);
            loginView.show();
        });
    }

    // Authentication
    public void handleLogin(String username, String password) {
        try {
            AuthModel.UserRecord u = authModel.authenticate(username, password);
            if (u == null) {
                loginView.showError("Invalid credentials");
                return;
            }
            if ("admin".equals(u.role)) {
                loginView.close();
                adminView = new view.AdminView(this);
                adminView.show();
            } else {
                Student s = studentModel.getById(u.linkedStudentId);
                if (s == null) {
                    loginView.showError("Student record not found");
                    return;
                }
                loginView.close();
                profileView = new view.StudentProfileView(this, s);
                profileView.show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            loginView.showError("Error during auth");
        }
    }

    // Provide subjects that student has NOT registered yet
    public List<Subject> getAvailableSubjectsForStudent(String studentId) {
        List<Subject> out = new ArrayList<>();
        for (Subject s : subjectModel.getAll()) {
            if (!regModel.isStudentRegisteredFor(studentId, s.id))
                out.add(s);
        }
        return out;
    }

    // Register student to subject: checks prereq, age, capacity
    public String registerStudentTo(String studentId, String subjectId) {
        Student st = studentModel.getById(studentId);
        Subject subj = subjectModel.getById(subjectId);
        if (st == null || subj == null)
            return "Student or Subject not found.";
        // age check
        if (st.getAge() < 15)
            return "Student must be at least 15 years old.";
        // prereq check
        if (subj.prereqId != null && !subj.prereqId.isEmpty()) {
            // check if student has grade for prereq (simple: check registrations with grade
            // != empty and passing e.g. any grade)
            boolean hasPassed = false;
            for (Registration r : regModel.getByStudent(studentId)) {
                if (r.subjectId.equals(subj.prereqId) && r.grade != null && !r.grade.trim().isEmpty()) {
                    hasPassed = true;
                    break;
                }
            }
            if (!hasPassed)
                return "Prerequisite not satisfied: must have completed " + subj.prereqId;
        }
        // capacity check
        if (!subj.hasCapacity())
            return "Subject is full. Cannot register.";
        // all good: add registration, increment subject count and persist
        Registration newReg = new Registration(studentId, subjectId, "registered", "");
        boolean ok = regModel.addRegistration(newReg);
        if (!ok)
            return "Already registered.";
        subj.incrementRegistered();
        subjectModel.save();
        return "OK";
    }

    // other helper getters
    public Student getStudent(String id) {
        return studentModel.getById(id);
    }

    public Subject getSubject(String id) {
        return subjectModel.getById(id);
    }

    public List<Registration> getRegistrationsOf(String studentId) {
        return regModel.getByStudent(studentId);
    }

    // Admin: assign grade
    public void assignGrade(String studentId, String subjectId, String grade) {
        regModel.find(studentId, subjectId).ifPresent(r -> {
            r.grade = grade;
            regModel.persist();
        });
    }

    public Collection<Student> getAllStudents() {
        return studentModel.getAll();
    }

    public Collection<Subject> getAllSubjects() {
        return subjectModel.getAll();
    }

}
