package controllers;

import Network.Client;
import Tables.Parameters;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import database.DBWorker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import sun.misc.Cleaner;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.Random;
import java.util.ResourceBundle;


public class Controller implements Initializable{

    @FXML
    public JFXDrawer menu;
    @FXML
    public JFXHamburger hamburger;

    //Car Info
    @FXML
    private JFXTextField carAge;
    @FXML
    private JFXTextField engineSize;
    @FXML
    private JFXTextField carValue;
    private int carTaxCost=0;


    //Goods Info
    @FXML
    private DatePicker goodsDate;
    @FXML
    private JFXTextField goodsWeight;
    @FXML
    private JFXTextField goodsCost;
    private double goodsTaxValue=0;

    //Money Info
    @FXML
    private JFXToggleButton toggleType;
    @FXML
    private JFXTextField moneyAmount;
    @FXML
    private DatePicker moneyDate;


    DBWorker db = DBWorker.getInstance();

    private Client connection = Client.getInstance();


    private int summaryCarTax(int age, int engine, int value){
        if (age <= 3) {
            if (value <= 8500) {
                carTaxCost = (int) (value * 0.54);
                if (carTaxCost < engine * 2.5) {
                    carTaxCost = (int) (engine * 2.5);
                }
            }
            if (value > 8500 && value <= 16700) {
                carTaxCost = (int) (value * 0.48);
                if (carTaxCost < engine * 3.5) {
                    carTaxCost = (int) (engine * 2.5);
                }
            }
            if (value > 16700 && value <= 42300) {
                carTaxCost = (int) (value * 0.48);
                if (carTaxCost < engine * 5.5) {
                    carTaxCost = (int) (engine * 2.5);
                }
            }
            if (value > 42300 && value <= 84500) {
                carTaxCost = (int) (value * 0.48);
                if (carTaxCost < engine * 7.5) {
                    carTaxCost = (int) (engine * 2.5);
                }
            }
            if (value > 84500 && value <= 169000) {
                carTaxCost = (int) (value * 0.48);
                if (carTaxCost < engine * 15) {
                    carTaxCost = (int) (engine * 2.5);
                }
            }
            if (value > 169000) {
                carTaxCost = (int) (value * 0.48);
                if (carTaxCost < engine * 20) {
                    carTaxCost = (int) (engine * 2.5);
                }
            }
        }

        if (age > 3 && age <= 5) {
            if (engine <= 1000) {
                carTaxCost = (int) (engine * 1.5);
            }
            if (engine > 1000 && engine <= 1500) {
                carTaxCost = (int) (engine * 1.7);
            }
            if (engine > 1500 && engine <= 1800) {
                carTaxCost = (int) (engine * 2.5);
            }
            if (engine > 1800 && engine <= 2300) {
                carTaxCost = (int) (engine * 2.7);
            }
            if (engine > 2300 && engine <= 3000) {
                carTaxCost = engine * 3;
            }
            if (engine > 3000) {
                carTaxCost = (int) (engine * 3.6);
            }

        }
        if (age > 5) {
            if (engine <= 1000) {
                carTaxCost = (engine * 3);
            }
            if (engine > 1000 && engine <= 1500) {
                carTaxCost = (int) (engine * 3.2);
            }
            if (engine > 1500 && engine <= 1800) {
                carTaxCost = (int) (engine * 3.5);
            }
            if (engine > 1800 && engine <= 2300) {
                carTaxCost = (int) (engine * 4.8);
            }
            if (engine > 2300 && engine <= 3000) {
                carTaxCost = engine * 5;
            }
            if (engine > 3000) {
                carTaxCost = (int) (engine * 5.7);
            }
        }
        return carTaxCost;
    }

    public void calculateCarTax(ActionEvent actionEvent) {

        connection.sendMessage("CarTax");
        int age = Integer.parseInt(carAge.getText());
        int engine = Integer.parseInt(engineSize.getText());
        int value = Integer.parseInt(carValue.getText());
        Parameters params = new Parameters(age,engine,value);
        connection.sendObject(params);
        connection.sendObject(summaryCarTax(age,engine,value));
        //db.insertCarTax(summaryCarTax(age,engine,value),age,engine,value);
        if(connection.readMessage().equals("Good")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Информация");
            alert.setHeaderText("Таможенная пошлина для вашего\n автомобиля посчитана!");
            alert.setContentText("Вам требуется оплатить\n" + String.valueOf(carTaxCost) + "€");
            alert.showAndWait();
        }else {
            System.out.println("owibka");
        }
    }

    public void calculateGoodsTax(ActionEvent actionEvent) {

        connection.sendMessage("GoodsTax");
        double goodsValue = Double.parseDouble(goodsCost.getText());
        double weight= Double.parseDouble(goodsWeight.getText());
        Parameters parameters = new Parameters(goodsValue,weight,goodsDate.getValue());
        connection.sendObject(parameters);

        if(goodsValue>22 && weight<10){
            goodsTaxValue = ((goodsValue-22)*0.3)+5;
        }
        if(weight>10){
            goodsTaxValue = weight*4;
        }

        connection.sendObject(goodsTaxValue);
        if(connection.readMessage().equals("Good")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Информация");
            alert.setHeaderText("Таможенная пошлина для вашего\n приобретенного товара расчитана!");
            alert.setContentText("Вам требуется оплатить\n" + String.valueOf(goodsTaxValue) + "€");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Произошла ошибка!");
            alert.setContentText("Неудалось выполнить операцию!\nПопробуйте повторить позже.");
            alert.showAndWait();
        }
    }

    public void calculateMoneyTax(ActionEvent actionEvent) {
        connection.sendMessage("MoneyTax");

        int moneySum = Integer.parseInt(moneyAmount.getText());
        Parameters parameters=new Parameters(moneySum,moneyDate.getValue());
        connection.sendObject(parameters);
        if(connection.readMessage().equals("Good")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Информация");
            alert.setHeaderText("Операция прошла успешно!");
            alert.setContentText("Ваша денежная сумма задекларирована!");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Произошла ошибка!");
            alert.setContentText("Неудалось выполнить операцию!\nПопробуйте повторить позже.");
            alert.showAndWait();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            VBox box = FXMLLoader.load(getClass().getResource("../fxml/sidePanel.fxml"));
            menu.setSidePane(box);
        }catch (IOException e ){
            e.printStackTrace();
        }

        HamburgerBackArrowBasicTransition burgerTask2 = new HamburgerBackArrowBasicTransition(hamburger);
        burgerTask2.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, event->{
            burgerTask2.setRate(burgerTask2.getRate()*-1);
            burgerTask2.play();
            if(menu.isShown()){
                menu.close();
            }else{
                menu.open();
            }
        });
    }

    public void showUserProfile(MouseEvent mouseEvent) {
        try{
            Stage stage = new Stage();
            stage.getIcons().add(new Image("Icons/System-icon.png"));
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/userProfile.fxml"));
            stage.setTitle("Профиль пользователя");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
