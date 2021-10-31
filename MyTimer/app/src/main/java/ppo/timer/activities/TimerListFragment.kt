package ppo.timer.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ppo.timer.R
import ppo.timer.db.TimerEntity
import ppo.timer.viewModels.TimerViewModel
import ppo.timer.viewModels.TabataViewModelFactory
import ppo.timer.databinding.FragmentTimerListBinding
import ppo.timer.utility.TimerApp
import ppo.timer.adapter.TimerListAdapter
import ppo.timer.viewModels.EditTimerViewModel


class TimerListFragment : Fragment() {

    private val binding: FragmentTimerListBinding by lazy { FragmentTimerListBinding.inflate(layoutInflater) }
    private val viewModel: EditTimerViewModel by activityViewModels()
    private val timerViewModel: TimerViewModel by viewModels {
        TabataViewModelFactory((activity?.application as TimerApp).repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = TimerListAdapter (timerViewModel.allTimers.value,
            { timer -> onItemStartClick(timer) },
            { timer -> onItemDeleteClick(timer) },
            { timer -> onItemEditClick(timer) })
        binding.recyclerview.adapter = adapter

        timerViewModel.allTimers.observe(viewLifecycleOwner, Observer { timers ->
            timers?.let { adapter.setList(it) }
        })
        binding.fab.setOnClickListener {
            viewModel.isNewTimer = true
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun onItemStartClick(timer: TimerEntity) {
        val intent = Intent(activity, TimerActivity::class.java)
        intent.putExtra("timer", timer)
        activity?.startActivity(intent)
    }

    private fun onItemEditClick(timer: TimerEntity) {
        viewModel.setTimer(timer)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }

    private fun onItemDeleteClick(timer: TimerEntity) {
        timerViewModel.delete(timer)
    }
}