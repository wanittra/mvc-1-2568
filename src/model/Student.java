package model;
import java.time.LocalDate;
public class Student {
    public final String id;
    public final String title;
    public final String firstName;
    public final String lastName;
    public final LocalDate dob;
    public final String school;
    public final String email;

    public Student(String id, String title, String firstName, String lastName, LocalDate dob, String school, String email) {
        this.id = id; this.title = title; this.firstName = firstName; this.lastName = lastName;
        this.dob = dob; this.school = school; this.email = email;
    }

    public int getAge() {
        return java.time.Period.between(dob, LocalDate.now()).getYears();
    }

    public String getFullName() { return title + " " + firstName + " " + lastName; }
}
