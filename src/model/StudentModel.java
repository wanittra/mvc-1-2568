package model;
import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.time.LocalDate;
public class StudentModel {
    private final Path csv;
    private Map<String, Student> students = new HashMap<>();
    public StudentModel(Path csv){ this.csv = csv; load(); }

    public void load(){
        students.clear();
        try{
            List<String> lines = Files.readAllLines(csv);
            for(String ln: lines){
                if(ln.trim().isEmpty()) continue;
                String[] a = ln.split(",", -1);
                String id=a[0], title=a[1], fn=a[2], lnname=a[3], dob=a[4], school=a[5], email=a[6];
                students.put(id, new Student(id,title,fn,lnname, LocalDate.parse(dob), school, email));
            }
        } catch(Exception e){ e.printStackTrace(); }
    }

    public Student getById(String id){ return students.get(id); }
    public Collection<Student> getAll(){ return students.values(); }
}
