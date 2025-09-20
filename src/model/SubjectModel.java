package model;
import java.nio.file.*;
import java.util.*;
public class SubjectModel {
    private final Path csv;
    private Map<String, Subject> subjects = new HashMap<>();
    public SubjectModel(Path csv){ this.csv = csv; load(); }

    public void load(){
        subjects.clear();
        try{
            List<String> lines = java.nio.file.Files.readAllLines(csv);
            for(String ln: lines){
                if(ln.trim().isEmpty()) continue;
                String[] a = ln.split(",", -1);
                String id=a[0], name=a[1], credits=a[2], instr=a[3], prereq=a[4].trim(), max=a[5], cur=a[6];
                subjects.put(id, new Subject(id, name, Integer.parseInt(credits), instr, prereq.equals("")?null:prereq, Integer.parseInt(max.trim()), Integer.parseInt(cur.trim())));
            }
        } catch(Exception e){ e.printStackTrace(); }
    }

    public Subject getById(String id){ return subjects.get(id); }
    public Collection<Subject> getAll(){ return subjects.values(); }

    // persist currentRegistered back to CSV (simple rewrite)
    public void save() {
        try {
            List<String> out = new ArrayList<>();
            for(Subject s : subjects.values()){
                out.add(String.join(",", s.id, s.name, String.valueOf(s.credits), s.instructor, s.prereqId.equals("")?"":s.prereqId, String.valueOf(s.maxCapacity), String.valueOf(s.getCurrentRegistered())));
            }
            java.nio.file.Files.write(csv, out);
        } catch(Exception e){ e.printStackTrace(); }
    }
}
