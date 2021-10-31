package ppo.timer.activities

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import ppo.timer.R
import ppo.timer.viewModels.TimerViewModel
import ppo.timer.viewModels.TabataViewModelFactory
import ppo.timer.databinding.FragmentTimerEditBinding
import ppo.timer.utility.TimerApp
import ppo.timer.viewModels.EditTimerViewModel


class TimerEditFragment : Fragment(){

    private val binding by lazy { FragmentTimerEditBinding.inflate(layoutInflater) }
    private val viewModel: EditTimerViewModel by activityViewModels()
    private val timerViewModel: TimerViewModel by viewModels {
        TabataViewModelFactory((activity?.application as TimerApp).repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.generateTimerForEdit(binding)

        binding.buttonSave.setOnClickListener {
            viewModel.saveTimer(binding, timerViewModel)
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.apply {
            plusCycle.setOnClickListener{ cycles.setText((cycles.text.toString().toInt() + 1).toString()) }
            minusCycle.setOnClickListener{
                val cyclesNum = cycles.text.toString().toInt()
                cycles.setText(if (cyclesNum > 1) (cyclesNum-1).toString() else cyclesNum.toString() )
            }
            plusWarmUp.setOnClickListener{ warmUpSeconds.setText((warmUpSeconds.text.toString().toInt() + 1).toString()) }
            minusWarmUp.setOnClickListener{
                val warmUpSecs = warmUpSeconds.text.toString().toInt()
                warmUpSeconds.setText(if (warmUpSecs > 10) (warmUpSecs-1).toString() else warmUpSecs.toString() )
            }
            plusWork.setOnClickListener{ workSeconds.setText((workSeconds.text.toString().toInt() + 1).toString()) }
            minusWork.setOnClickListener{
                val workSecs = workSeconds.text.toString().toInt()
                workSeconds.setText(if (workSecs > 10) (workSecs-1).toString() else workSecs.toString() )
            }
            plusRest.setOnClickListener{ restSeconds.setText((restSeconds.text.toString().toInt() + 1).toString()) }
            minusRest.setOnClickListener{
                val restSecs = restSeconds.text.toString().toInt()
                restSeconds.setText(if (restSecs > 10) (restSecs-1).toString() else restSecs.toString() )
            }
            plusRepeats.setOnClickListener{ repeats.setText((repeats.text.toString().toInt() + 1).toString()) }
            minusRepeats.setOnClickListener{
                val reps = repeats.text.toString().toInt()
                repeats.setText(if (reps > 0) (reps-1).toString() else reps.toString() )
            }
            plusCooldown.setOnClickListener{ cooldownSeconds.setText((cooldownSeconds.text.toString().toInt() + 1).toString()) }
            minusCooldown.setOnClickListener{
                val cooldownSecs = cooldownSeconds.text.toString().toInt()
                cooldownSeconds.setText(if (cooldownSecs > 10) (cooldownSecs-1).toString() else cooldownSecs.toString() )
            }
            selectColor.setOnClickListener {
                createColorPickerDialog((binding.selectColor.background as ColorDrawable).color)
            }
        }
    }

    private fun createColorPickerDialog(defaultColor: Int){
        context?.let {
            MaterialColorPickerDialog
                .Builder(it)
                .setColorShape(ColorShape.SQAURE)
                .setColorSwatch(ColorSwatch._700)
                .setDefaultColor(defaultColor)
                .setColorListener { color, _ -> binding.selectColor.setBackgroundColor(color) }
                .show()
        }
    }
}