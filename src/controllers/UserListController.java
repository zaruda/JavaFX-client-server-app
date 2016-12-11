package controllers;

import Tables.CarTax;
import Tables.User;
import com.jfoenix.controls.JFXButton;
import database.DBWorker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.jws.soap.SOAPBinding;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * Created by zarud on 07.12.2016.
 */
public class UserListController implements Initializable {
    @FXML
    public JFXButton blockButton;
    @FXML
    private TableView<User> usersList;

    @FXML
    private TableColumn<User, String> idCol;

    @FXML
    private TableColumn<User, String> nameCol;

    @FXML
    private TableColumn<User, String> surnameCol;

    @FXML
    private TableColumn<User, String> emailCol;

    @FXML
    private TableColumn<User, String> loginCol;

    DBWorker db= DBWorker.getInstance();
    private ObservableList<User> dataList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataList.clear();
        try {
            ResultSet rs = db.getConnection().createStatement().executeQuery("SELECT id, name,surname,email,login FROM user");
            while (rs.next()) {
                dataList.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5)));
            }
        }catch (Exception e){
            e.getMessage();
        }
        usersList.setItems(dataList);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("userSurname"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        loginCol.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
    }

}
