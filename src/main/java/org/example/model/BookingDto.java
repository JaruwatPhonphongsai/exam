package org.example.model;

public class BookingDto {

    private String name;

    private String contactPhoneNumber;

    private String date;

    private String timeToEnter;

    private String timeToGoOut;

    private Integer numberOfCustomers;

    public BookingDto(String name, String contactPhoneNumber, String date, String timeToEnter, String timeToGoOut, Integer numberOfCustomers) {
        this.name = name;
        this.contactPhoneNumber = contactPhoneNumber;
        this.date = date;
        this.timeToEnter = timeToEnter;
        this.timeToGoOut = timeToGoOut;
        this.numberOfCustomers = numberOfCustomers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeToEnter() {
        return timeToEnter;
    }

    public void setTimeToEnter(String timeToEnter) {
        this.timeToEnter = timeToEnter;
    }

    public String getTimeToGoOut() {
        return timeToGoOut;
    }

    public void setTimeToGoOut(String timeToGoOut) {
        this.timeToGoOut = timeToGoOut;
    }

    public Integer getNumberOfCustomers() {
        return numberOfCustomers;
    }

    public void setNumberOfCustomers(Integer numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
    }
}
