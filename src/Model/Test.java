package Model;

public class Test {
    private int ID;
    private String name;
    private final long createdOn = System.currentTimeMillis();

    public Test(int ID) {
        this.ID = ID;
    }

    public Test(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setName(String name) {
        this.name = name;
    }
}
