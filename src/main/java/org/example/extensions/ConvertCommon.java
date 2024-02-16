package org.example.extensions;

import org.example.Main;
import org.example.model.BookingDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.example.extensions.ValidateCommon.isInvalidBooking;

public class ConvertCommon {

    public static Date stringToDateTime(String value) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            return format.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<BookingDto> readBookingData(String filename) throws IOException {
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(filename);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            return bufferedReader.lines()
                    .map(line -> {
                        String[] data = line.split(",");
                        if (5 <= data.length) {
                            BookingDto bookingDto = new BookingDto(data[0], data[1], data[2], data[3], data[4], Integer.valueOf(data[5]));
                            if (!isInvalidBooking(bookingDto)) {
                                return bookingDto;
                            }
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
    }
}
