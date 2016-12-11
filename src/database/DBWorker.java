package database;

import Tables.Taxes;
import Tables.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class DBWorker implements databaseInterface {
    private static DBWorker db = null;
    private static final String url = "jdbc:mysql://localhost:3306/database2?autoReconnect=true&useSSL=false";
    private static final String user = "root";
    private static final String password = "root";
    private PreparedStatement preparedStatement;
    private Connection connection;
    private int userid = 0;
    private boolean flag = false;
    private int taxCount = 0;
    private static ArrayList<Taxes> taxList;
    private static ArrayList<User> userInfo;

    public Connection getConnection() {
        return connection;
    }

    private DBWorker() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            System.out.println("Неудалось загрузить драйвер");
        }
    }

    public static DBWorker getInstance() {
        if (db == null) {
            db = new DBWorker();
        }
        return db;
    }

    public boolean logIN(String login, String pass) {
        flag=false;
        try {
            preparedStatement = connection.prepareStatement("SELECT id FROM user WHERE (login,password)= (?,md5(?))");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, pass);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                userid = rs.getInt("id");
                flag = true;
            }

        } catch (SQLException e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    public boolean RegisterNewUser(String name, String surname, String email, String login, String pass) {
        try {

            preparedStatement = connection.prepareStatement("INSERT INTO user(name, surname, email, login,password) VALUES (?,?,?,?,md5(?))");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, login);
            preparedStatement.setString(5, pass);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();

            return false;

        }
    }

    public ArrayList<User> userProfileInfo(String login){
        userInfo = new ArrayList<>();
        try{
            preparedStatement = connection.prepareStatement("SELECT user.name,user.surname,user.email,user.login from user where user.login = (?)");
            preparedStatement.setString(1,login);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                userInfo.add(new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return userInfo;
    }

    public boolean insertCarTax(String type, int ageOfCar, int engineType, double carPrice, int totalCost) {
        try {

            PreparedStatement prep = connection.prepareStatement("INSERT INTO taxtype(taxName) VALUES (?)");
            prep.setString(1,type);
            prep.executeUpdate();

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO result(summary, userid) VALUES (?,?)");
            preparedStatement.setInt(1, totalCost);
            preparedStatement.setInt(2, userid);
            preparedStatement.executeUpdate();

            int taxID = 0;
            ResultSet rs = preparedStatement.executeQuery("SELECT  last_insert_id() AS id FROM result");
            if (rs.next()) {
                taxID = rs.getInt("id");
            }
            PreparedStatement statement = connection.prepareStatement("INSERT INTO parameters(id,ageOfCar,engine,value) VALUES (?,?,?,?)");
            statement.setInt(1, taxID);
            statement.setInt(2, ageOfCar);
            statement.setInt(3, engineType);
            statement.setDouble(4, carPrice);
            statement.executeUpdate();
            flag=true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            flag=false;
        }
        return flag;
    }

    public boolean insertGoodsTax(String type,double goodsValue, double weight, double goodsTaxValue, LocalDate date) {
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO taxtype(taxName) VALUES (?)");
            preparedStatement.setString(1,type);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("INSERT INTO result(summary, userid) VALUES (?,?)");
            preparedStatement.setDouble(1, goodsTaxValue);
            preparedStatement.setInt(2, userid);
            preparedStatement.executeUpdate();

            int taxID = 0;
            ResultSet rs = preparedStatement.executeQuery("SELECT  last_insert_id() AS id FROM result");
            if (rs.next()) {
                taxID = rs.getInt("id");
            }
            preparedStatement = connection.prepareStatement("INSERT INTO parameters(id,weight,value,date) VALUES (?,?,?,?)");
            preparedStatement.setInt(1, taxID);
            preparedStatement.setDouble(2, weight);
            preparedStatement.setDouble(3, goodsValue);
            Date goodsDate = Date.valueOf(date);
            preparedStatement.setDate(4, goodsDate);
            preparedStatement.executeUpdate();
            flag=true;

        } catch (SQLException ex) {
            flag=false;
            ex.printStackTrace();
        }
        return flag;
    }

    public boolean insertMoneyTax(String type,double moneySum, LocalDate date) {
        try {

            preparedStatement = connection.prepareStatement("INSERT INTO taxtype(taxName) VALUES (?)");
            preparedStatement.setString(1,type);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("INSERT INTO result(summary, userid) VALUES (?,?)");
            preparedStatement.setDouble(1, moneySum);
            preparedStatement.setInt(2, userid);
            preparedStatement.executeUpdate();

            int taxID;
            ResultSet rs = preparedStatement.executeQuery("SELECT  last_insert_id() AS id FROM result");
            if (rs.next()) {
                taxID = rs.getInt("id");
            }
            preparedStatement = connection.prepareStatement("INSERT INTO parameters(value,date) VALUES (?,?)");
            preparedStatement.setDouble(1, moneySum);
            Date mDate = Date.valueOf(date);
            preparedStatement.setDate(2, mDate);
            preparedStatement.executeUpdate();
            flag=true;
        } catch (SQLException ex) {
            flag=false;
            ex.printStackTrace();
        }
        return flag;
    }

    public int carTaxesCount() {
        try {
            preparedStatement = connection.prepareStatement("SELECT count(*) FROM taxtype WHERE taxName='CarTax'");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                taxCount = rs.getInt("count(*)");
                flag = true;
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
        return taxCount;
    }

    public int moneyTaxesCount() {
        try {
            preparedStatement = connection.prepareStatement("SELECT count(*) FROM taxtype WHERE taxName='MoneyTax'");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                taxCount = rs.getInt("count(*)");
                flag = true;
            }

        } catch (SQLException ex) {
            ex.getMessage();
        }
        return taxCount;
    }

    public int goodsTaxesCount() {
        try {
            preparedStatement = connection.prepareStatement("SELECT count(*) FROM taxtype WHERE taxName='GoodsTax'");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                taxCount = rs.getInt("count(*)");
                flag = true;
            }

        } catch (SQLException ex) {
            ex.getMessage();
        }
        return taxCount;
    }
    public int carTaxSummary(){
        int summary=0;
        try{
            ResultSet rs = preparedStatement.executeQuery("SELECT sum(summary) from result inner join taxtype on result.result_id = taxtype.id where taxtype.taxName='CarTax'");
            while (rs.next()){
                summary=rs.getInt("sum(summary)");
            }
        }catch (Exception ex){
            ex.getMessage();
        }
        return summary;
    }
    public int goodsTaxSummary(){
        int summary=0;
        try{
            ResultSet rs = preparedStatement.executeQuery("SELECT sum(summary) from result inner join taxtype on result.result_id = taxtype.id where taxtype.taxName='GoodsTax'");
            while (rs.next()){
                summary=rs.getInt("sum(summary)");
            }
        }catch (Exception ex){
            ex.getMessage();
        }
        return summary;
    }
    public int moneyTaxSummary(){
        int summary=0;
        try{
            ResultSet rs = preparedStatement.executeQuery("SELECT sum(summary) from result inner join taxtype on result.result_id = taxtype.id where taxtype.taxName='MoneyTax'");
            while (rs.next()){
                summary=rs.getInt("sum(summary)");
            }
        }catch (Exception ex){
            ex.getMessage();
        }
        return summary;
    }

    public ObservableList findTax(String surname) {
        ObservableList<Taxes> dataList = FXCollections.observableArrayList();
        try {
            preparedStatement = connection.prepareStatement("select user.name,user.surname, taxtype.taxName, result.summary from result inner join user on result.userid = user.id inner join taxtype on result.result_id = taxtype.id where user.surname = (?)");
            preparedStatement.setString(1, surname);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                dataList.add(new Taxes(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                    }
        }catch (Exception e){
            e.getMessage();
        }
        return dataList;

                /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Информация");
                alert.setHeaderText("Результаты найдены!");
                alert.setContentText("Имя: " + row.get(0) + "\nФамилия: " + row.get(1) + "\nТип налога: " + row.get(2)+"\nСумма: "+row.get(3));*/

                //alert.showAndWait();

    }


    public boolean deleteRecord(String login) {
        flag = false;
        try{
            preparedStatement = connection.prepareStatement("DELETE from user WHERE login=(?)");
            preparedStatement.setString(1,login);
            preparedStatement.executeUpdate();
            flag = true;
        }catch (Exception e){
            flag=false;
            e.getMessage();
        }
        return flag;
    }

    public ArrayList<Taxes> showHistory(String login){
        taxList=new ArrayList<>();
        try{
            preparedStatement = connection.prepareStatement("select user.name,user.surname, taxtype.taxName, result.summary, parameters.date from result inner join user on result.userid = user.id inner join taxtype on result.result_id = taxtype.id INNER join parameters on result_id=parameters.id WHERE user.login = (?)");
            preparedStatement.setString(1,login);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                taxList.add(new Taxes(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
            }
        }catch (Exception e){
            e.getMessage();
        }
        return taxList;
    }

}


 /*   SELECT result.userid,user.name,user.surname,user.email,cartax.ageOfCar,cartax.engine,cartax.price, result.total  FROM result
        inner join cartax on result.id = cartax.taxid
        inner join user on result.userid = user.id*/