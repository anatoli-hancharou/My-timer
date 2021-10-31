package ppo.timer.viewModels

import android.app.Application
import android.content.res.Resources
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ppo.timer.R
import ppo.timer.db.TimerEntity

class CDTimerViewModel(application: Application): AndroidViewModel(application) {
    private lateinit var timer: TimerEntity
    lateinit var res: Resources

    var currentText = MutableLiveData<String>("Warm-up")

    var prevText = MutableLiveData<String>("")
    var nextText = MutableLiveData<String>("Work")

    var timeRemainingText = MutableLiveData<String>("00:00")
    var timePercentRemaining = MutableLiveData<Int>(100)

    var tmr: CountDownTimer? = null
    private var timeRemaining: Long = 0
    private var timeRemainingStatic = timeRemaining
    var currIndex = 0
    private var stagesCount = 0
    var isRunning: Boolean = false

    var sequenceText = arrayListOf<String>()
    var sequenceTime = arrayListOf<Int>()

    private val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
    private val soundPool = SoundPool.Builder().setAudioAttributes(audioAttributes).build()

    fun setTimer(timer: TimerEntity) {
        this.timer = timer
        res = getApplication<Application>().resources
       // res.updateConfiguration(res.configuration, res.displayMetrics)
        setInitValues()
        soundPool.load(getApplication<Application>().applicationContext, R.raw.drink, 1)
        createSequence()
    }

    private fun setInitValues() {
        stagesCount = timer.cycles * (timer.repeats * 2 + 1)
        timeRemaining = (timer.warm_up * 1000).toLong()
        timeRemainingStatic = timeRemaining
        timeRemainingText.value = timer.warm_up.toString()
        currentText.value = res.getString(R.string.warm_up)
        nextText.value = res.getString(R.string.work_short)
        currIndex = 2
    }

    private fun createSequence() {
        sequenceText.add("")
        sequenceTime.add(timer.warm_up)

        sequenceText.add(res.getString(R.string.warm_up))
        sequenceTime.add(timer.warm_up)

        for (i in 0 until timer.cycles) {
            for (j in 0 until timer.repeats) {
                sequenceText.add(res.getString(R.string.work_short))
                sequenceText.add(res.getString(R.string.rest))
                sequenceTime.add(timer.work)
                sequenceTime.add(timer.rest)
            }

            sequenceText.add(res.getString(R.string.cooldown))
            sequenceTime.add(timer.cooldown)
        }
        sequenceText.add("")
        sequenceText.add("")
    }

    private fun getTimeRemainingText(time: Long) = (time.toInt() / 1000).toString()

    fun startTimer() {
        isRunning = true
        tmr = object : CountDownTimer(timeRemaining, 1000) {
            override fun onFinish() {
                soundPool.play(1, 1F, 1F, 1, 0, 1F)
                timePercentRemaining.value = 100

                if (currIndex < 1) currIndex = 1
                if (currIndex - 2 > stagesCount) return

                if (currIndex - 2 == stagesCount) {
                    currentText.value = res.getString(R.string.complete)
                    prevText.value = ""
                    currIndex += 1
                }
                else {
                    prevText.value = sequenceText[currIndex-1]
                    currentText.value = sequenceText[currIndex]
                    nextText.value = sequenceText[currIndex+1]

                    timeRemaining = sequenceTime[currIndex].toLong() * 1000
                    timeRemainingStatic = timeRemaining
                    timeRemainingText.value = getTimeRemainingText(timeRemaining)
                    startTimer()
                    currIndex += 1
                }
            }
            override fun onTick(millisUntilFinished: Long) {
                timeRemainingText.value = getTimeRemainingText(timeRemaining)
                timePercentRemaining.value = ((timeRemaining * 100) / timeRemainingStatic).toInt()
                timeRemaining -= 1000
            }
        }.start()
    }

    fun prev(){
        if (tmr != null) {
            currIndex -= 2
            tmr?.cancel()
            tmr?.onFinish()
        }
    }
    fun next(){
        tmr?.cancel()
        tmr?.onFinish()
    }
    fun pause() {
        isRunning = false
        tmr?.cancel()
    }
}