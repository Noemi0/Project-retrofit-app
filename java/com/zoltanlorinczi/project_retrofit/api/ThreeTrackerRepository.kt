package com.zoltanlorinczi.project_retrofit.api

import com.zoltanlorinczi.project_retrofit.api.model.*
import retrofit2.Response


class ThreeTrackerRepository {

    /**
     * 'suspend' keyword means that this function can be blocking.
     * We need to be aware that we can only call them from within a coroutine or another suspend function!
     */
    suspend fun login(loginRequestBody: LoginRequestBody): Response<LoginResponse> {
        return RetrofitInstance.USER_API_SERVICE.login(loginRequestBody)
    }

    suspend fun getTasks(token: String): Response<List<TaskResponse>> {
        return RetrofitInstance.TASK_API_SERVICE.getTasks(token)
    }

    suspend fun getUsers(token: String): Response<List<UserResponse>> {
        return RetrofitInstance.USER_API_SERVICE.getUsers(token)
    }

    suspend fun getMyUser(token: String): Response<UserResponse> {
        return RetrofitInstance.USER_API_SERVICE.getMyUser(token)
    }

    suspend fun updateMyProfile(token: String, updateProfileRequest: UpdateResponse): Response<Any> {
        return RetrofitInstance.USER_API_SERVICE.updateMyProfile(token,updateProfileRequest)
    }

    suspend fun getActivities(token: String): Response<List<ActivityResponse>> {
        return RetrofitInstance.ACTIVITY_API_SERVICE.getActivities(token)
    }

    suspend fun getGroups(token: String): Response<List<GroupListResponse>> {
        return RetrofitInstance.GROUP_API_SERVICE.getGroups(token)
    }

    suspend fun createTask(token: String, createTaskRequest: CreateTaskResponse): Response<Any> {
        return RetrofitInstance.TASK_API_SERVICE.createTask(token,createTaskRequest)
    }

}