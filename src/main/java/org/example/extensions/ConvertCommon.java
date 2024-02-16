package org.example.extensions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertCommon {

    public static Date stringToDate(String value) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return format.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date stringToDateTime(String value) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            return format.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
