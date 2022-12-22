package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Password {

    private String password;
    @JsonIgnore
    private String super_secret = "super_secret";

    Password () {};

    public String getPassword() {
        return this.password;
    }
    public void setPassword(String pwd) {
        this.password  = pwd;
    }

    public String getSuper_secret() {
        return super_secret;
    }
}
