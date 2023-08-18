package com.zoltanlorinczi.project_retrofit.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retrofit.adapter.ActivitiesListAdapter
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.ActivityResponse
import com.zoltanlorinczi.project_retrofit.api.model.TaskResponse
import com.zoltanlorinczi.project_retrofit.viewmodel.*


class ActivityFragment : Fragment(R.layout.fragment_activity), ActivitiesListAdapter.OnItemClickListener{

    companion object {
        private val TAG: String = javaClass.simpleName
    }

    private lateinit var activityViewModel: ActivityViewModel;
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ActivitiesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ActivityViewModelFactory(ThreeTrackerRepository())
        activityViewModel = ViewModelProvider(requireActivity(), factory)[ActivityViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)

        navBar?.visibility = View.VISIBLE;


        val view = inflater.inflate(R.layout.fragment_activity, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        setupRecyclerView()
        activityViewModel.activities.observe(viewLifecycleOwner) {
            adapter.setData(activityViewModel.activities.value as ArrayList<ActivityResponse>)
            adapter.notifyDataSetChanged()
        }



        return  view
    }

    private fun setupRecyclerView() {
        adapter = ActivitiesListAdapter(ArrayList(), this.requireContext(), this)
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

    }



    override fun onResume() {
        super.onResume()
    }
}