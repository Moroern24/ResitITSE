package models;

public class User {
    private int userID;
    private int age;
    private String email;

    public User(int userID, int age, String email) {
        this.userID = userID;
        this.age = age;
        this.email = email;
    }

    public int getUserID() { return userID; }
    public int getAge() { return age; }
    public String getEmail() { return email; }
}
