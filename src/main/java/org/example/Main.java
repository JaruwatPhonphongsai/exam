package org.example;

import org.example.model.BookingDto;
import org.example.service.BookingService;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String targetDate = "01/01/2024";
        BookingService bookingService = new BookingService();
        List<BookingDto> bookingDtoList = new ArrayList<>();
        bookingDtoList.add(new BookingDto("A", "1234567891", "01/01/2024", "02:00", "04:00", 3)); // 1
        bookingDtoList.add(new BookingDto("B", "1234567892", "01/01/2024", "03:00", "05:00", 3)); // 1
        bookingDtoList.add(new BookingDto("C", "1234567893", "01/01/2024", "06:30", "07:00", 4)); // 2
        bookingDtoList.add(new BookingDto("A", "1234567894", "02/01/2024", "06:30", "09:00", 8)); // 2
        bookingDtoList.add(new BookingDto("B", "1234567895", "02/01/2024", "06:30", "08:00", 6)); // 2
        bookingDtoList.add(new BookingDto("A", "1234567896", "03/01/2024", "08:30", "10:00", 10)); // 3
        bookingDtoList.add(new BookingDto("B", "1234567897", "03/01/2024", "08:30", "09:30", 6)); // 2
        System.out.println(bookingService.booking(targetDate, bookingDtoList));
    }
}
