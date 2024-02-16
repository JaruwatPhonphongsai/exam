package org.example.service;

import java.util.*;

import org.example.model.BookingDto;
import org.example.model.TableDto;
import static org.example.extensions.ConvertCommon.stringToDateTime;
import static org.example.extensions.ValidateCommon.*;

public class BookingService {

    private static final double MAX_BOOKING_TABLE = 4.0;

    public Map<String, String> booking(String targetDate, List<BookingDto> bookingDtoList) {
        return processBooking(targetDate, bookingDtoList);
    }

    private Map<String, String> processBooking(String targetDate, List<BookingDto> bookingDtoList) {
        Map<String, List<TableDto>> tableInformation = new HashMap<>();
        Set<String> dateInformation = new HashSet<>();
        Map<String, String> processInfo = new HashMap<>();
        for (BookingDto bookingDto : bookingDtoList) {
            if (isBookingDtoInvalid(bookingDto)) {
                processInfo.put("invalid", "true");
                return processInfo;
            }

            dateInformation.add(bookingDto.getDate());
            calculateCountTable(tableInformation, bookingDto);
        }
        setInformationBooking(targetDate, processInfo, dateInformation, tableInformation);
        return processInfo;
    }

    private boolean isBookingDtoInvalid(BookingDto bookingDto) {
        if (isEmptyString(bookingDto.getName())) {
            return true;
        }
        if (isEmptyString(bookingDto.getContactPhoneNumber())
                && isInValidPhoneNumber(bookingDto.getContactPhoneNumber())) {
            return true;
        }
        if (isEmptyString(bookingDto.getDate())
                && isInValidDateFormat(bookingDto.getDate())) {
            return true;
        }
        if (isEmptyString(bookingDto.getTimeToEnter())
                && isInValidTimeFormat(bookingDto.getTimeToEnter())) {
            return true;
        }
        if (isEmptyString(bookingDto.getTimeToGoOut())
                && isInValidTimeFormat(bookingDto.getTimeToGoOut())) {
            return true;
        }
        if (stringToDateTime(bookingDto.getDate() + " " + bookingDto.getTimeToGoOut()).getTime()
                < stringToDateTime(bookingDto.getDate() + " " + bookingDto.getTimeToEnter()).getTime()) {
            return true;
        }
        return isEmptyInteger(bookingDto.getNumberOfCustomers())
                && isNotGreaterThan0Integer(bookingDto.getNumberOfCustomers());
    }

    private void setInformationBooking(
            String targetDate,
            Map<String, String> processInfo,
            Set<String> dateInformation,
            Map<String, List<TableDto>> tableInformation) {
        for (String date : new ArrayList<>(dateInformation)) {
            int max = 0;
            if (targetDate.equals(date)) {
                for (TableDto table1 : tableInformation.get(date)) {

                    int totalTableAllOverlap = table1.getTotalTable();
                    for (TableDto table2 : tableInformation.get(date)) {
                        if (!table1.equals(table2) && isOverlapping(table1, table2)) {
                            totalTableAllOverlap += table2.getTotalTable();
                        }
                    }
                    if (totalTableAllOverlap > max) {
                        max = totalTableAllOverlap;
                    }
                }
                processInfo.put(String.format("Minimum number of tables needed on %s ", date), String.format(" %s",max));
            }
        }
    }

    private void calculateCountTable(Map<String, List<TableDto>> tableInfo, BookingDto bookingDto) {
        if (!tableInfo.containsKey(bookingDto.getDate())) {
            tableInfo.put(bookingDto.getDate(), new ArrayList<>());
        }
        TableDto tableDto = new TableDto(
                bookingDto.getName(),
                bookingDto.getContactPhoneNumber(),
                bookingDto.getDate(),
                bookingDto.getTimeToEnter(),
                bookingDto.getTimeToGoOut(),
                bookingDto.getNumberOfCustomers());
        tableDto.setTotalTable((int) Math.ceil(bookingDto.getNumberOfCustomers() / MAX_BOOKING_TABLE));
        tableInfo.get(bookingDto.getDate()).add(tableDto);
    }

    private boolean isOverlapping(TableDto r1, TableDto r2) {
        return (r1.getTimeToEnter().compareTo(r2.getTimeToGoOut()) < 0) &&
                (r1.getTimeToGoOut().compareTo(r2.getTimeToEnter()) > 0);
    }

}
