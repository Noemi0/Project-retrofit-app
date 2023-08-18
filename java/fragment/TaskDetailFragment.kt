package com.zoltanlorinczi.project_retrofit.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retorfit.databinding.FragmentTaskDetailBinding
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.TaskResponse
import com.zoltanlorinczi.project_retrofit.viewmodel.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log


class TaskDetailFragment : Fragment(R.layout.fragment_task_detail) {

    companion object {
        private val TAG: String = javaClass.simpleName
    }

    private lateinit var tasksViewModel: TasksViewModel
    private  lateinit var binding: FragmentTaskDetailBinding;
    private var task: TaskResponse? = null;
    private lateinit var userViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = TasksViewModelFactory(ThreeTrackerRepository())
        tasksViewModel = ViewModelProvider(requireActivity(), factory)[TasksViewModel::class.java]
        val factory2 = UserViewModelFactory(ThreeTrackerRepository())
        userViewModel = ViewModelProvider(requireActivity(),factory)[UserViewModel::class.java]

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
        navBar?.visibility = View.VISIBLE

        binding = FragmentTaskDetailBinding.inflate(inflater)

        task = tasksViewModel.products.value?.get(tasksViewModel.pos);

        TaskDetail()


        return binding.root
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

    fun TaskDetail(){

            binding.descriptionEditText.text = task?.description
            binding.detailTitle.text = task?.title
            binding.assignedDate.text =getDateTime(task?.createdTime!!)
            binding.assigneeText.text = getCreatedByName(task?.createdByUserID!!)
            //binding.assignedByName1.text = getCreatedByName(task?.assignedToUserID!!)
            //Log.d("userid", "${task?.assignedToUserID!!}")


        if(task?.departmentID==1){
                binding.projectType.setText("Info A project")
            }else if(task?.departmentID==2){
                binding.projectType.setText("Info B project")
            }else if(task?.departmentID==3){
                binding.projectType.setText("Info C project")
            }else if(task?.departmentID==4) {
                binding.projectType.setText("Info D project")
            }else if(task?.departmentID==5) {
                binding.projectType.setText("Szamtech A project")
            }else if(task?.departmentID==6) {
                binding.projectType.setText("Szamtech B project")
            }else if(task?.departmentID==7) {
                binding.projectType.setText("HR project")
            }

            if (task?.priority == 0) {
                binding.priotityText.setBackgroundColor(Color.RED)
                binding.priotityText.text = "High";
            } else if (task?.priority == 1) {
                binding.priotityText.setBackgroundColor(Color.YELLOW)
                binding.priotityText.text = "Mid";
            } else if (task?.priority == 2) {
                binding.priotityText.setBackgroundColor(Color.GREEN)
                binding.priotityText.text = "Low";
            }

        if(task!!.status == 0){
            binding.taskStatusView2.setText("NEW")
        }else if (task!!.status == 1){
            binding.taskStatusView2.setText("IN PROGRESS")
        }else if(task!!.status == 2){
            binding.taskStatusView2.setText("DONE")
        }else if(task!!.status == 3){
            binding.taskStatusView2.setText("BLOCKED")
        }

         binding.deadlineText.text =getDateTime(task?.deadline!!)


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