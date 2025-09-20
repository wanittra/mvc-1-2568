package model;
public class Registration {
    public final String studentId;
    public final String subjectId;
    public String status; // "registered" or "dropped"
    public String grade; // nullable

    public Registration(String studentId, String subjectId, String status, String grade) {
        this.studentId = studentId; this.subjectId = subjectId; this.status = status; this.grade = grade;
    }
}
