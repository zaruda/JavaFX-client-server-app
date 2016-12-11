package controllers;

import Network.Client;
import RegExValidators.EmailValidator;
import Tables.User;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;


public class RegistrationControl {


    public JFXTextField name;
    public JFXTextField surname;
    public JFXTextField email;
    public JFXTextField login;
    public JFXPasswordField password;

    private Client connection = Client.getInstance();
    private EmailValidator emailValidator = new EmailValidator();



    public void MakeRegistration(ActionEvent actionEvent) throws IOException {
        User newUser = new User(name.getText(),surname.getText(),email.getText(),login.getText(),password.getText());

        if(emailValidator.validate(email.getText())) {
            connection.sendMessage("SignIn");
            connection.sendObject(newUser);
            if(connection.readMessage().equals("Good")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Регистрация");
                alert.setHeaderText("Вы успешно прошли регистрацию");
                alert.setContentText(name.getText()+" "+ surname.getText()+ ", теперь Вы можете войти,\nиспользуя свой логин и пароль");
                alert.showAndWait();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.hide();
        }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка!");
                alert.setHeaderText("Заполните все поля");
                alert.setContentText("Произошла ошибка!\nНаверное, вы не заполнили некоторые поля\nили ввели некоректрый E-mail");
                alert.showAndWait();
        }
    }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка!");
            alert.setHeaderText("Заполните все поля");
            alert.setContentText("Произошла ошибка!\nНаверное, Вы ввели некоректрый E-mail");
            alert.showAndWait();
    }

    }
}
