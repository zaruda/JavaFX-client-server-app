package controllers;

import Network.Client;
import Tables.Taxes;
import database.DBWorker;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.*;


public class sidePanelController{
    @FXML
    private JFXButton b1;
    @FXML
    private JFXButton b2;
    @FXML
    private JFXButton b3;
    @FXML
    private JFXButton exit;
    private Client connection = Client.getInstance();

    @FXML
    private void changeColor(ActionEvent event) {
        JFXButton btn = (JFXButton) event.getSource();

        switch(btn.getText())
        {
            case "Найти налоги":
            {

                connection.sendMessage("FindTax");


                List<String> choices = new ArrayList<>();
                choices.add("Автомобили");
                choices.add("Покупки");
                choices.add("Денежные средства");

                ChoiceDialog<String> dialog = new ChoiceDialog<>("Выберите тип", choices);
                dialog.setTitle("Поиск таможенных сборов");
                dialog.setHeaderText("Поиск сборов");
                dialog.setContentText("Пожалуйста, введите тип сбора");

                Stage stage2 = (Stage) dialog.getDialogPane().getScene().getWindow();
                stage2.getIcons().add(new Image(this.getClass().getResource("/Icons/System-icon.png").toString()));

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()){
                    if(result.get().equals("Автомобили"))
                        connection.sendObject("CarTax");
                    if(result.get().equals("Покупки"))
                        connection.sendObject("MoneyTax");
                    if(result.get().equals("Денежные средства"))
                        connection.sendObject("GoodsTax");
                }
                    try{
                        Stage stage = new Stage();
                        stage.getIcons().add(new Image("Icons/System-icon.png"));
                        Parent root = FXMLLoader.load(getClass().getResource("/fxml/taxTable.fxml"));
                        stage.setTitle("Результат поиска: История таможенных сборов");
                        stage.setResizable(false);
                        stage.setScene(new Scene(root));
                        stage.show();

                    }catch (IOException e){
                        e.printStackTrace();
                    }


            }break;
            case "Просмотреть всю историю":
            {
                connection.sendMessage("ShowHistory");
            try {
                Stage stage = new Stage();
                stage.getIcons().add(new Image("Icons/System-icon.png"));
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/taxTable.fxml"));
                stage.setTitle("История таможенных платежей");
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.show();

            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                Stage stage2 = (Stage) alert.getDialogPane().getScene().getWindow();
                stage2.getIcons().add(new Image(this.getClass().getResource("/Icons/System-icon.png").toString()));

                alert.setTitle("Ошибка");
                alert.setHeaderText("Произошла ошибка!");
                alert.setContentText("Неудалось выполнить операцию!\nПопробуйте повторить позже.");
                alert.showAndWait();
            }
            }
                break;
            case "Удалить учетную запись":
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Удаление записи");
                alert.setHeaderText("Удаление учетной записи пользователя");
                alert.setContentText("Вы уверены, что хотите удалить\nВашу учетную запись?");

                Stage stage2 = (Stage) alert.getDialogPane().getScene().getWindow();
                stage2.getIcons().add(new Image(this.getClass().getResource("/Icons/System-icon.png").toString()));

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    connection.sendMessage("DeleteRecords");
                    if(connection.readMessage().equals("Good")){
                        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);

                        Stage stage3 = (Stage) infoAlert.getDialogPane().getScene().getWindow();
                        stage3.getIcons().add(new Image(this.getClass().getResource("/Icons/System-icon.png").toString()));
                        infoAlert.setTitle("Уведомление");
                        infoAlert.setHeaderText(null);
                        infoAlert.setContentText("Операция прошла успешно! Ваши данные удалены из системы.\nПосле закрытия окна, приложение будет закрыто.");
                        infoAlert.showAndWait();
                    }
                    System.exit(0);

                } else {
                }
            }
                break;
        }
    }

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }

}

