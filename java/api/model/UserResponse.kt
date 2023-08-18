package com.zoltanlorinczi.project_retrofit.api.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("ID")
    var id: Int,

    @SerializedName("last_name")
    var last_name: String,

    @SerializedName("first_name")
    var first_name: String,

    @SerializedName("email")
    var email: String,

    @SerializedName("type")
    var type: Int,

    @SerializedName("location")
    var location: String,

    @SerializedName("phone_number")
    var phone_number: String,

    @SerializedName("department_id")
    var department_id: Int,

    @SerializedName("image")
    var image: String

    )