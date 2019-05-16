package io.bej.android.unisysfinances.model;

import java.math.BigDecimal;
import java.util.Date;

public interface WorkDay {
    int getId();
    Date getDate();
    double getHoursWorked();
    double getHoursTraveled();
    double getMiles();
    BigDecimal getExpenses();
}
