package org.example;

import org.example.model.BookingDto;
import org.example.service.BookingService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        String selectDate = args.length > 0 ? args[0] : "01/01/2024";
        BookingService bookingService = new BookingService();
        List<BookingDto> bookingDtoList = readBookingData("booking-information.csv");
        System.out.println(bookingService.booking(selectDate, bookingDtoList));
    }

    private static List<BookingDto> readBookingData(String filename) throws IOException {
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(filename);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            return bufferedReader.lines()
                    .map(line -> {
                        String[] data = line.split(",");
                        return new BookingDto(data[0], data[1], data[2], data[3], data[4], Integer.valueOf(data[5]));
                    })
                    .collect(Collectors.toList());
        }
    }
}
