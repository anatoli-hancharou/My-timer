package ppo.timer.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
import ppo.timer.db.TimerEntity
import ppo.timer.databinding.ActivityTimerBinding
import ppo.timer.viewModels.CDTimerViewModel

class TimerActivity : LocaleAwareCompatActivity() {

    private val binding: ActivityTimerBinding by lazy {
        ActivityTimerBinding.inflate(layoutInflater) }
    private val viewModel: CDTimerViewModel by lazy {
        ViewModelProvider(this).get(CDTimerViewModel::class.java) }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.setTimer(intent.getParcelableExtra<TimerEntity>("timer") as TimerEntity)

        binding.runStop.setOnClickListener{
            if (viewModel.isRunning) viewModel.pause() else viewModel.startTimer()
        }

        viewModel.currentText.observe(this, Observer<String>{
            binding.currText.text = it
        })
        viewModel.prevText.observe(this, Observer<String>{
            binding.prevText.text = it
        })
        viewModel.nextText.observe(this, Observer<String>{
            binding.nextText.text = it
        })
        viewModel.timeRemainingText.observe(this, Observer<String>{
            binding.time.text = it
        })
        viewModel.timePercentRemaining.observe(this, Observer<Int>{
            binding.pBar.progress = it
        })
        binding.next.setOnClickListener{
            viewModel.next()
        }
        binding.prev.setOnClickListener{
            viewModel.prev()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (viewModel.tmr != null) viewModel.pause()
        finish()
    }
}