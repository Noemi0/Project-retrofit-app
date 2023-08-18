package com.zoltanlorinczi.project_retrofit.api

import com.zoltanlorinczi.project_retrofit.api.model.ActivityResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ActivityApiService {

    @GET(BackendConstants.GET_ACTIVITIES_URL)
    suspend fun getActivities(@Header(BackendConstants.HEADER_TOKEN) token: String): Response<List<ActivityResponse>>


}