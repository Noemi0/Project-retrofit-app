package com.zoltanlorinczi.project_retrofit.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zoltanlorinczi.project_retrofit.App
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.UpdateResponse
import com.zoltanlorinczi.project_retrofit.api.model.UserResponse
import com.zoltanlorinczi.project_retrofit.manager.SharedPreferencesManager
import kotlinx.coroutines.launch

class UserViewModel(private val repository: ThreeTrackerRepository) : ViewModel() {

    companion object {
        private val TAG: String = javaClass.simpleName
    }


    var users: MutableLiveData<List<UserResponse>> = MutableLiveData()
    var currUser: MutableLiveData<UserResponse> = MutableLiveData()
    var loggedIn:MutableLiveData<Int> = MutableLiveData()
    var updateProfileIsSuccessful: MutableLiveData<Int> = MutableLiveData();
    var myUser: MutableLiveData<UserResponse> = MutableLiveData();
    init {
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch {
            try {
                val token: String? = App.sharedPreferences.getStringValue(
                    SharedPreferencesManager.KEY_TOKEN,
                    "Empty token!"
                )
                val response = token?.let {
                    repository.getUsers(it)
                }

                if (response?.isSuccessful == true) {
                    Log.d(TAG, "Get users response: ${response.body()}")

                    val userList = response.body()
                    userList?.let {
                        users.value = userList
                    }
                } else {
                    Log.d(TAG, "Get users error response: ${response?.errorBody()}")
                }

            } catch (e: Exception) {
                Log.d(TAG, "UserViewModel - getUsers() failed with exception: ${e.message}")
                users.value = emptyList();

            }
        }
    }

     fun getMyUser() {
        viewModelScope.launch {
            try {
                val token: String? = App.sharedPreferences.getStringValue(
                    SharedPreferencesManager.KEY_TOKEN,
                    "Empty token!"
                )
                val response = token?.let {
                    repository.getMyUser(it)
                }

                if (response?.isSuccessful == true) {
                    loggedIn.value = 1;
                    val user = response.body()
                    user?.let {
                        myUser.value = user
                    }
                } else {
                    loggedIn.value = 0;
                    Log.d("MyUser", "Get MyUser error response: ${response?.errorBody()}")
                }

            } catch (e: Exception) {
                loggedIn.value = 0
                Log.d("MyUser", "UserViewModel - getMyUser() failed with exception: ${e.message}")
            }
        }
    }

    fun updateMyProfile(updateProfileRequest: UpdateResponse) {
        viewModelScope.launch {
            try {
                val token: String? = App.sharedPreferences.getStringValue(
                    SharedPreferencesManager.KEY_TOKEN,
                    "Empty token!"
                )
                val response = token?.let {
                    repository.updateMyProfile(it,updateProfileRequest)
                }

                if (response?.isSuccessful == true) {
                    Log.d("UpdateUser", "Get update user response: ${response.body()}")
                    getMyUser()
                    updateProfileIsSuccessful.value=1
                } else {
                    Log.d("UpdateUser", "Get update user error response: ${response?.errorBody()}")
                    updateProfileIsSuccessful.value = 0
                }

            } catch (e: Exception) {
                loggedIn.value = 0
                Log.d("UpdateUser", "UserViewModel - UpdateMyProfile() failed with exception: ${e.message}")
                updateProfileIsSuccessful.value = 0
            }
            updateProfileIsSuccessful.value =0
        }
    }

}