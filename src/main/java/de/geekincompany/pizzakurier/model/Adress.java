package de.geekincompany.pizzakurier.model;

public class Adress {
    int zip;
    String street;
    String name;
    String sname;
    String phone;
    private String email;
    AdressType type;

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AdressType getType() {
        return type;
    }

    public void setType(AdressType type) {
        this.type = type;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public enum AdressType{
        MALE("AnHr"),
        FEMALE("AnFr"),
        FIRM("AnFi");

        private final String id;

        AdressType(String id){
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public static AdressType valueOf(int i){
            return AdressType.values()[i];
        }
    }
}
