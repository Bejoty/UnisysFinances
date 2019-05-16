package io.bej.android.unisysfinances.db;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import io.bej.android.unisysfinances.R;
import io.bej.android.unisysfinances.db.entity.WorkDayEntity;

public class JsonDataUtil {

    static List<WorkDayEntity> getWorkDataFromJson(Context context) {
        List<WorkDayEntity> workData = new ArrayList<>();
        try {
            String jsonString = getJsonString(context);
            if (jsonString == null) return workData;

            JSONObject jsonData = new JSONObject(jsonString);
            JSONArray entries = jsonData.getJSONArray("entries");
            for (int i = 0; i < entries.length(); i++) {
                JSONObject entry = entries.getJSONObject(i);
                WorkDayEntity day = new WorkDayEntity();

                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                day.setDate(format.parse(entry.getString("date")));

                JSONObject data = entry.getJSONObject("data");
                day.setHoursWorked(data.getDouble("tech"));
                day.setHoursTraveled(data.getDouble("travel"));
                day.setMiles(data.getDouble("distance"));
                day.setExpenses(new BigDecimal(data.getString("expenses")));

                workData.add(day);
            }
            return workData;
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
            return workData;
        }
    }

    private static String getJsonString(Context context) {
        InputStream data = context.getResources().openRawResource(R.raw.data);
        Scanner scanner = new java.util.Scanner(data).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : null;
    }
}
