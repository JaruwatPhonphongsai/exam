package org.example;

import org.example.model.BookingDto;
import org.example.service.BookingService;

import java.io.IOException;
import java.util.List;

import static org.example.extensions.ConvertCommon.readBookingData;

public class Main {

    public static void main(String[] args) throws IOException {
        String selectDate = args.length > 0 ? args[0] : "01/01/2024";
        BookingService bookingService = new BookingService();
        List<BookingDto> bookingDtoList = readBookingData("booking-information.csv");
        System.out.println("Number of tables that must be prepared on the day " + selectDate + " is " +
                bookingService.booking(selectDate, bookingDtoList).get(selectDate) + " item.");
    }
}
