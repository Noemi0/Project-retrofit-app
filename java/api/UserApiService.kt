package com.zoltanlorinczi.project_retrofit.api

import com.zoltanlorinczi.project_retrofit.api.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface UserApiService {

    @POST(BackendConstants.LOGIN_URL)
    suspend fun login(@Body loginRequest: LoginRequestBody): Response<LoginResponse>

    @GET(BackendConstants.GET_USER_URL)
    suspend fun getUsers(@Header(BackendConstants.HEADER_TOKEN) token: String): Response<List<UserResponse>>

    @GET(BackendConstants.GET_MY_USER_URL)
    suspend fun getMyUser(@Header(BackendConstants.HEADER_TOKEN) token: String): Response<UserResponse>

    @POST(BackendConstants.UPDATE_MY_PROFILE)
    suspend fun updateMyProfile(@Header(BackendConstants.HEADER_TOKEN) token: String, @Body updateProfileRequest: UpdateResponse): Response<Any>


}