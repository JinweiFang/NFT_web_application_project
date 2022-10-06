package Domain;

public class User {
    private int id, isAdmin;
    private String fName, lName, email, username, password, secAns1, secAns2, secAns3;
    private double balance;

    public User(){}

    public User(int id, String fName, String lName, String email, String username, String password, double balance, int isAdmin) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.isAdmin = isAdmin;
    }

    public User(int id, String fName, String lName, String email, String username, String password) {
        this(id, fName, lName, email, username, password, 0, 0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isAdmin(){
        return getIsAdmin() == 1;
    }

    public void setSecAnswers(String secAns1, String secAns2, String secAns3){
        this.secAns1 = secAns1;
        this.secAns2 = secAns2;
        this.secAns3 = secAns3;
    }

    public String getSecAns1() { return secAns1; }

    public String getSecAns2() { return secAns2; }

    public String getSecAns3() { return secAns3; }

}
