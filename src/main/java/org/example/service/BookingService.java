package org.example.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.model.BookingDto;
import org.example.model.TableDto;
import static org.example.extensions.ConvertCommon.stringToDateTime;
import static org.example.extensions.ValidateCommon.*;

public class BookingService {

    private static final double MAX_BOOKING_TABLE = 4.0;

    public Map<String, String> booking(String selectDate, List<BookingDto> bookingDtoList) {
        if (isValidDate(selectDate)) {
            return processBooking(selectDate, bookingDtoList);
        }
        return null;
    }

    private Map<String, String> processBooking(String selectDate, List<BookingDto> bookingDtoList) {
        Map<String, List<TableDto>> tableInformation = new HashMap<>();
        Map<String, String> processInfo = new HashMap<>();
        for (BookingDto bookingDto : bookingDtoList) {
            if (isInvalidBooking(bookingDto)) {
                processInfo.put("invalid", "true");
                return processInfo;
            }
            calculateTableCount(tableInformation, bookingDto);
        }
        setBookingInformation(selectDate, processInfo, tableInformation);
        processInfo.put("invalid", "false");
        return processInfo;
    }

    private boolean isInvalidBooking(BookingDto bookingDto) {
        return isEmptyString(bookingDto.getName())
                || isEmptyString(bookingDto.getContactPhoneNumber())
                || !isValidPhoneNumber(bookingDto.getContactPhoneNumber())
                || isEmptyString(bookingDto.getDate())
                || !isValidDate(bookingDto.getDate())
                || isEmptyString(bookingDto.getTimeToEnter())
                || !isValidTime(bookingDto.getTimeToEnter())
                || isEmptyString(bookingDto.getTimeToGoOut())
                || !isValidTime(bookingDto.getTimeToGoOut())
                || isInvalidDateTimeOrder(bookingDto.getDate(), bookingDto.getTimeToEnter(), bookingDto.getTimeToGoOut())
                || isEmptyInteger(bookingDto.getNumberOfCustomers())
                || !isGreaterThan0Integer(bookingDto.getNumberOfCustomers());
    }

    private boolean isInvalidDateTimeOrder(String date, String timeToEnter, String timeToGoOut) {
        return stringToDateTime(date + " " + timeToGoOut).getTime() < stringToDateTime(date + " " + timeToEnter).getTime();
    }

    private void setBookingInformation(String selectDate, Map<String, String> processInfo, Map<String, List<TableDto>> tableInformation) {
        int maxTables = 0;
        List<TableDto> tablesForDate = tableInformation.get(selectDate);
        if (tablesForDate != null) {
            for (TableDto table1 : tablesForDate) {
                int totalTablesForOverlap = table1.getTotalTable();
                for (TableDto table2 : tablesForDate) {
                    if (!table1.equals(table2) && isOverlapping(table1, table2)) {
                        totalTablesForOverlap += table2.getTotalTable();
                    }
                }
                if (totalTablesForOverlap > maxTables) {
                    maxTables = totalTablesForOverlap;
                }
            }
        }
        processInfo.put(String.format("Minimum number of tables needed on %s ", selectDate), String.format(" %s", maxTables));
    }

    private boolean isOverlapping(TableDto table1, TableDto table2) {
        return table1.getTimeToEnter().compareTo(table2.getTimeToGoOut()) < 0 &&
                table1.getTimeToGoOut().compareTo(table2.getTimeToEnter()) > 0;
    }

    private void calculateTableCount(Map<String, List<TableDto>> tableInfo, BookingDto bookingDto) {
        tableInfo.computeIfAbsent(bookingDto.getDate(), k -> new ArrayList<>())
                .add(createTable(bookingDto));
    }

    private TableDto createTable(BookingDto bookingDto) {
        TableDto tableDto = new TableDto(
                bookingDto.getName(),
                bookingDto.getContactPhoneNumber(),
                bookingDto.getDate(),
                bookingDto.getTimeToEnter(),
                bookingDto.getTimeToGoOut(),
                bookingDto.getNumberOfCustomers()
        );
        tableDto.setTotalTable((int) Math.ceil(bookingDto.getNumberOfCustomers() / MAX_BOOKING_TABLE));
        return tableDto;
    }
}
