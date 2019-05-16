package io.bej.android.unisysfinances.db;

import android.content.Context;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.bej.android.unisysfinances.db.entity.WorkDayEntity;
import io.bej.android.unisysfinances.db.entity.WorkWeekEntity;
import io.bej.android.unisysfinances.util.UnisysUtils;

class DataGenerator {
    static List<WorkDayEntity> generateWorkDays(Context context) {
        return JsonDataUtil.getWorkDataFromJson(context);
    }

    /**
     * TODO
     * @param workDays
     * @return
     */
    static List<WorkWeekEntity> generateWorkWeeks(List<WorkDayEntity> workDays) {
        List<WorkWeekEntity> workWeeks = new ArrayList<>();

        WorkDayEntity firstDay = workDays.get(0);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-ww", Locale.US);
        String curWeekTag = format.format(firstDay.getDate());

        double hours = 0;
        double miles = 0;
        BigDecimal expenses = new BigDecimal(0);
        for (WorkDayEntity day : workDays) {
            String weekTag = format.format(day.getDate());
            if (!weekTag.equals(curWeekTag)) {
                WorkWeekEntity week = new WorkWeekEntity();

                String[] timeInfo = curWeekTag.split("-");
                week.setYear(Integer.parseInt(timeInfo[0]));
                week.setWeekInYear(Integer.parseInt(timeInfo[1]));

                BigDecimal bd = new BigDecimal(hours);
                bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
                week.setHours(bd.doubleValue());

                // TODO - Use BigDecimal here? Previous BigDecimal unnecessary?
                week.setMiles(miles);
                week.setExpenses(expenses);

                BigDecimal earnings = UnisysUtils.calculateEarnings(hours);
                week.setGrossEarnings(earnings);
                week.setTaxes(UnisysUtils.calculateTaxes(earnings));
                week.setDeductions(UnisysUtils.calculateDeductions());

                workWeeks.add(week);

                curWeekTag = weekTag;

                hours = 0;
                miles = 0;
                expenses = new BigDecimal(0);
            }

            hours += day.getHoursWorked() + day.getHoursTraveled();
            miles += day.getMiles();
            expenses = expenses.add(day.getExpenses());
        }
        return workWeeks;
    }
}
