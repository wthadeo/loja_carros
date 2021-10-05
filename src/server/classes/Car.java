package server.classes;

import java.io.Serializable;

public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum Category {ECONOMIC, INTERMEDIARY, EXECUTIVE}

    public String renavam;
    public String name;
    public Category category;
    public int year;
    public int quantity;
    public double price;

    public Car(){}

    public Car(String renavam, String name, int year, double price){

        this.renavam = renavam;
        this.name = name;
        this.year = year;
        this.quantity = 1;
        this.price = price;

        if(price <= 30000)
            this.category = Category.ECONOMIC;
        else if (price > 30000 && price <= 60000)
            this.category = Category.INTERMEDIARY;
        else
            this.category = Category.EXECUTIVE;


    }


}
