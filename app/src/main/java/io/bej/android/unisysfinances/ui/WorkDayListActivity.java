package io.bej.android.unisysfinances.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.bej.android.unisysfinances.R;
import io.bej.android.unisysfinances.db.entity.WorkDayEntity;
import io.bej.android.unisysfinances.viewmodel.WorkDayListViewModel;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class WorkDayListActivity extends AppCompatActivity {

    private TextView mError;
    private RecyclerView mWorkDaysList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWorkDaysList.setLayoutManager(new LinearLayoutManager(this));

        final WorkDayListViewModel viewModel =
                ViewModelProviders.of(this).get(WorkDayListViewModel.class);

        subscribeUI(viewModel.getWorkDays());
    }

    private void subscribeUI(LiveData<List<WorkDayEntity>> liveData) {
        liveData.observe(this, new Observer<List<WorkDayEntity>>() {
            @Override
            public void onChanged(List<WorkDayEntity> workDayEntities) {
                if (workDayEntities != null) {
                    mError.setVisibility(View.INVISIBLE);
                    // TODO - Figure out where to use WorkDay vs WorkDayEntity
                    //mWorkDaysList.setAdapter(new WorkDayAdapter(workDayEntities));
                } else {
                    mError.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
