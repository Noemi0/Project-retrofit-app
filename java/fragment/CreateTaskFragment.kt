package com.zoltanlorinczi.project_retrofit.fragment



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retorfit.databinding.FragmentCreateTaskBinding
import com.zoltanlorinczi.project_retrofit.App
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.CreateTaskResponse
import com.zoltanlorinczi.project_retrofit.api.model.TaskResponse
import com.zoltanlorinczi.project_retrofit.viewmodel.TasksViewModel
import com.zoltanlorinczi.project_retrofit.viewmodel.TasksViewModelFactory
import com.zoltanlorinczi.project_retrofit.viewmodel.UserViewModel
import com.zoltanlorinczi.project_retrofit.viewmodel.UserViewModelFactory

import java.util.*


class CreateTaskFragment : Fragment() {
    private lateinit var tasksViewModel: TasksViewModel
    private lateinit var usersViewModel: UserViewModel;
    private lateinit var binding: FragmentCreateTaskBinding;
    private var task: TaskResponse? = null
    var assigneeToUser :Int =0
    var priority=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = TasksViewModelFactory(ThreeTrackerRepository())
        tasksViewModel = ViewModelProvider(requireActivity(), factory)[TasksViewModel::class.java]
        val factoryUser = UserViewModelFactory(ThreeTrackerRepository())
        usersViewModel = ViewModelProvider(requireActivity(),factoryUser)[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateTaskBinding.inflate(inflater);

        binding.createBtn.setOnClickListener {
            createNewTask()
        }


        val arrayNames = mutableListOf<String>("Select a user")
        usersViewModel.users.value!!.forEach{
            arrayNames.add(it.first_name + " " + it.last_name)
        }

        val arrayAdapter = ArrayAdapter(App.context,R.layout.support_simple_spinner_dropdown_item,arrayNames)


        binding.assigneeSpinner.adapter = arrayAdapter
        binding.assigneeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                assigneeToUser = usersViewModel.users.value!!.get(p2).id
            }
        }

        val priorityList = arrayOf("HIGH","MID","LOW")

        val arrayAdapterpriority = ArrayAdapter(App.context,R.layout.support_simple_spinner_dropdown_item,priorityList)


        binding.prioritySpinner2.adapter = arrayAdapterpriority
        binding.prioritySpinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                priority = p2
                //Log.i("prioritas","${p2}")
            }
        }

        return binding.root
    }

    fun createNewTask() {
        val title = binding.taskTitle.text.toString()
        val description = binding.description.text.toString()
        val deadline = getDateTime()
        val departmentID = 2
        val status = 0
        //Log.i("assigneeto","${assigneeToUser}")

        if(title==""){
            binding.taskTitle.setError("Enter task title")
            return
        }
        if(assigneeToUser==1){
            val toast = Toast.makeText(App.context, "Select a user", Toast.LENGTH_SHORT)
            toast.show()
            return
        }

        if(description==""){
            binding.description.setError("Write description")
            return

        }
        val newTask = CreateTaskResponse(1, title, description, assigneeToUser, priority, deadline, departmentID, status);

        tasksViewModel.createTask(newTask)
        tasksViewModel.createTaskIsSuccess.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            findNavController().navigate(R.id.action_createTaskFragment_to_listFragment)
        })
    }

    fun getDateTime(): Long {
        try {

            val day = binding.datePicker1.dayOfMonth
            val month = binding.datePicker1.month
            val year = binding.datePicker1.year - 1900
            var date = Date(year,month,day)
            //Log.i("evdatum","${date}")
            return date.time
        } catch (e: Exception) {
            return 0
        }
    }


}
