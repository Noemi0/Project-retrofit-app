package com.zoltanlorinczi.project_retrofit.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.TaskResponse
import com.zoltanlorinczi.project_retrofit.viewmodel.UserViewModel
import com.zoltanlorinczi.project_retrofit.viewmodel.UserViewModelFactory
import java.text.SimpleDateFormat
import java.time.*
import java.util.*
import kotlin.collections.ArrayList


class TasksListAdapter(
    private var list: ArrayList<TaskResponse>,
    private val context: FragmentActivity,
    private val listener: OnItemClickListener

) :
        RecyclerView.Adapter<TasksListAdapter.DataViewHolder>() {

    private lateinit var userViewModel: UserViewModel

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }


    // 1. user defined ViewHolder type - Embedded class!
    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {
       // val taskPtojectTypeView: TextView = itemView.findViewById(R.id.task_project_view)
        val taskAssigneeView: TextView = itemView.findViewById(R.id.task_assignee_view)
        val taskTitleTextView: TextView = itemView.findViewById(R.id.taskTitleView)
        val taskDeadline: TextView = itemView.findViewById(R.id.task_deadline_view)
        val taskDescriptionTextView: TextView = itemView.findViewById(R.id.task_description_view)
        val taskPriorityTextView: TextView = itemView.findViewById(R.id.task_priority_view)
        val taskStatusTextView: TextView = itemView.findViewById(R.id.task_status_view)
        val taskHourTextView: TextView = itemView.findViewById(R.id.task_hour_view)
        init {
            itemView.setOnClickListener(this)

        }

        override fun onClick(p0: View?) {
            val currentPosition = this.adapterPosition
            listener.onItemClick(currentPosition)

        }


    }

    // 2. Called only a few times = number of items on screen + a few more ones
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.tasks_list_item, parent, false)

        val factory = UserViewModelFactory(ThreeTrackerRepository())
        userViewModel = ViewModelProvider(context,factory)[UserViewModel::class.java]
        return DataViewHolder(itemView)
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateTime(s: Long): String? {
        try {
            val date = Date(s)
            val format = SimpleDateFormat("yyyy-MM-dd")
            return format.format(date)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    fun getTime(s: Long): String? {
        try {
            val sdf = SimpleDateFormat("HH:mm:ss")
            val netDate = Date(s.toLong() * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    // 3. Called many times, when we scroll the list
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentItem = list[position]

        holder.taskTitleTextView.text = currentItem.title
        holder.taskDescriptionTextView.text = currentItem.description
        holder.taskAssigneeView.text = getCreatedByName(currentItem.createdByUserID);
        holder.taskHourTextView.text = getTime(currentItem.createdTime)
        holder.taskDeadline.text = getDateTime(currentItem.deadline)


        if(currentItem.status == 0){
            holder.taskStatusTextView.setText("NEW")
        }else if (currentItem.status == 1){
            holder.taskStatusTextView.setText("IN PROGRESS")
        }else if(currentItem.status == 2){
            holder.taskStatusTextView.setText("DONE")
        }else if(currentItem.status == 3){
            holder.taskStatusTextView.setText("BLOCKED")
        }

        if (currentItem.priority == 0) {
            holder.taskPriorityTextView.setBackgroundColor(Color.RED)
            holder.taskPriorityTextView.setText("High")
        } else if (currentItem.priority == 1) {
            holder.taskPriorityTextView.setBackgroundColor(Color.YELLOW)
            holder.taskPriorityTextView.setText("Mid")
        } else if (currentItem.priority == 2) {
            holder.taskPriorityTextView.setBackgroundColor(Color.GREEN)
            holder.taskPriorityTextView.setText("Low")
        }

//        Glide.with(this.context)
//            .load(R.drawable.ic_launcher_background)
//            .override(200, 200)
//            .into(holder.imageView);
    }

    override fun getItemCount() = list.size

    // Update the list
    fun setData(newList: ArrayList<TaskResponse>) {
        list = newList
    }

    fun getCreatedByName(id:Int) : String{
        for (item in userViewModel.users.value!!){
            if(item.id == id){
                return item.first_name + " " + item.last_name
            }
        }
        return "None"
    }

}