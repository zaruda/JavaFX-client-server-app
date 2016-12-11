package controllers;

import Network.Client;
import Tables.Taxes;
import Tables.User;
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
import java.io.Serializable;
import java.net.URL;
import java.sql.ResultSet;
import java.util.*;


public class sidePanelController{

    @FXML
    private TableView<Taxes> taxList;

    @FXML
    private TableColumn<Taxes, String> nameCol;

    @FXML
    private TableColumn<Taxes, String> surnameCol;

    @FXML
    private TableColumn<Taxes, String> typeCol;

    @FXML
    private TableColumn<Taxes, String> summaryCol;

    @FXML
    private TableColumn<Taxes,String> dateCol;

    @FXML
    private JFXButton b1;
    @FXML
    private JFXButton b2;
    @FXML
    private JFXButton b3;
    @FXML
    private JFXButton exit;
    DBWorker db= DBWorker.getInstance();
    private Client connection = Client.getInstance();
    private ObservableList<Taxes> dataList = FXCollections.observableArrayList();

    private void tableCreator(){
        taxList.setItems(dataList);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("taxType"));
        summaryCol.setCellValueFactory(new PropertyValueFactory<>("summary"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

    }
    @FXML
    private void changeColor(ActionEvent event) {
        JFXButton btn = (JFXButton) event.getSource();

        switch(btn.getText())
        {
            case "Найти налоги":
            {
                TextInputDialog dialog = new TextInputDialog("");
                dialog.setTitle("Поиск таможенных сборов");
                dialog.setHeaderText("Поиск сборов");
                dialog.setContentText("Пожалуйста, введите тип сбора");

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()){
                    connection.sendMessage("FindTax");
                    connection.sendObject(result.get());
                    try {
                        ResultSet rs = db.getConnection().createStatement().executeQuery("select user.name,user.surname, taxtype.taxName, result.summary from result inner join user on result.userid = user.id inner join taxtype on result.result_id = taxtype.id where taxtype.taxName = '" + result.get() + "'");
                        while (rs.next()) {
                            dataList.add(new Taxes(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                        }

                    }catch (Exception e){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Ошибка");
                        alert.setHeaderText("Произошла ошибка!");
                        alert.setContentText("Неудалось выполнить операцию!\nПопробуйте повторить позже.");
                        alert.showAndWait();
                    }
                    try{
                        Stage stage = new Stage();
                        stage.getIcons().add(new Image("Icons/System-icon.png"));
                        Parent root = FXMLLoader.load(getClass().getResource("/fxml/taxTable.fxml"));
                        stage.setTitle("Результат поиска: История таможенных сборов");
                        stage.setResizable(false);
                        stage.setScene(new Scene(root));
                        stage.show();
                        stage.setOnShown(new EventHandler<WindowEvent>() {
                            @Override
                            public void handle(WindowEvent event) {
                                tableCreator();
                            }
                        });

                    }catch (IOException e){
                        e.printStackTrace();
                    }
;
                }

            }
                break;
            case "Просмотреть всю историю":
            {

                    connection.sendMessage("ShowHistory");
                    ArrayList<Taxes> dataList = (ArrayList<Taxes>) connection.readObject();
                    ObservableList<Taxes> data = FXCollections.observableArrayList(dataList);
                    for(Taxes taxes : dataList){

                    }
                System.out.println("dataList: " +dataList);

            try {
                Stage stage = new Stage();
                stage.getIcons().add(new Image("Icons/System-icon.png"));
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/taxTable.fxml"));
                stage.setTitle("Регистрация");
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.show();
                stage.setOnShown(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {

                        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
                        typeCol.setCellValueFactory(new PropertyValueFactory<>("taxType"));
                        summaryCol.setCellValueFactory(new PropertyValueFactory<>("summary"));
                        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
                        taxList.setItems(data);
                    }
                });


            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
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

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    connection.sendMessage("DeleteRecords");
                    if(connection.readMessage().equals("Good")){
                        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                        infoAlert.setTitle("Уведомление");
                        infoAlert.setHeaderText(null);
                        infoAlert.setContentText("Операция прошла успешно! Ваши данные удалены из системы.\nПосле закрытия окна, приложение будет закрыто.");
                        infoAlert.showAndWait();
                    }
                    System.exit(0);

                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            }
                break;
        }
    }

    @FXML
    private void exit(ActionEvent event) {
        //connection.sendMessage("Shutdown");
        System.exit(0);
    }


}

