package com.zoltanlorinczi.project_retrofit.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retrofit.api.model.UserResponse
import com.zoltanlorinczi.project_retrofit.fragment.GroupFragment
import com.zoltanlorinczi.project_retrofit.viewmodel.GroupViewModel
import com.zoltanlorinczi.project_retrofit.viewmodel.UserViewModel
import java.time.*
import java.util.*
import kotlin.collections.ArrayList


class GroupAdapter(
    private var list: ArrayList<UserResponse>,
    private val context: Context,
    private val listener: GroupFragment,
    private val listener2: GroupFragment
) :
    RecyclerView.Adapter<GroupAdapter.DataViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }



    // 1. user defined ViewHolder type - Embedded class!
    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener{
        val userName: TextView = itemView.findViewById(R.id.nameView)


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
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_group_members_item, parent, false)
        return DataViewHolder(itemView)
    }




    // 3. Called many times, when we scroll the list
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentItem = list[position]

        holder.userName.text = currentItem.first_name +" "+ currentItem.last_name


    }

    override fun getItemCount() = list.size

    // Update the list
    fun setData(newList: ArrayList<UserResponse>) {
        list = newList
    }



}