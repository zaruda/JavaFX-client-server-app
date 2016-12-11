package database;

import Tables.Taxes;
import Tables.User;

import java.time.LocalDate;
import java.util.ArrayList;

public interface databaseInterface {
    boolean logIN(String login, String pass);
    boolean RegisterNewUser(String name,String surname,String email,String login,String pass);
    boolean insertCarTax(String type, int ageOfCar, int engineType, double carPrice, int totalCost);
    boolean insertGoodsTax(String type,double goodsValue, double weight, double goodsTaxValue, LocalDate date);
    boolean insertMoneyTax(String type,double moneySum, LocalDate date);
    boolean deleteRecord(String login);
    ArrayList<Taxes> showHistory(String login);
    ArrayList<User> userProfileInfo(String login);
    int carTaxSummary();
    int moneyTaxSummary();
    int goodsTaxSummary();
    int carTaxesCount();
    int moneyTaxesCount();
    int goodsTaxesCount();
}
