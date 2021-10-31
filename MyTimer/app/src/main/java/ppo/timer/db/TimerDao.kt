package ppo.timer.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TimerDao {
    @Query("SELECT * FROM TimerEntity")
    fun getAllTimers() : LiveData<List<TimerEntity>>

    @Query("SELECT * FROM TimerEntity WHERE id = :id")
    fun getTimer(id : Int) : LiveData<TimerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(timer: TimerEntity)

    @Update
    fun update(timer: TimerEntity)

    @Delete
    fun delete(timer: TimerEntity)

    @Query("DELETE FROM TimerEntity")
    fun clear()
}