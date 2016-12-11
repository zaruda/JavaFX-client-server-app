package Tables;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


import java.io.Serializable;

public class CarTax implements Serializable{
    private final StringProperty id;
    private final StringProperty name;
    private final StringProperty surname;
    private final StringProperty email;
    private final StringProperty ageOfCar;
    private final StringProperty engine;
    private final StringProperty price;
    private final StringProperty total;

    public CarTax(String id,String name, String surname, String email, String ageOfCar, String engine, String price,String total) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.email =new SimpleStringProperty(email);
        this.ageOfCar = new SimpleStringProperty(ageOfCar);
        this.engine = new SimpleStringProperty(engine);
        this.price = new SimpleStringProperty(price);
        this.total = new SimpleStringProperty(total);

    }


    public String getID(){return id.get();}
    public String getName(){return name.get();}
    public String getSurname(){return surname.get();}
    public String getemail(){return email.get();}
    public String getageOfCar(){return ageOfCar.get();}
    public String getengine(){return engine.get();}
    public String getPrice(){return price.get();}
    public String getTotal(){return total.get();}

    public void setID(String id){ this.id.set(id);}
    public void setName(String name) {
        this.name.set(name);
    }
    public void setSurname(String surname) {
        this.name.set(surname);
    }
    public void setemail(String email) {
        this.name.set(email);
    }
    public void setageOfCar(String ageOfCar) {
        this.name.set(ageOfCar);
    }
    public void setengine(String engine) {
        this.name.set(engine);
    }
    public void setPrice(String price){ this.id.set(price);}
    public void setTotal(String total){ this.id.set(total);}

    public StringProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty ageOfCarProperty() {
        return ageOfCar;
    }

    public StringProperty engineProperty() {
        return engine;
    }

    public StringProperty priceProperty() {
        return price;
    }

    public StringProperty totalProperty() {
        return total;
    }

}
