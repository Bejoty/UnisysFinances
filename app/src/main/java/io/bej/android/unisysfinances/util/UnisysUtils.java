package io.bej.android.unisysfinances.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UnisysUtils {
    private static final String INCOME_RATE_STANDARD = "20.34";
    private static final String INCOME_RATE_OVERTIME = "30.51";
    private static final int INCOME_RATE_THRESHOLD = 40;

    // Tax rates derived from 2018 pay stub data
    private static final String WEEKLY_TAX_RATE_MEDICARE = "0.0145";
    private static final String WEEKLY_TAX_RATE_SOCIAL_SECURITY = "0.062";
    private static final String WEEKLY_TAX_RATE_FIELD_TECH = "0.009045";

    private static final String DEDUCTION_MEC_PLUS = "27.63";
    private static final String DEDUCTION_MEC_DENTAL = "4.99";

    private static final String MILEAGE_RATE_STANDARD = "0.51";
    private static final String MILEAGE_RATE_EXCESS = "0.51";
    private static final int MILEAGE_THRESHOLD = 225;


    private static BigDecimal roundValue(BigDecimal grossPay) {
        return grossPay.setScale(2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal calculateEarnings(double hours) {
        BigDecimal earnings;
        if (hours > INCOME_RATE_THRESHOLD) {
            earnings = new BigDecimal(INCOME_RATE_THRESHOLD).multiply(new BigDecimal(INCOME_RATE_STANDARD));
            earnings = earnings.add(new BigDecimal(hours - INCOME_RATE_THRESHOLD)
                    .multiply(new BigDecimal(INCOME_RATE_OVERTIME)));
        } else {
            earnings = new BigDecimal(hours).multiply(new BigDecimal(INCOME_RATE_STANDARD));
        }
        return roundValue(earnings);
    }

    public static BigDecimal calculateTaxes(BigDecimal earnings) {
        BigDecimal taxes = calculateFederalTax(earnings).add(earnings.multiply(
                new BigDecimal(WEEKLY_TAX_RATE_MEDICARE)
                .add(new BigDecimal(WEEKLY_TAX_RATE_SOCIAL_SECURITY))
                .add(new BigDecimal(WEEKLY_TAX_RATE_FIELD_TECH))
        ));
        return roundValue(taxes);
    }

    // Federal tax calculation derived from 2018 pay stub data
    private static BigDecimal calculateFederalTax(BigDecimal earnings) {
        return earnings.multiply(new BigDecimal("0.12")).subtract(new BigDecimal("31.4"));
    }

    public static BigDecimal calculateDeductions() {
        return roundValue(new BigDecimal(DEDUCTION_MEC_PLUS).add(new BigDecimal(DEDUCTION_MEC_DENTAL)));
    }

    public static BigDecimal calculateMileageReimbursement(double miles) {
        BigDecimal reimbursement;
        if (miles > MILEAGE_THRESHOLD) {
            reimbursement = new BigDecimal(MILEAGE_THRESHOLD).multiply(new BigDecimal(MILEAGE_RATE_STANDARD));
            reimbursement = reimbursement.add(new BigDecimal(miles - MILEAGE_THRESHOLD)
                    .multiply(new BigDecimal(MILEAGE_RATE_EXCESS)));
        } else {
            reimbursement = new BigDecimal(miles).multiply(new BigDecimal(MILEAGE_RATE_STANDARD));
        }
        return roundValue(reimbursement);
    }

    public static String getMonday(int year, int weekInYear) {
        return getDayInWeek(year, weekInYear, "Monday");
    }

    public static String getFriday(int year, int weekInYear) {
        return getDayInWeek(year, weekInYear, "Friday");
    }

    private static String getDayInWeek(int year, int weekInYear, String day) {
        DateFormat parseFormat = new SimpleDateFormat("yyyy ww E", Locale.US);
        DateFormat displayFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
        try {
            Date date = parseFormat.parse(String.valueOf(year) + " " + String.valueOf(weekInYear) + " " + day);
            return displayFormat.format(date);
        } catch (ParseException e) {
            return String.format(
                    Locale.US,
                    "UnisysUtils Error: could not parse date (%d:%d, %s)",
                    year, weekInYear, day
            );
        }
    }
}
