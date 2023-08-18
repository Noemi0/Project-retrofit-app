package com.zoltanlorinczi.project_retrofit.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retrofit.adapter.GroupAdapter
import com.zoltanlorinczi.project_retrofit.adapter.GroupListAdapter
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.GroupListResponse
import com.zoltanlorinczi.project_retrofit.api.model.TaskResponse
import com.zoltanlorinczi.project_retrofit.api.model.UserResponse
import com.zoltanlorinczi.project_retrofit.viewmodel.*
import kotlinx.android.synthetic.main.fragment_group_members_list.*


class GroupFragment : Fragment(R.layout.fragment_group_members_list), GroupListAdapter.OnItemClickListener {

    companion object {
        private val TAG: String = javaClass.simpleName
    }
    private lateinit var groupViewModel: GroupViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GroupAdapter
    private var currentUsers = ArrayList<UserResponse>()
    private var group: GroupListResponse? = null


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
        val view = inflater.inflate(R.layout.fragment_group_members_list, container, false)
        recyclerView = view.findViewById(R.id.recyclergroupMemberView)
        setupRecyclerView()

        Log.i("ItemUser",userViewModel.users.value!!.size.toString())
        if(userViewModel.users.value != null){
            for (item in userViewModel.users.value!!) {
                Log.i("ItemUser",item.department_id.toString())
                if (item.department_id == groupViewModel.ID){
                    Log.i("ItemUser","Found")
                    currentUsers.add(item)
                }
            }
        }
        val noFoundTtext:TextView = view.findViewById(R.id.noUserFoundText)
        val notFoundPic:ImageView = view.findViewById(R.id.noMemberImg)
        var taskTitle: TextView = view.findViewById(R.id.groupTitle)

        group = groupViewModel.groups.value?.get(groupViewModel.ID)
        taskTitle.text = getGroupName(group?.id!!)

        if(currentUsers.size == 0){
            noFoundTtext.visibility = View.VISIBLE
            notFoundPic.visibility = View.VISIBLE
        }else{
            noFoundTtext.visibility =View.GONE
            notFoundPic.visibility = View.GONE

        }
        Log.i("Size",currentUsers.size.toString())
        Log.i("Size",groupViewModel.ID.toString())
        userViewModel.users.observe(viewLifecycleOwner) {
            adapter.setData(currentUsers as ArrayList<UserResponse>)
            adapter.notifyDataSetChanged()
            if(userViewModel.users.value!!.isEmpty()){

            }
        }
        return view;
    }

    private fun setupRecyclerView() {
        adapter = GroupAdapter(ArrayList(), this.requireContext(), this, this)
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
        userViewModel.currUser.value = currentUsers[position]
        findNavController().navigate(R.id.profileFragment)
    }


    override fun onResume() {
        groupViewModel.fetchGroups();
        super.onResume()
    }

    fun getGroupName(id:Int) : String{
        for (item in groupViewModel.groups.value!!){
            if(item.id == id){
                return item.name
            }
        }
        return "None"
    }
}