package com.example.cch.mongodemo.config;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.bson.json.Converter;
import org.bson.json.StrictJsonWriter;

public class JsonDateTimeCoverter implements Converter<Long> {

    static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.of("UTC"));

    @Override
    public void convert(Long value, StrictJsonWriter writer) {
        // TODO Auto-generated method stub
        try {
            Instant instant = new Date(value).toInstant();
            String s = DATE_TIME_FORMATTER.format(instant);
            writer.writeString(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
