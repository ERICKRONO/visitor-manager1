package com.ericktech.vistorsmanager.one;

public class vistorObject {

    private String name;
    private String idNumber;
    private String phone;
    private String destination;


    public vistorObject() {

    }

    public  vistorObject(String name, String idNumber, String phone, String destination) {
        this.name = name;
        this.idNumber = idNumber;
        this.phone = phone;
        this.destination = destination;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIdNumberd(String idNumber) {
        this.idNumber = idNumber;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }



    /***************************************************/

    public String getName() {
        return name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getPhone() {
        return phone;
    }

    public String getDestination() {
        return destination;
    }
}
