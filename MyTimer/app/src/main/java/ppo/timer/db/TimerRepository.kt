package ppo.timer.data

import androidx.lifecycle.LiveData

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

    fun clear(){
        timerDao.clear()
    }

}