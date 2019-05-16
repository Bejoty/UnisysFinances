package io.bej.android.unisysfinances.db.entity;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import io.bej.android.unisysfinances.model.WorkWeek;
import io.bej.android.unisysfinances.util.UnisysUtils;

@Entity(tableName = "weeks")
public class WorkWeekEntity implements WorkWeek {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int year;
    @ColumnInfo(name = "week_in_year")
    private int weekInYear;
    private double hours;
    private double miles;
    @ColumnInfo(name = "gross_earnings")
    private BigDecimal grossEarnings;
    private BigDecimal taxes;
    private BigDecimal deductions;
    private BigDecimal expenses;

    public WorkWeekEntity() {

    }

    @Ignore
    public WorkWeekEntity(int id, int year, int weekInYear, double hours,
                          BigDecimal grossEarnings, BigDecimal taxes, BigDecimal deductions) {
        this.id = id;
        this.year = year;
        this.weekInYear = weekInYear;
        this.hours = hours;
        this.grossEarnings = grossEarnings;
        this.taxes = taxes;
        this.deductions = deductions;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public int getWeekInYear() {
        return weekInYear;
    }

    public void setWeekInYear(int weekInYear) {
        this.weekInYear = weekInYear;
    }

    @Override
    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
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

    @Override
    public BigDecimal getGrossEarnings() {
        return grossEarnings;
    }

    public void setGrossEarnings(BigDecimal grossEarnings) {
        this.grossEarnings = grossEarnings;
    }

    @Override
    public BigDecimal getTaxes() {
        return taxes;
    }

    public void setTaxes(BigDecimal taxes) {
        this.taxes = taxes;
    }

    @Override
    public BigDecimal getDeductions() {
        return deductions;
    }

    public void setDeductions(BigDecimal deductions) {
        this.deductions = deductions;
    }

    // TODO - Is this the most appropriate place to define data derivation functions?
    // It's particularly handy for databinding use in layout files.
    // Perhaps binding adapters...

    @Override
    public String getMonday() {
        return UnisysUtils.getMonday(year, weekInYear);
    }

    @Override
    public String getFriday() {
        return UnisysUtils.getFriday(year, weekInYear);
    }
}
