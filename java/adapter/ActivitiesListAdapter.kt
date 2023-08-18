package com.zoltanlorinczi.project_retrofit.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retrofit.api.model.ActivityResponse
import com.zoltanlorinczi.project_retrofit.api.model.TaskResponse
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ActivitiesListAdapter(
    private var list: ArrayList<ActivityResponse>,
    private val context: Context,
    private val listener: OnItemClickListener,

) :
    RecyclerView.Adapter<ActivitiesListAdapter.SimpleDataViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    open inner class SimpleDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener{

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }


    }

    // 1. user defined ViewHolder type - Embedded class!
    inner class DataViewHolder(itemView: View) : SimpleDataViewHolder(itemView),
        View.OnClickListener {

        val taskName: TextView = itemView.findViewById(R.id.activitytaskName)
        val createDate: TextView = itemView.findViewById(R.id.createdDate)
        val createBy: TextView =  itemView.findViewById(R.id.taskAssignee)

        init {
            itemView.setOnClickListener(this)

        }

        override fun onClick(p0: View?) {
            val currentPosition = this.adapterPosition
            listener.onItemClick(currentPosition)

        }

    }

    // 2. Called only a few times = number of items on screen + a few more ones
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleDataViewHolder {
        return when (viewType) {
            TaskListItemType.SIMPLE.value -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.simple_task_list_item, parent, false)
                SimpleDataViewHolder(itemView)
            }
            TaskListItemType.ANNOUNCEMENT.value -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.announcement_list_item, parent, false)
                DataViewHolder(itemView)
            }
            TaskListItemType.COMPLEX.value -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.task_activity_item, parent, false)
                DataViewHolder(itemView)
            }
            else -> {
                throw IllegalStateException("Type is not supported!")
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        val currentItem = list[position]
        if(currentItem.type == 0){
            return TaskListItemType.SIMPLE.value
        }
        if(currentItem.type == 1){
            return  TaskListItemType.COMPLEX.value
        }
        return  TaskListItemType.ANNOUNCEMENT.value

    }

    // 3. Called many times, when we scroll the list
    override fun onBindViewHolder(holder: SimpleDataViewHolder, position: Int) {
        if (getItemViewType(position) == TaskListItemType.COMPLEX.value) {
            val complexHolder = (holder as DataViewHolder)
            val currentItem = list[position]



            complexHolder.createDate.text =getDateTime(currentItem.createdTime)


        }
    }

    override fun getItemCount() = list.size

    // Update the list
    fun setData(newList: ArrayList<ActivityResponse>) {
        list = newList
    }

    private enum class TaskListItemType(val value: Int) {
        SIMPLE(0),
        COMPLEX(1),
        ANNOUNCEMENT(2)
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateTime(s: Long): String {
        try {
            val date = Date(s)
            val format = SimpleDateFormat("yyyy-MM-dd")
            return format.format(date)
        } catch (e: Exception) {
            return e.toString()
        }
    }


}