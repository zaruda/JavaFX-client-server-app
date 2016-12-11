package controllers;

import Network.Client;
import Tables.Taxes;
import Tables.User;
import database.DBWorker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by zaruda on 08.12.2016.
 */
public class userProfileController implements Initializable{

    @FXML
    private Label firstName;

    @FXML
    private Label lastName;

    @FXML
    private Label email;

    @FXML
    private Label username;
    private Client connection = Client.getInstance();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection.sendMessage("MakeProfile");
        ArrayList<User> user = (ArrayList<User>) connection.readObject();

        for(User users : user){
            firstName.setText(users.getUsername());
            lastName.setText(users.getUserSurname());
            email.setText(users.getEmail());
            username.setText(users.getLogin());
        }
    }
}
