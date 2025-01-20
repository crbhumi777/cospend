package dto;

public class User {
    private String userId;
    private String name;
    private String email;
    private String mobileNumber;

    public User(String userId, String name, String email, String mobileNumber) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
    }
}
