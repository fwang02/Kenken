package Domain;

public class User {
    private String username;
    private String password;
    private int maxPoint;

    public User() {
        username = null;
        password = null;
        maxPoint = 0;
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        maxPoint = 0;
    }

    public User(String username, String password, int maxPoint) {
        this.username = username;
        this.password = password;
        this.maxPoint = maxPoint;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getMaxPoint() {
        return maxPoint;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMaxPoint(int point) {
        maxPoint = point;
    }
}
