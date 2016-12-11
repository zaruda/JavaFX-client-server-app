package Tables;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;


public class Taxes implements Serializable{
   /* private StringProperty name;
    private StringProperty surname;
    private StringProperty taxType;
    private StringProperty summary;
    private StringProperty date;

    public Taxes(String name, String surname, String taxType, String summary) {
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.taxType = new SimpleStringProperty(taxType);
        this.summary = new SimpleStringProperty(summary);
    }

    public Taxes(String name, String surname, String taxType, String summary, String date) {
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.taxType = new SimpleStringProperty(taxType);
        this.summary = new SimpleStringProperty(summary);
        this.date = new SimpleStringProperty(date);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getTaxType() {
        return taxType.get();
    }

    public StringProperty taxTypeProperty() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType.set(taxType);
    }

    public String getSummary() {
        return summary.get();
    }

    public StringProperty summaryProperty() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary.set(summary);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }*/
     private String name;
    private String surname;
    private String taxType;
    private String summary;
    private String date;

    public Taxes(String name, String surname, String taxType, String summary) {
        this.name = name;
        this.surname = surname;
        this.taxType = taxType;
        this.summary = summary;
    }

    public Taxes(String name, String surname, String taxType, String summary, String date) {
        this.name = name;
        this.surname = surname;
        this.taxType = taxType;
        this.summary = summary;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
