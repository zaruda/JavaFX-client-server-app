package Tables;


import java.io.Serializable;
import java.time.LocalDate;


public class Parameters implements Serializable{

    private int ageOfCar;
    private int engine;
    private double weight;
    private double value;
    private LocalDate date;


    public Parameters(int ageOfCar, int engine, double value) {
        this.ageOfCar = ageOfCar;
        this.engine = engine;
        this.value = value;
    }

    public Parameters(int value, LocalDate date) {
        this.value = value;
        this.date = date;
    }

    public Parameters(double goodsValue, double goodsWeight, LocalDate date) {
        this.value=goodsValue;
        this.weight = goodsWeight;
        this.date = date;
    }

    public int getAgeOfCar() {
        return ageOfCar;
    }

    public void setAgeOfCar(int ageOfCar) {
        this.ageOfCar = ageOfCar;
    }

    public int getEngine() {
        return engine;
    }

    public void setEngine(int engine) {
        this.engine = engine;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public double getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
