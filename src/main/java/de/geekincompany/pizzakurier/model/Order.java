package de.geekincompany.pizzakurier.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Order {
    private ArrayList<OrderObject> objects;
    private Adress adress;
    private Date date;
    private boolean takeaway;
    private boolean submitted;

    public Order(){
        objects = new ArrayList<>();
        date = new Date();
        adress = DataStore.adress;
        takeaway = false;
    }

    public void addObject(OrderObject object){
        objects.add(object);
    }

    public List<OrderObject> getObjects() {
        return objects;
    }

    public Date getDate(){
        return date;
    }

    public boolean isTakeaway(){
        return takeaway;
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        StringBuilder tmp = new StringBuilder("Order " + df.format(date) + " - #" + objects.size() + "\n");
        for(OrderObject obj:objects){
            tmp.append("\t").append(obj).append("\n");
        }
        return tmp.toString();
    }

    public void setTakeaway(boolean takeaway) {
        this.takeaway = takeaway;
    }

    public void setSubmitted(boolean submitted){
        this.submitted = submitted;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }
}
