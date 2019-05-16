package io.bej.android.unisysfinances;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import io.bej.android.unisysfinances.db.AppDatabase;
import io.bej.android.unisysfinances.db.entity.WorkDayEntity;
import io.bej.android.unisysfinances.db.entity.WorkWeekEntity;
import io.bej.android.unisysfinances.model.WorkDay;

public class DataRepository {

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<WorkDayEntity>> mObservableWorkDays;
    private MediatorLiveData<List<WorkWeekEntity>> mObservableWorkWeeks;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;

        mObservableWorkDays = new MediatorLiveData<>();
        mObservableWorkDays.addSource(mDatabase.workDayDao().loadAllWorkDays(),
                workDayEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableWorkDays.postValue(workDayEntities);
                    }
                });

        mObservableWorkWeeks = new MediatorLiveData<>();
        mObservableWorkWeeks.addSource(mDatabase.workWeekDao().loadAllWorkWeeks(),
                workWeekEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableWorkWeeks.postValue(workWeekEntities);
                    }
                });
    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<WorkDayEntity>> getWorkDays() {
        return mObservableWorkDays;
    }

    public LiveData<List<WorkWeekEntity>> getWorkWeeks() { return mObservableWorkWeeks; }

    public LiveData<WorkWeekEntity> loadWeek(final int weekId) {
        return mDatabase.workWeekDao().loadWeek(weekId);
    }
}

/*
    public LiveData<ProductEntity> loadProduct(final int productId) {
        return mDatabase.productDao().loadProduct(productId);
    }

    public LiveData<List<CommentEntity>> loadComments(final int productId) {
        return mDatabase.commentDao().loadComments(productId);
    }

    public LiveData<List<ProductEntity>> searchProducts(String query) {
        return mDatabase.productDao().searchAllProducts(query);
    }
*/