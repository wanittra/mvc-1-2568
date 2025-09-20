package model;
import java.nio.file.*;
import java.util.*;
import java.io.*;
public class RegistrationModel {
    private final Path csv;
    private List<Registration> regs = new ArrayList<>();
    public RegistrationModel(Path csv){ this.csv = csv; load(); }

    public void load(){
        regs.clear();
        try{
            List<String> lines = Files.readAllLines(csv);
            for(String ln: lines){
                if(ln.trim().isEmpty()) continue;
                String[] a = ln.split(",", -1);
                regs.add(new Registration(a[0], a[1], a[2], a.length>3? a[3] : ""));
            }
        } catch(Exception e){ e.printStackTrace(); }
    }

    public List<Registration> getByStudent(String studentId){
        List<Registration> r = new ArrayList<>();
        for(Registration reg: regs) if(reg.studentId.equals(studentId) && "registered".equals(reg.status)) r.add(reg);
        return r;
    }

    public boolean isStudentRegisteredFor(String studentId, String subjectId){
        for(Registration reg: regs) if(reg.studentId.equals(studentId) && reg.subjectId.equals(subjectId) && "registered".equals(reg.status)) return true;
        return false;
    }

    public Optional<Registration> find(String studentId, String subjectId){
        for(Registration reg: regs) if(reg.studentId.equals(studentId) && reg.subjectId.equals(subjectId)) return Optional.of(reg);
        return Optional.empty();
    }

    public boolean addRegistration(Registration r){
        // prevent duplicates
        if(isStudentRegisteredFor(r.studentId, r.subjectId)) return false;
        regs.add(r);
        persist();
        return true;
    }

    public void persist(){
        try {
            List<String> out = new ArrayList<>();
            for(Registration r: regs) out.add(String.join(",", r.studentId, r.subjectId, r.status, r.grade==null?"":r.grade));
            Files.write(csv, out);
        } catch(Exception e){ e.printStackTrace(); }
    }
}
