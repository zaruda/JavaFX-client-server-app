package Tables;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class GoodsTax implements Serializable{

    private final StringProperty id;
    private final StringProperty name;
    private final StringProperty surname;
    private final StringProperty email;
    private final StringProperty type;
    private final StringProperty value;
    private final StringProperty date;
    private final StringProperty total;

    public GoodsTax(String id,String name, String surname, String email, String type, String value, String date,String total) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.email =new SimpleStringProperty(email);
        this.type = new SimpleStringProperty(type);
        this.value = new SimpleStringProperty(value);
        this.date = new SimpleStringProperty(date);
        this.total = new SimpleStringProperty(total);

    }

    public String getID(){return id.get();}
    public String getName(){return name.get();}
    public String getSurname(){return surname.get();}
    public String getemail(){return email.get();}
    public String gettype(){return type.get();}
    public String getvalue(){return value.get();}
    public String getdate(){return date.get();}
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
    public void settype(String type) {
        this.name.set(type);
    }
    public void setvalue(String value) {
        this.name.set(value);
    }
    public void setdate(String date){ this.id.set(date);}
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

    public StringProperty typeProperty() {
        return type;
    }

    public StringProperty valueProperty() {
        return value;
    }

    public StringProperty dateProperty() {
        return date;
    }

    public StringProperty totalProperty() {
        return total;
    }


}
