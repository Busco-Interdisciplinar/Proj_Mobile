package com.example.busco.Api;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public class DateAdapter {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    @ToJson
    String dateToJson(Date date) {
        return dateFormat.format(date);
    }

    @FromJson
    Date dateFromJson(String dateStr) throws ParseException {
        return dateFormat.parse(dateStr);
    }
}
