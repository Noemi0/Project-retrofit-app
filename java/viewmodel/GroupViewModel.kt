package com.zoltanlorinczi.project_retrofit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zoltanlorinczi.project_retrofit.App
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.GroupListResponse
import com.zoltanlorinczi.project_retrofit.manager.SharedPreferencesManager
import kotlinx.coroutines.launch

class GroupViewModel(private val repository: ThreeTrackerRepository) : ViewModel() {

    companion object {
        private val TAG: String = javaClass.simpleName
    }

    var groups: MutableLiveData<List<GroupListResponse>> = MutableLiveData()
    var getGroupsIsSuccess: MutableLiveData<Boolean> = MutableLiveData();
    var ID: Int = 0;

    public fun fetchGroups(){
        viewModelScope.launch {
            try {
                val token: String? = App.sharedPreferences.getStringValue(
                    SharedPreferencesManager.KEY_TOKEN,
                    "Empty token!"
                )
                val response = token?.let {
                    repository.getGroups(it)
                }

                if (response?.isSuccessful == true) {

                    getGroupsIsSuccess.value = true;
                    val groupList = response.body()
                    groupList?.let {
                        groups.value = groupList
                    }
                } else {
                    getGroupsIsSuccess.value = false
                    groups.value = emptyList();
                }

            } catch (e: Exception) {
                getGroupsIsSuccess.value = false;
            }
        }
    }

}