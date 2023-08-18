package com.zoltanlorinczi.project_retrofit.api

import com.zoltanlorinczi.project_retrofit.api.model.CreateTaskResponse
import com.zoltanlorinczi.project_retrofit.api.model.TaskResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TaskApiService {
    @POST(BackendConstants.CREATE_TASK)
    suspend fun createTask(@Header(BackendConstants.HEADER_TOKEN) token: String, @Body createTaskRequest: CreateTaskResponse): Response<Any>

    @GET(BackendConstants.GET_TASKS_URL)
    suspend fun getTasks(@Header(BackendConstants.HEADER_TOKEN) token: String): Response<List<TaskResponse>>
}