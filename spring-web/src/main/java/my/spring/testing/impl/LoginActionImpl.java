package my.spring.testing.impl;

import my.spring.testing.LoginAction;

public class LoginActionImpl implements LoginAction {

    private String user;
    private String pswd;

    public LoginActionImpl( String user, String pswd ) {
        this.user = user;
        this.pswd = pswd;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getPswd() {
        return pswd;
    }
}
