package io.bej.android.unisysfinances.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import io.bej.android.unisysfinances.DataRepository;
import io.bej.android.unisysfinances.FinancesApp;
import io.bej.android.unisysfinances.db.entity.WorkWeekEntity;

public class WeekViewModel extends AndroidViewModel {

    private final LiveData<WorkWeekEntity> mObservableWeek;

    public ObservableField<WorkWeekEntity> week = new ObservableField<>();

    private final int mWeekId;

    public WeekViewModel(@NonNull Application application, DataRepository repository,
                         final int weekId) {
        super(application);
        mWeekId = weekId;

        mObservableWeek = repository.loadWeek(weekId);
    }

    public LiveData<WorkWeekEntity> getObservableWeek() {
        return mObservableWeek;
    }

    public void setWeek(WorkWeekEntity week) {
        this.week.set(week);
    }

    /**
     * A creator is used to inject the week ID into the ViewModel
     * <p>
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the week ID can be passed in a public method.
     * TODO - Simplify by passing weekId to WeekViewModel via public method
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;
        private final int mWeekId;
        private final DataRepository mRepository;

        public Factory(@NonNull Application application, int weekId) {
            mApplication = application;
            mWeekId = weekId;
            mRepository = ((FinancesApp) application).getRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new WeekViewModel(mApplication, mRepository, mWeekId);
        }
    }
}
