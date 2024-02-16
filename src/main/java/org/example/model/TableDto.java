package org.example.model;

public class TableDto extends BookingDto {
    private Integer totalTable;

    public TableDto(String name, String contactPhoneNumber, String date, String timeToEnter, String timeToGoOut, Integer numberOfCustomers) {
        super(name, contactPhoneNumber, date, timeToEnter, timeToGoOut, numberOfCustomers);
    }

    public Integer getTotalTable() {
        return totalTable;
    }

    public void setTotalTable(Integer totalTable) {
        this.totalTable = totalTable;
    }
}
