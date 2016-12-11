package controllers;

import Network.Client;
import Tables.User;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;


public class LoginController{

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField pass;

    private Client connection = new Client();


    public void makeLogin(ActionEvent actionEvent) throws Exception {

        User user = new User(username.getText(),pass.getText());

        if(username.getText().equals("admin")&&pass.getText().equals("admin")){
            try {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.getIcons().add(new Image("Icons/System-icon.png"));
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminPanel.fxml"));
                stage.setTitle("Панель администратора");
                stage.setResizable(true);
                stage.setScene(new Scene(root));
                stage.show();
            }catch (IOException ex){
                ex.getMessage();
            }

        }else{

            connection.sendMessage("LogIn");
            connection.sendObject(user);
            if(connection.readMessage().equals("Good")){
                try {
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    stage.getIcons().add(new Image("Icons/System-icon.png"));
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainWindow.fxml"));
                    stage.setTitle("Калькулятор таможенных выплат");
                    stage.setResizable(false);
                    stage.setScene(new Scene(root));
                    stage.show();
                }catch (IOException ex){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText("Произошла ошибка!");
                    alert.setContentText("Неудалось выполнить операцию!\nПопробуйте повторить позже.");
                    alert.showAndWait();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Авторизация");
                alert.setHeaderText("Пользователь не найден!");
                alert.setContentText("Пользователь с именем "+ username.getText() +" ещё не зарегистрирован!");
                alert.showAndWait();
            }

        }

    }
}
