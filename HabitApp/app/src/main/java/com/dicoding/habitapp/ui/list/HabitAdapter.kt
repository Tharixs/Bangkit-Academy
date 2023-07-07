package com.dicoding.habitapp.ui.list

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.habitapp.R
import com.dicoding.habitapp.data.Habit
import com.dicoding.habitapp.ui.detail.DetailHabitActivity
import com.dicoding.habitapp.utils.HABIT_ID
import com.dicoding.habitapp.utils.HABIT_TITLE

class HabitAdapter(
    private val onClick: (Habit) -> Unit
) : PagedListAdapter<Habit, HabitAdapter.HabitViewHolder>(DIFF_CALLBACK) {

    //TODO 8 : Create and initialize ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.habit_item, parent, false)
        return HabitViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = getItem(position) as Habit
        holder.bind(habit)
    }

    inner class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvTitle: TextView = itemView.findViewById(R.id.item_tv_title)
        private val ivPriority: ImageView = itemView.findViewById(R.id.item_priority_level)
        private val tvStartTime: TextView = itemView.findViewById(R.id.item_tv_start_time)
        private val tvMinutes: TextView = itemView.findViewById(R.id.item_tv_minutes)


        lateinit var getHabit: Habit
        fun bind(habit: Habit) {
            getHabit = habit
            tvTitle.text = habit.title
            tvStartTime.text = habit.startTime
            tvMinutes.text = habit.minutesFocus.toString()

            ivPriority.setImageResource(
                when (habit.priorityLevel) {
                    itemView.resources.getStringArray(R.array.priority_level)[0] -> R.drawable.ic_priority_high
                    itemView.resources.getStringArray(R.array.priority_level)[1] -> R.drawable.ic_priority_medium
                    else -> R.drawable.ic_priority_low
                }
            )

            itemView.setOnClickListener {
                val detailIntent = Intent(itemView.context, DetailHabitActivity::class.java)
                detailIntent.putExtra(HABIT_ID, habit.id)
                detailIntent.putExtra(HABIT_TITLE, habit.title)
                itemView.context.startActivity(detailIntent)
            }
        }

    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Habit>() {
            override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
                return oldItem == newItem
            }
        }

    }

}