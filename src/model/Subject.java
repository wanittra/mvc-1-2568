package model;
public class Subject {
    public final String id;
    public final String name;
    public final int credits;
    public final String instructor;
    public final String prereqId; // empty if none
    public final int maxCapacity; // -1 for no limit
    private int currentRegistered;

    public Subject(String id, String name, int credits, String instructor, String prereqId, int maxCapacity, int currentRegistered) {
        this.id = id; this.name = name; this.credits = credits; this.instructor = instructor;
        this.prereqId = (prereqId==null?"":prereqId);
        this.maxCapacity = maxCapacity;
        this.currentRegistered = Math.max(0, currentRegistered);
    }

    public int getCurrentRegistered() { return currentRegistered; }
    public void incrementRegistered(){ currentRegistered++; }
    public void decrementRegistered(){ if (currentRegistered>0) currentRegistered--; }
    public boolean hasCapacity(){
        return maxCapacity == -1 || currentRegistered < maxCapacity;
    }
}
