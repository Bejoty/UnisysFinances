package io.bej.android.unisysfinances.model;

import java.math.BigDecimal;

public interface WorkWeek {
    int getId();
    int getYear();
    int getWeekInYear();
    double getHours();
    double getMiles();
    BigDecimal getExpenses();
    BigDecimal getGrossEarnings();
    BigDecimal getTaxes();
    BigDecimal getDeductions();
    String getMonday();
    String getFriday();
}