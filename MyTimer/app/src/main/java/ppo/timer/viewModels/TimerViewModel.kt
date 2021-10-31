package ppo.timer.viewModels

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ppo.timer.db.TimerEntity
import ppo.timer.db.TimerRepository

class TimerViewModel(private val repo: TimerRepository) : ViewModel() {
    val allTimers: LiveData<List<TimerEntity>> = repo.allTimers

    fun insert(timer: TimerEntity) = viewModelScope.launch(Dispatchers.IO){
        repo.insertTimer(timer)
    }
    fun update(timer: TimerEntity) = viewModelScope.launch(Dispatchers.IO){
        repo.updateTimer(timer)
    }
    fun delete(timer: TimerEntity) = viewModelScope.launch(Dispatchers.IO){
        repo.deleteTimer(timer)
    }
}

class TabataViewModelFactory(private val repository: TimerRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            return TimerViewModel(repository) as T
        }
        throw IllegalArgumentException("")
    }
}
