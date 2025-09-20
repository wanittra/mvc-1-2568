package model;
import java.nio.file.*;
import java.io.*;
import java.util.*;
public class AuthModel {
    private final Path usersFile;
    public AuthModel(Path usersFile){ this.usersFile = usersFile; }

    public static class UserRecord { public final String username, role, linkedStudentId; public UserRecord(String u,String r,String l){username=u;role=r;linkedStudentId=l;} }

    public UserRecord authenticate(String username, String password) throws IOException{
        List<String> lines = Files.readAllLines(usersFile);
        for(String ln: lines){
            String[] a = ln.split(",", -1);
            if(a.length >= 3){
                String u=a[0], p=a[1], r=a[2], linked = a.length>3 ? a[3] : "";
                if(u.equals(username) && p.equals(password)) return new UserRecord(u, r, linked);
            }
        }
        return null;
    }
}
