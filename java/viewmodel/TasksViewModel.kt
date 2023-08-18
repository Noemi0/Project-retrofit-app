package com.zoltanlorinczi.project_retrofit.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zoltanlorinczi.project_retrofit.App
import com.zoltanlorinczi.project_retrofit.api.ThreeTrackerRepository
import com.zoltanlorinczi.project_retrofit.api.model.CreateTaskResponse
import com.zoltanlorinczi.project_retrofit.api.model.TaskResponse
import com.zoltanlorinczi.project_retrofit.manager.SharedPreferencesManager
import kotlinx.coroutines.launch


class TasksViewModel(private val repository: ThreeTrackerRepository) : ViewModel() {

    companion object {
        private val TAG: String = javaClass.simpleName
    }

    var products: MutableLiveData<List<TaskResponse>> = MutableLiveData()
    var pos: Int = 0
    var createTaskIsSuccess: MutableLiveData<Boolean> = MutableLiveData();

    init {
        getTasks()
    }

    fun getTasks() {
        viewModelScope.launch {
            try {
                val token: String? = App.sharedPreferences.getStringValue(
                        SharedPreferencesManager.KEY_TOKEN,
                        "Empty token!"
                )
                val response = token?.let {
                    repository.getTasks(it)
                }

                if (response?.isSuccessful == true) {
                    Log.d("Tasks", "Get tasks response: ${response.body()}")

                    val tasksList = response.body()
                    tasksList?.let {
                        products.value = tasksList
                    }
                } else {
                    products.value = emptyList();
                    Log.d("Tasks", "Get tasks error response: ${response?.errorBody()}")
                }

            } catch (e: Exception) {
                products.value = null;
                Log.d("Tasks", "TasksViewModel - getTasks() failed with exception: ${e.message}")
            }
        }
    }

    public fun createTask(newTask: CreateTaskResponse){
        viewModelScope.launch {
            try {
                val token: String? = App.sharedPreferences.getStringValue(
                    SharedPreferencesManager.KEY_TOKEN,
                    "Empty token!"
                )
                val response = token?.let {
                    repository.createTask(it,newTask);
                }
                if (response?.isSuccessful == true) {
                    getTasks();
                    createTaskIsSuccess.value = true;
                } else {
                    createTaskIsSuccess.value = false;
                    Log.d("Task","Error")
                }

            }catch (e: Exception) {
                Log.d("Task","${e.message}")
                createTaskIsSuccess.value = false;
            }
        }
        createTaskIsSuccess.value = null;
    }
}