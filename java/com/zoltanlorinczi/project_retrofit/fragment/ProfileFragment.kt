package com.zoltanlorinczi.project_retrofit.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retorfit.databinding.FragmentProfileBinding
import com.zoltanlorinczi.project_retorfit.databinding.FragmentTaskDetailBinding
import com.zoltanlorinczi.project_retrofit.App
import com.zoltanlorinczi.project_retrofit.adapter.TasksListAdapter
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.TaskResponse
import com.zoltanlorinczi.project_retrofit.api.model.UserResponse
import com.zoltanlorinczi.project_retrofit.manager.SharedPreferencesManager
import com.zoltanlorinczi.project_retrofit.viewmodel.*


class ProfileFragment : Fragment() {

    companion object {
        private val TAG: String = javaClass.simpleName
    }

    private lateinit var userViewModel: UserViewModel
    private lateinit var recycler_view: RecyclerView
    private lateinit var binding: FragmentProfileBinding
    private var profile: UserResponse? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val factory = UserViewModelFactory(ThreeTrackerRepository())
        userViewModel = ViewModelProvider(requireActivity(), factory)[UserViewModel::class.java]
        binding = FragmentProfileBinding.inflate(inflater)

        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)

        navBar?.visibility = View.VISIBLE;

        profile = userViewModel.currUser.value

        binding.firstNameTextView.text = profile?.first_name
        binding.lastNameTextView.text = profile?.last_name
        if(profile?.department_id==1){
            binding.role.setText("Info A")
        }else if(profile?.department_id==2){
            binding.role.setText("Info B")
        }else if(profile?.department_id==3){
            binding.role.setText("Info C")
        }else if(profile?.department_id==4) {
            binding.role.setText("Info D")
        }else if(profile?.department_id==5) {
            binding.role.setText("Szamtech A")
        }else if(profile?.department_id==6) {
            binding.role.setText("Szamtech B")
        }else if(profile?.department_id==7) {
            binding.role.setText("HR")
        }

        binding.emailTextView.text = profile?.email
        binding.phoneNumberTextView.text = profile?.phone_number.toString()
        binding.locationTextView.text = profile?.location
        Log.i("location", "${profile?.location}")


        if(userViewModel.currUser.value?.id == userViewModel.myUser.value?.id){
            binding.editButton.visibility= View.VISIBLE;
        }else{
            binding.editButton.visibility= View.GONE;
        }
        binding.editButton.setOnClickListener{
            edit()
        }


        return binding.root
    }

    fun edit(){
        Log.i("navigate", "naviagte() failed with exception")
        findNavController().navigate(R.id.profileUpdateFragment)
    }


}