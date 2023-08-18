package com.zoltanlorinczi.project_retrofit.api.model

import com.google.gson.annotations.SerializedName

/**
 * Author:  Zoltan Lorinczi
 * Date:    11/8/2021
 */
data class LoginRequestBody(
    @SerializedName("email")
    var email: String,
    @SerializedName("passwordKey")
    var password: String
)