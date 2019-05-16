package io.bej.android.unisysfinances.db.converter;

import java.math.BigDecimal;

import androidx.room.TypeConverter;

public class BigDecimalConverter {
    @TypeConverter
    public static BigDecimal toBigDecimal(Long value) {
        return value == null ? null : new BigDecimal(value).divide(new BigDecimal(100));
    }

    @TypeConverter
    public static Long toLong(BigDecimal bigDecimal) {
        return bigDecimal == null ? null : bigDecimal.multiply(new BigDecimal(100)).longValue();
    }
}
