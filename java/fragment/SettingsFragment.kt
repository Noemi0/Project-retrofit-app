package com.zoltanlorinczi.project_retrofit.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retorfit.databinding.FragmentProfileBinding
import com.zoltanlorinczi.project_retorfit.databinding.FragmentSettingsBinding
import com.zoltanlorinczi.project_retrofit.App
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.UserResponse
import com.zoltanlorinczi.project_retrofit.manager.SharedPreferencesManager
import com.zoltanlorinczi.project_retrofit.viewmodel.UserViewModel
import com.zoltanlorinczi.project_retrofit.viewmodel.UserViewModelFactory

class SettingsFragment :Fragment(R.layout.fragment_settings){


    companion object {
        private val TAG: String = javaClass.simpleName
    }

    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater)
        val factory = UserViewModelFactory(ThreeTrackerRepository())
        userViewModel = ViewModelProvider(requireActivity(), factory)[UserViewModel::class.java];

        binding.logOutButton.setOnClickListener{
            App.sharedPreferences.putStringValue(SharedPreferencesManager.KEY_TOKEN,"LogOut");
            findNavController().navigate(R.id.loginFragment)
        }

        binding.goToProfile.setOnClickListener{
            userViewModel.currUser.value = userViewModel.myUser.value;
            findNavController().navigate(R.id.profileFragment);
        }

        return binding.root

    }

}