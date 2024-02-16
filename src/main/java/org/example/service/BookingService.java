package org.example.service;

import java.util.*;
import org.example.model.BookingDto;
import org.example.model.TableDto;
import static org.example.extensions.ValidateCommon.isValidDate;

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
            calculateTableCount(tableInformation, bookingDto);
        }
        setBookingInformation(selectDate, processInfo, tableInformation);
        return processInfo;
    }

    private void setBookingInformation(String selectDate, Map<String, String> processInfo, Map<String, List<TableDto>> tableInformation) {
        int maxTables = 0;
        List<TableDto> tablesForDate = tableInformation.get(selectDate);


        Map<String, List<TableDto>> groupsByDate = new HashMap<>();

        for (TableDto tableDto : tablesForDate) {
            groupsByDate.computeIfAbsent(tableDto.getDate(), k -> new ArrayList<>()).add(tableDto);
        }

        List<List<TableDto>> finalGroups = new ArrayList<>();

        for (List<TableDto> tableDtoByDate : groupsByDate.values()) {
            tableDtoByDate.sort(Comparator.comparing(TableDto::getTimeToEnter));

            List<List<TableDto>> groups = new ArrayList<>();
            List<TableDto> currentGroup = new ArrayList<>();
            TableDto previousTableDto = null;

            for (TableDto tableDto : tableDtoByDate) {
                if (previousTableDto != null && !isOverlapping(previousTableDto, tableDto)) {
                    groups.add(currentGroup);
                    currentGroup = new ArrayList<>();
                }
                currentGroup.add(tableDto);
                previousTableDto = tableDto;
            }
            groups.add(currentGroup);

            finalGroups.addAll(groups);
            int total;
            int countTable;
            for (List<TableDto> group : finalGroups) {
                total = 0;
                for (TableDto reservation : group) {
                    countTable = (int) Math.ceil(reservation.getNumberOfCustomers() / 4.0);
                    total += countTable;
                }
                if (total > maxTables) {
                    maxTables = total;
                }
            }
            processInfo.put(selectDate, String.valueOf(maxTables));
        }
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
