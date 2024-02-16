package org.example.extensions;

import org.example.model.BookingDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.extensions.ConvertCommon.stringToDateTime;

public class ValidateCommon {

    public static boolean isEmptyString(String value) {
        if (null == value) {
            return true;
        }
        return "".equals(value.trim());
    }

    public static boolean isEmptyInteger(Integer value) {
        return null == value;
    }

    public static boolean isGreaterThan0Integer(Integer value) {
        return 0 <= value;
    }

    public static boolean isValidDate(String value) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isValidTime(String value) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^\\+[0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public static boolean isInvalidBooking(BookingDto bookingDto) {
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

    public static boolean isInvalidDateTimeOrder(String date, String timeToEnter, String timeToGoOut) {
        return stringToDateTime(date + " " + timeToGoOut).getTime() < stringToDateTime(date + " " + timeToEnter).getTime();
    }

}
