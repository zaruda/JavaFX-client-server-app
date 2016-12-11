package controllers;

import Network.Client;
import Tables.CarTax;
import Tables.GoodsTax;
import Tables.MoneyTax;
import database.DBWorker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminPanelController implements Initializable{


    @FXML
    private TableView<CarTax> carTaxTable;

    @FXML
    private TableColumn<CarTax, String> idCol;

    @FXML
    private TableColumn<CarTax, String> nameCol;

    @FXML
    private TableColumn<CarTax, String> surnameCol;

    @FXML
    private TableColumn<CarTax, String> emailCol;

    @FXML
    private TableColumn<CarTax, String> carAgeCol;

    @FXML
    private TableColumn<CarTax, String> engineCol;

    @FXML
    private TableColumn<CarTax, String> valueCol;

    @FXML
    private TableColumn<CarTax, String> summaryCol;

    @FXML
    private TableView<GoodsTax> goodsTaxTable;

    @FXML
    private TableColumn<GoodsTax, String> goodsIdCol;

    @FXML
    private TableColumn<GoodsTax, String> goodsNameCol;

    @FXML
    private TableColumn<GoodsTax, String> goodsSurnameCol;

    @FXML
    private TableColumn<GoodsTax, String> goodsEmailCol;

    @FXML
    private TableColumn<GoodsTax, String> typeCol;

    @FXML
    private TableColumn<GoodsTax, String> goodsValueCol;

    @FXML
    private TableColumn<GoodsTax, String> dateCol;

    @FXML
    private TableColumn<GoodsTax, String> summaryGoodsCol;

    @FXML
    private TableView<MoneyTax> moneyTaxTable;
    @FXML
    private TableColumn<MoneyTax, String> moneyIdCol;

    @FXML
    private TableColumn<MoneyTax, String> moneyNameCol;

    @FXML
    private TableColumn<MoneyTax, String> moneySurnameCol;

    @FXML
    private TableColumn<MoneyTax, String> moneyEmailCol;

    @FXML
    private TableColumn<MoneyTax, String> moneyValueCol;

    @FXML
    private TableColumn<MoneyTax, String> moneyDateCol;

    @FXML
    private TableColumn<MoneyTax, String> moneySummaryCol;

    @FXML
    private PieChart pieChart;
    @FXML
    public BarChart barChart;

    private ObservableList<CarTax> carsData = FXCollections.observableArrayList();
    private ObservableList<GoodsTax> goodsData = FXCollections.observableArrayList();
    private ObservableList<MoneyTax> moneyData = FXCollections.observableArrayList();
    private DBWorker db = DBWorker.getInstance();

    private void columnsImport(){

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        carAgeCol.setCellValueFactory(new PropertyValueFactory<>("ageOfCar"));
        engineCol.setCellValueFactory(new PropertyValueFactory<>("engine"));
        valueCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        summaryCol.setCellValueFactory(new PropertyValueFactory<>("total"));

        carTaxTable.setItems(carsData);

        goodsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        goodsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        goodsSurnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        goodsEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        goodsValueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        summaryGoodsCol.setCellValueFactory(new PropertyValueFactory<>("total"));

        goodsTaxTable.setItems(goodsData);

        moneyIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        moneyNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        moneySurnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        moneyEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        moneyValueCol.setCellValueFactory(new PropertyValueFactory<>("moneyValue"));
        moneyDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        moneySummaryCol.setCellValueFactory(new PropertyValueFactory<>("total"));

        moneyTaxTable.setItems(moneyData);


    }

    public void loadData() {

        barChart.getData().clear();
        carsData.clear();
        goodsData.clear();
        moneyData.clear();

        try{
            ResultSet carRS = db.getConnection().createStatement().executeQuery("SELECT result.userid, user.name,user.surname, user.email,parameters.ageOfCar,parameters.engine,parameters.value ,result.summary FROM result inner join user on result.userid = user.id inner join parameters on result.result_id = parameters.id inner join taxtype on result.result_id=taxtype.id where taxtype.taxName = 'CarTax'");
            while(carRS.next()) {
                carsData.add(new CarTax(carRS.getString(1), carRS.getString(2), carRS.getString(3), carRS.getString(4), carRS.getString(5), carRS.getString(6), carRS.getString(7), carRS.getString(8)));
            }
            ResultSet goodsRS = db.getConnection().createStatement().executeQuery("SELECT result.userid,user.name,user.surname,user.email,parameters.weight,parameters.value,parameters.date, result.summary  FROM result inner join user on result.userid = user.id inner join parameters on result.result_id = parameters.id inner join taxtype on result.result_id=taxtype.id where taxtype.taxName = 'GoodsTax'");
            while (goodsRS.next()){
                goodsData.add(new GoodsTax(goodsRS.getString(1),goodsRS.getString(2),goodsRS.getString(3),goodsRS.getString(4),goodsRS.getString(5),goodsRS.getString(6),goodsRS.getString(7),goodsRS.getString(8)));
            }
            ResultSet moneyRS = db.getConnection().createStatement().executeQuery("SELECT result.userid,user.name,user.surname,user.email,parameters.value,parameters.date, parameters.id  FROM result inner join user on result.userid = user.id inner join parameters on result.result_id = parameters.id inner join taxtype on result.result_id=taxtype.id where taxtype.taxName = 'MoneyTax'");
            while (moneyRS.next()){
                moneyData.add(new MoneyTax(moneyRS.getString(1),moneyRS.getString(2),moneyRS.getString(3),moneyRS.getString(4),moneyRS.getString(5),moneyRS.getString(6),moneyRS.getString(7)));
            }
        }catch (SQLException ex){
            ex.getMessage();
        }
        columnsImport();


        ObservableList<PieChart.Data> list = FXCollections.observableArrayList(
                new PieChart.Data("Автомобили", db.carTaxesCount()),
                new PieChart.Data("Покупки", db.goodsTaxesCount()),
                new PieChart.Data("Денежные средства", db.moneyTaxesCount())
        );
        
        pieChart.setData(list);


         CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Тип сборов");
         NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Сумма");
        XYChart.Series dataSeries = new XYChart.Series();
        dataSeries.setName("Сумма оплаченных пошлин");

        dataSeries.getData().add(new XYChart.Data("Автомобли",db.carTaxSummary()));
        dataSeries.getData().add(new XYChart.Data("Покупки", db.goodsTaxSummary()));
        dataSeries.getData().add(new XYChart.Data("Денежные\nсредства", db.moneyTaxSummary()));

        barChart.getData().add(dataSeries);




    }

    public void exit(ActionEvent actionEvent) {
        try{
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.getIcons().add(new Image("Icons/System-icon.png"));
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/intro.fxml"));
            stage.setTitle("Калькулятор таможенных выплат");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void serverControl(ActionEvent actionEvent) {

        try{
            Stage stage = new Stage();
            stage.getIcons().add(new Image("Icons/System-icon.png"));
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/serverPanel.fxml"));
            stage.setTitle("Управление сервером");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void loadUsersList(ActionEvent actionEvent) {
        try{
            Stage stage = new Stage();
            stage.getIcons().add(new Image("Icons/System-icon.png"));
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/userList.fxml"));
            stage.setTitle("Список пользователей");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    public void exportToPdf(ActionEvent actionEvent) {
        if(db.saveDataToPdf()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Информация");
            alert.setHeaderText("Информация из базы данных успешно созхранена!");
            alert.setContentText("Файл PDF находится в корне папки!");
            alert.showAndWait();
        }
    }
}
