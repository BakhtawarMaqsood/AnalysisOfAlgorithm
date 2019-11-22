package uet.se.emailsearching;

public class SignUpData {

    public String email;
    public String password;

    public SignUpData() {
    }

    public SignUpData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
