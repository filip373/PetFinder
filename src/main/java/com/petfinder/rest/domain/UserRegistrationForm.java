package com.petfinder.rest.domain;

public class UserRegistrationForm {

    private String login;
    private String email;
    private String password;
    private String repeatPassword;

    public UserRegistrationForm() {}

    public UserRegistrationForm(String login, String email, String password, String repeatPassword) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
