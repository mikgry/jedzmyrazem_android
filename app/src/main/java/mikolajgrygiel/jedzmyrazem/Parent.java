package mikolajgrygiel.jedzmyrazem;

import java.util.ArrayList;

public class Parent
{
    private String startPlace;
    private String startTime;
    private String finishPlace;
    private String finishTime;
    private String passes;

    // ArrayList to store child objects
    private ArrayList<Child> children;

    public String getStartPlace()
    {
        return startPlace;
    }

   // ArrayList to store child objects
    public ArrayList<Child> getChildren()
    {
        return children;
    }

    public void setChildren(ArrayList<Child> children)
    {
        this.children = children;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishPlace() {
        return finishPlace;
    }

    public void setFinishPlace(String finishPlace) {
        this.finishPlace = finishPlace;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getPasses() {
        return passes;
    }

    public void setPasses(String passes) {
        this.passes = passes;
    }
}