package io.bej.android.unisysfinances.ui;

import androidx.appcompat.app.AppCompatActivity;
import io.bej.android.unisysfinances.R;
import io.bej.android.unisysfinances.model.WorkWeek;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Add product list fragment if this is first creation
        if (savedInstanceState == null) {
            WeekListFragment fragment = new WeekListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, WeekListFragment.TAG).commit();
        }
    }

    /** Shows the week detail fragment */
    public void show(WorkWeek week) {

        WeekDetailFragment weekFragment = WeekDetailFragment.forWeek(week.getId());

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("week")
                .replace(R.id.fragment_container,
                        weekFragment, null).commit();
    }
}
