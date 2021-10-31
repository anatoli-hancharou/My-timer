package ppo.timer.db

import androidx.lifecycle.LiveData
import ppo.timer.db.TimerDao
import ppo.timer.db.TimerEntity

class TimerRepository(private val timerDao : TimerDao) {
    val allTimers : LiveData<List<TimerEntity>> = timerDao.getAllTimers()

    fun insertTimer(timer: TimerEntity){
        timerDao.insert(timer)
    }

    fun updateTimer(timer: TimerEntity){
        timerDao.update(timer)
    }

    fun deleteTimer(timer: TimerEntity){
        timerDao.delete(timer)
    }
}