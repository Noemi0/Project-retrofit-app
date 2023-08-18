package com.zoltanlorinczi.project_retrofit.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zoltanlorinczi.project_retrofit.App
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.ActivityResponse
import com.zoltanlorinczi.project_retrofit.api.model.TaskResponse
import com.zoltanlorinczi.project_retrofit.api.model.UserResponse
import com.zoltanlorinczi.project_retrofit.manager.SharedPreferencesManager
import kotlinx.coroutines.launch


class ActivityViewModel(private val repository: ThreeTrackerRepository) : ViewModel() {

    companion object {
        private val TAG: String = javaClass.simpleName
    }

    var activities: MutableLiveData<List<ActivityResponse>> = MutableLiveData()
    var currActivity: MutableLiveData<ActivityResponse> = MutableLiveData()

    init {
        getActivities()
    }

    fun getActivities() {
        viewModelScope.launch {
            try {
                val token: String? = App.sharedPreferences.getStringValue(
                    SharedPreferencesManager.KEY_TOKEN,
                    "Empty token!"
                )
                val response = token?.let {
                    repository.getActivities(it)
                }

                if (response?.isSuccessful == true) {
                    Log.d("Activities", "Get activities response: ${response.body()}")

                    val activityList = response.body()
                    activityList?.let {
                        activities.value = activityList
                    }
                } else {
                    Log.d("Activities", "Get activities error response: ${response?.errorBody()}")
                }

            } catch (e: Exception) {
                Log.d("Activities", "ActivityViewModel - getActivities() failed with exception: ${e.message}")
            }
        }
    }
}