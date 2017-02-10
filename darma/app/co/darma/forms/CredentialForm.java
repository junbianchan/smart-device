package co.darma.forms;

import play.data.validation.Constraints.Required;

public class CredentialForm {

    @Required
    public String email;


    @Required
    public String password;

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


