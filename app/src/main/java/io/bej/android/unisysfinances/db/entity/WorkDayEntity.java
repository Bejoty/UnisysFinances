package io.bej.android.unisysfinances.db.entity;

import java.math.BigDecimal;
import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import io.bej.android.unisysfinances.model.WorkDay;

@Entity(tableName = "days")
public class WorkDayEntity implements WorkDay {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date date;
    @ColumnInfo(name = "hours_worked")
    private double hoursWorked;
    @ColumnInfo(name = "hours_traveled")
    private double hoursTraveled;
    private double miles;
    private BigDecimal expenses;

    public WorkDayEntity() {
    }

    @Ignore
    public WorkDayEntity(int id, Date date, double hoursWorked, double hoursTraveled,
                         double miles, BigDecimal expenses) {
        this.id = id;
        this.date = date;
        this.hoursWorked = hoursWorked;
        this.hoursTraveled = hoursTraveled;
        this.miles = miles;
        this.expenses = expenses;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double getHoursTraveled() {
        return hoursTraveled;
    }

    public void setHoursTraveled(double hoursTraveled) {
        this.hoursTraveled = hoursTraveled;
    }

    @Override
    public double getMiles() {
        return miles;
    }

    public void setMiles(double miles) {
        this.miles = miles;
    }

    @Override
    public BigDecimal getExpenses() {
        return expenses;
    }

    public void setExpenses(BigDecimal expenses) {
        this.expenses = expenses;
    }
}
