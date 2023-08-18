package com.zoltanlorinczi.project_retrofit.api


object BackendConstants {

    /**
     * Project backend base URL.
     */
    const val BASE_URL = "http://tracker-3track.a2hosted.com/"

    /**
     * Specific URL segments, which will be concatenated with the base URL.
     */
    const val LOGIN_URL = "login"
    const val GET_TASKS_URL = "task/getTasks"
    const val GET_USER_URL = "users"
    const val GET_MY_USER_URL = "user"
    const val UPDATE_MY_PROFILE = "users/updateProfile"
    const val GET_ACTIVITIES_URL = "activity/getActivities"
    const val GET_GROUPS_URL = "department"
    const val CREATE_TASK = "task/create"

    /**
     * Header values.
     */
    const val HEADER_TOKEN = "token"
}