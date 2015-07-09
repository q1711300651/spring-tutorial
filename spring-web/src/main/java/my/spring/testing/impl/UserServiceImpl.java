package my.spring.testing.impl;

import my.spring.testing.LoginAction;
import my.spring.testing.UserPreferences;
import my.spring.testing.UserService;

public class UserServiceImpl implements UserService {

    private LoginAction loginAction;
    private UserPreferences preferences;


    public UserServiceImpl( LoginAction loginAction, UserPreferences preferences ) {
        this.loginAction = loginAction;
        this.preferences = preferences;
    }

    @Override
    public String loginUser() {
        return "user " + loginAction.getUser() + " has login with password " + loginAction.getPswd();
    }

    public UserPreferences preferences() {
        return preferences;
    }
}
