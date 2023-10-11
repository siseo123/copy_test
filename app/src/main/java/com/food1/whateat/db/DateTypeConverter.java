package com.food1.whateat.db;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import java.time.LocalDateTime;

@ProvidedTypeConverter
public class DateTypeConverter {

    @TypeConverter
    public static LocalDateTime fromTimestamp(String value) {
        return value == null ? null : LocalDateTime.parse(value);
    }

    @TypeConverter
    public static String dateToTimestamp(LocalDateTime date) {
        return date == null ? null : date.toString();
    }
}
