package io.bej.android.unisysfinances.db.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.bej.android.unisysfinances.db.entity.WorkDayEntity;

@Dao
public interface WorkDayDao {
    @Query("SELECT * FROM days")
    LiveData<List<WorkDayEntity>> loadAllWorkDays();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<WorkDayEntity> workDays);
}
