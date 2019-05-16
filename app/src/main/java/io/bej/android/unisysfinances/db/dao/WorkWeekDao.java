package io.bej.android.unisysfinances.db.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.bej.android.unisysfinances.db.entity.WorkWeekEntity;

@Dao
public interface WorkWeekDao {
    @Query("SELECT * FROM weeks")
    LiveData<List<WorkWeekEntity>> loadAllWorkWeeks();

    @Query("SELECT * FROM weeks WHERE id = :weekId")
    LiveData<WorkWeekEntity> loadWeek(int weekId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<WorkWeekEntity> workWeeks);
}
