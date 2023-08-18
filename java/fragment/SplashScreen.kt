package com.zoltanlorinczi.project_retrofit.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.viewmodel.UserViewModel
import com.zoltanlorinczi.project_retrofit.viewmodel.UserViewModelFactory


class SplashScreen :Fragment(){

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)

        navBar?.visibility = View.GONE;

        val factory = UserViewModelFactory(ThreeTrackerRepository())
        userViewModel = ViewModelProvider(requireActivity(),factory)[UserViewModel::class.java]

        Handler().postDelayed({
            userViewModel.getMyUser()
        },2500)
        userViewModel.loggedIn.observe(viewLifecycleOwner, Observer {
            if(it == 0){
                findNavController().navigate(R.id.action_splashScreen_to_loginFragment)
            }else{
                findNavController().navigate(R.id.action_splashScreen_to_listFragment)
            }
        })

        return inflater.inflate(R.layout.fragment_splash_screen, container, false)

    }


}