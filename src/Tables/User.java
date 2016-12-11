package Tables;

import controllers.UserListController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;


public class User implements Serializable {

    private int id;
    private String username;
    private String userSurname;
    private String email;
    private String login;
    private String password;

    public User(String username, String userSurname, String email, String login, String password) {
        this.username =username;
        this.userSurname =userSurname;
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public User(String username, String userSurname, String email, String login) {
        this.username = username;
        this.userSurname = userSurname;
        this.email = email;
        this.login = login;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(int id, String username,String userSurname,String email,String login){
        this.id = id;
        this.username=username;
        this.userSurname=userSurname;
        this.email=email;
        this.login=login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
