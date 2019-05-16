package io.bej.android.unisysfinances.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import io.bej.android.unisysfinances.DataRepository;
import io.bej.android.unisysfinances.FinancesApp;
import io.bej.android.unisysfinances.db.entity.WorkDayEntity;
import io.bej.android.unisysfinances.db.entity.WorkWeekEntity;
import io.bej.android.unisysfinances.model.WorkWeek;

public class WeekListViewModel extends AndroidViewModel {

    private final DataRepository mRepository;

    private final MediatorLiveData<List<WorkWeekEntity>> mObservableWeeks;

    public WeekListViewModel(@NonNull Application application) {
        super(application);

        mObservableWeeks = new MediatorLiveData<>();
        mObservableWeeks.setValue(null);

        mRepository = ((FinancesApp) application).getRepository();
        LiveData<List<WorkWeekEntity>> weeks = mRepository.getWorkWeeks();

        mObservableWeeks.addSource(weeks, mObservableWeeks::setValue);
    }

    public LiveData<List<WorkWeekEntity>> getWeeks() {
        return mObservableWeeks;
    }
}
