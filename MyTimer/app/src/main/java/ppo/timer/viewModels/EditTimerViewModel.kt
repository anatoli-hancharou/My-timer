package ppo.timer.viewModels

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ppo.timer.db.TimerEntity
import ppo.timer.databinding.FragmentTimerEditBinding
import java.lang.String.*

class EditTimerViewModel: ViewModel() {

    private var timer = MutableLiveData<TimerEntity>()
    var isNewTimer = false

    fun setTimer(timer: TimerEntity) {
        this.timer.value = timer
        isNewTimer = false
    }

    fun generateTimerForEdit(binding: FragmentTimerEditBinding){
        if (!isNewTimer) {
            val curr: TimerEntity = timer.value!!
            binding.apply {
                timerTitle.setText(curr.name)
                warmUpSeconds.setText(curr.warm_up.toString())
                workSeconds.setText(curr.work.toString())
                restSeconds.setText(curr.rest.toString())
                cooldownSeconds.setText(curr.cooldown.toString())
                repeats.setText(curr.repeats.toString())
                cycles.setText(curr.cycles.toString())
                repeats.setText(curr.repeats.toString())
                selectColor.setBackgroundColor(Color.parseColor(curr.color))
            }
        }
    }

    fun saveTimer(binding: FragmentTimerEditBinding, viewModel: TimerViewModel) {
        binding.apply {
            val name = timerTitle.text.toString()
            val warm_up = warmUpSeconds.text.toString().toInt()
            val work = workSeconds.text.toString().toInt()
            val rest = restSeconds.text.toString().toInt()
            val cooldown = cooldownSeconds.text.toString().toInt()
            val repeats = repeats.text.toString().toInt()
            val cycles = cycles.text.toString().toInt()
            val color = format("#%06X", 0xFFFFFF and (selectColor.background as ColorDrawable).color)

            val currTimer = TimerEntity(name, color, warm_up, work, rest, repeats, cycles, cooldown)

            if (isNewTimer)
                viewModel.insert(currTimer)
            else {
                currTimer.id = timer.value!!.id
                viewModel.update(currTimer)
            }
        }
    }
}