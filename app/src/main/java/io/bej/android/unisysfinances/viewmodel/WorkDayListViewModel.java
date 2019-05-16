package io.bej.android.unisysfinances.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import io.bej.android.unisysfinances.FinancesApp;
import io.bej.android.unisysfinances.DataRepository;
import io.bej.android.unisysfinances.db.entity.WorkDayEntity;

public class WorkDayListViewModel extends AndroidViewModel {

    private final DataRepository mRepository;

    private final MediatorLiveData<List<WorkDayEntity>> mObservableWorkDays;

    public WorkDayListViewModel(@NonNull Application application) {
        super(application);

        mObservableWorkDays = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableWorkDays.setValue(null);

        mRepository = ((FinancesApp) application).getRepository();
        LiveData<List<WorkDayEntity>> products = mRepository.getWorkDays();

        // observe the changes of the products from the database and forward them
        mObservableWorkDays.addSource(products, mObservableWorkDays::setValue);
    }

    public LiveData<List<WorkDayEntity>> getWorkDays() {
        return mObservableWorkDays;
    }
}
