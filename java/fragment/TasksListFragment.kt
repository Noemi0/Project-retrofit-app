package com.zoltanlorinczi.project_retrofit.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retorfit.databinding.FragmentTasksListBinding
import com.zoltanlorinczi.project_retrofit.adapter.TasksListAdapter
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.TaskResponse
import com.zoltanlorinczi.project_retrofit.viewmodel.TasksViewModel
import com.zoltanlorinczi.project_retrofit.viewmodel.TasksViewModelFactory



class TasksListFragment : Fragment(R.layout.fragment_tasks_list), TasksListAdapter.OnItemClickListener{

    companion object {
        private val TAG: String = javaClass.simpleName
    }

    private lateinit var tasksViewModel: TasksViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TasksListAdapter
    private lateinit var binding: FragmentTasksListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = TasksViewModelFactory(ThreeTrackerRepository())
        tasksViewModel = ViewModelProvider(requireActivity(), factory)[TasksViewModel::class.java]
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)

        navBar?.visibility = View.VISIBLE;

        binding = FragmentTasksListBinding.inflate(inflater);

        recyclerView = binding.recyclerView;
        setupRecyclerView()

        tasksViewModel.products.observe(viewLifecycleOwner) {
            adapter.setData(tasksViewModel.products.value as ArrayList<TaskResponse>)
            adapter.notifyDataSetChanged()
        }

        binding.createButton.setOnClickListener {
            findNavController().navigate(R.id.createTaskFragment);
        }

        return binding.root;


    }

    private fun setupRecyclerView() {
        adapter = TasksListAdapter(ArrayList(), requireActivity(), this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.addItemDecoration(
                DividerItemDecoration(
                        activity,
                        DividerItemDecoration.VERTICAL
                )
        )
        recyclerView.setHasFixedSize(true)
    }

    override fun onItemClick(position: Int) {
        tasksViewModel.pos = position
        findNavController().navigate(R.id.taskDetailFragment)
    }

    override fun onResume() {
        tasksViewModel.getTasks();
        super.onResume()
    }


}