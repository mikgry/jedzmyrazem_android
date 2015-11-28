package mikolajgrygiel.jedzmyrazem;

public class Child
{
    private String startPlace;
    private String startTime;
    private String finishPlace;
    private String finishTime;
    private String driver;
    private String spaces;

    public String getStartPlace()
    {
        return startPlace;
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

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getSpaces() {
        return spaces;
    }

    public void setSpaces(String spaces) {
        this.spaces = spaces;
    }
}