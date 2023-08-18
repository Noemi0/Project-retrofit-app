package com.zoltanlorinczi.project_retrofit.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retorfit.databinding.FragmentProfileUpdateBinding
import com.zoltanlorinczi.project_retrofit.App
import com.zoltanlorinczi.project_retrofit.adapter.TasksListAdapter
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.TaskResponse
import com.zoltanlorinczi.project_retrofit.api.model.UpdateResponse
import com.zoltanlorinczi.project_retrofit.api.model.UserResponse
import com.zoltanlorinczi.project_retrofit.manager.SharedPreferencesManager
import com.zoltanlorinczi.project_retrofit.viewmodel.*


class ProfileUpdateFragment : Fragment() {

    companion object {
        private val TAG: String = javaClass.simpleName
    }

    private lateinit var userViewModel: UserViewModel
    private lateinit var recycler_view: RecyclerView
    private lateinit var binding: FragmentProfileUpdateBinding
    private var profile: UserResponse? = null;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = UserViewModelFactory(ThreeTrackerRepository())
        userViewModel = ViewModelProvider(requireActivity(), factory)[UserViewModel::class.java]


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileUpdateBinding.inflate(inflater, container, false)

        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)

        navBar?.visibility = View.VISIBLE;

        profile = userViewModel.currUser.value



        binding.firstNameEditText.setText(profile?.first_name)
        binding.lastNameEditText.setText(profile?.last_name)
        if(profile?.department_id==1){
            binding.roleEditText.setText("Info A")
        }else if(profile?.department_id==2){
            binding.roleEditText.setText("Info B")
        }else if(profile?.department_id==3){
            binding.roleEditText.setText("Info C")
        }else if(profile?.department_id==4) {
            binding.roleEditText.setText("Info D")
        }else if(profile?.department_id==5) {
            binding.roleEditText.setText("Szamtech A")
        }else if(profile?.department_id==6) {
            binding.roleEditText.setText("Szamtech B")
        }else if(profile?.department_id==7) {
            binding.roleEditText.setText("HR")
        }
        binding.emailEditText.setText(profile?.email)
        binding.phoneNumberEditText.setText(profile?.phone_number.toString())
        binding.locationEditText.setText(profile?.location.toString())



        binding.updateButton.setOnClickListener {
            update()
        }

        return binding.root
    }

    fun update(){
        val firstName:String = binding.firstNameEditText.text.toString();
        val lastName:String = binding.lastNameEditText.text.toString();
        val location:String =  binding.locationEditText.text.toString();
        val phoneNumber:String = binding.phoneNumberEditText.text.toString();
        Log.i("user",firstName)
        val updatedProfile = UpdateResponse(firstName,lastName,location, phoneNumber,"")

        Handler().postDelayed({
            userViewModel.updateMyProfile(updatedProfile);
        },2000);

        userViewModel.updateProfileIsSuccessful.observe(viewLifecycleOwner, Observer {
            Log.i("navigate", "back")
                findNavController().navigate(R.id.profileFragment);
            })

    }



}