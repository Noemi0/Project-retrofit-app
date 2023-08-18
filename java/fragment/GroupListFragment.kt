package com.zoltanlorinczi.project_retrofit.fragment

import android.os.Bundle
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
import com.zoltanlorinczi.project_retorfit.databinding.FragmentGroupListBinding
import com.zoltanlorinczi.project_retrofit.adapter.GroupListAdapter
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.GroupListResponse
import com.zoltanlorinczi.project_retrofit.viewmodel.*

class GroupListFragment : Fragment(R.layout.fragment_group_list), GroupListAdapter.OnItemClickListener{

    companion object {
        private val TAG: String = javaClass.simpleName
    }
    private lateinit var binding: FragmentGroupListBinding;
    private lateinit var groupViewModel: GroupViewModel;
    private lateinit var userViewModel: UserViewModel;
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GroupListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = GroupViewModelFactory(ThreeTrackerRepository())
        groupViewModel = ViewModelProvider(requireActivity(), factory)[GroupViewModel::class.java]
        val factoryUser = UserViewModelFactory(ThreeTrackerRepository());
        userViewModel = ViewModelProvider(requireActivity(), factoryUser)[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)

        navBar?.visibility = View.VISIBLE;
        binding = FragmentGroupListBinding.inflate(inflater)
        recyclerView = binding.recyclergroupView
        setupRecyclerView()
        userViewModel.getUsers()
        groupViewModel.groups.observe(viewLifecycleOwner) {
            Log.d(TAG, "Tasks list = $it")
            adapter.setData(groupViewModel.groups.value as ArrayList<GroupListResponse>)
            adapter.notifyDataSetChanged()
        }

        return binding.root;
    }

    private fun setupRecyclerView() {
        adapter = GroupListAdapter(ArrayList(), this.requireContext(), this)
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
        groupViewModel.ID = position;
        findNavController().navigate(R.id.groupFragment);
    }


    override fun onResume() {
        groupViewModel.fetchGroups();
        super.onResume()
    }
}