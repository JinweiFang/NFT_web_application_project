package Model;

public class Test {
    private int ID;
    private String name;
    private long createdOn = System.currentTimeMillis();

    public Test(String name) {
        this.name = name;
    }

    public Test(int ID, String name) {
        this(name);
        this.ID = ID;
    }

    public  Test(int ID, String name, long createdOn) {
        this(ID, name);
        this.createdOn = createdOn;
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
