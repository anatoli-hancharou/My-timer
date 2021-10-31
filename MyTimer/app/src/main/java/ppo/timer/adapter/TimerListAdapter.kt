package ppo.timer.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ppo.timer.db.TimerEntity
import ppo.timer.databinding.RecyclerviewItemBinding


class TimerListAdapter(private var timerList: List<TimerEntity>?,
                       private val onStart: (TimerEntity) -> Unit,
                       private val onDelete: (TimerEntity) -> Unit,
                       private val onEdit: (TimerEntity) -> Unit)
    : RecyclerView.Adapter<TimerListAdapter.TimerViewHolder>() {

    companion object {lateinit var binding: RecyclerviewItemBinding}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        binding = RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context))
        return TimerViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
        timerList?.get(position)?.let { holder.bind(it, onStart) }
    }

   fun setList(list: List<TimerEntity>?) {
       if (list !== null) {
           timerList = list
           notifyDataSetChanged()
       }
   }

   inner class TimerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       fun bind(timer: TimerEntity, clickListener: (TimerEntity) -> Unit) {
           binding.apply {
               textView.text = timer.name
               timerItemView.setCardBackgroundColor(Color.parseColor(timer.color))
              // timerItem.setBackgroundColor(Color.parseColor(timer.color))
               itemView.setOnClickListener { clickListener(timer) }
               editButton.setOnClickListener {
                   timerList?.get(adapterPosition)?.let { it1 -> onEdit(it1) }
               }
               deleteButton.setOnClickListener {
                   timerList?.get(adapterPosition)?.let { it1 -> onDelete(it1) }
               }
           }
       }
   }

   override fun getItemCount() = timerList?.size ?: 0
}
