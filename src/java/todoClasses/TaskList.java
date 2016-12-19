package todoClasses;

import java.util.ArrayList;
import java.util.Date;

public class TaskList {
    private ArrayList<Integer> primaryKey;
    private ArrayList<String> title; 
    private ArrayList<Integer> priority;
    private ArrayList<Date> deadline;

    public ArrayList<Integer> getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(ArrayList<Integer> primaryKey) {
        this.primaryKey = primaryKey;
    }

    public ArrayList<Integer> getPriority() {
        return priority;
    }

    public void setPriority(ArrayList<Integer> priority) {
        this.priority = priority;
    }

    public ArrayList<Date> getDeadline() {
        return deadline;
    }

    public void setDeadline(ArrayList<Date> deadline) {
        this.deadline = deadline;
    }

    public ArrayList<String> getTitle() {
        return title;
    }

    public void setTitle(ArrayList<String> title) {
        this.title = title;
    }
}
