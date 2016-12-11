package controllers;

import Network.Client;
import Tables.Taxes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by zaruda on 09.12.2016.
 */
public class taxListController implements Initializable {

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
    Taxes taxChose;

    private Client connection = Client.getInstance();

    private void selectedTax(Taxes tax){
        if(tax!=null){
            taxChose=tax;
        }
    }
    public void deleteSelectedTax(ActionEvent actionEvent) {
        connection.sendMessage("DeleteTax");
        connection.sendObject(taxChose);
        if(connection.readMessage().equals("Good")){
            System.out.println("Tax deleted");
        }
        else{
            System.out.println("Error");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ArrayList<Taxes> dataList = (ArrayList<Taxes>) connection.readObject();
        ObservableList<Taxes> data = FXCollections.observableArrayList(dataList);

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("taxType"));
        summaryCol.setCellValueFactory(new PropertyValueFactory<>("summary"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        taxList.setItems(data);

        taxList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedTax(newValue));


    }


}
