package io.bej.android.unisysfinances.db;

import android.content.Context;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import io.bej.android.unisysfinances.AppExecutors;
import io.bej.android.unisysfinances.db.converter.BigDecimalConverter;
import io.bej.android.unisysfinances.db.converter.DateConverter;
import io.bej.android.unisysfinances.db.dao.WorkDayDao;
import io.bej.android.unisysfinances.db.dao.WorkWeekDao;
import io.bej.android.unisysfinances.db.entity.WorkDayEntity;
import io.bej.android.unisysfinances.db.entity.WorkWeekEntity;

// TODO - "Schema export directory is not provided to the annotation processor so we cannot export
// the schema. You can either provide `room.schemaLocation` annotation processor argument OR set
// exportSchema to false."
@Database(entities = {WorkDayEntity.class, WorkWeekEntity.class}, version = 1)
@TypeConverters({BigDecimalConverter.class, DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "my-finances-db";

    public abstract WorkDayDao workDayDao();

    public abstract WorkWeekDao workWeekDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDatabase buildDatabase(final Context appContext,
                                             final AppExecutors executors) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(() -> {
                            // Generate the data for pre-population
                            AppDatabase database = AppDatabase.getInstance(appContext, executors);
                            List<WorkDayEntity> workDays = DataGenerator.generateWorkDays(appContext);
                            List<WorkWeekEntity> workWeeks = DataGenerator.generateWorkWeeks(workDays);

                            insertData(database, workDays, workWeeks);

                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });
                    }
                })
                .build();
    }

    private static void insertData(final AppDatabase database, final List<WorkDayEntity> workDays,
                                   final List<WorkWeekEntity> workWeeks) {
        database.runInTransaction(() -> database.workDayDao().insertAll(workDays));
        database.runInTransaction(() -> database.workWeekDao().insertAll(workWeeks));
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
