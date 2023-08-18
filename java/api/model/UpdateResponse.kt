package com.zoltanlorinczi.project_retrofit.api.model

import com.google.gson.annotations.SerializedName

data class UpdateResponse(
    @SerializedName("last_name")
    var last_name: String,

    @SerializedName("first_name")
    var first_name: String,

    @SerializedName("location")
    var location: String,

    @SerializedName("phone_number")
    var phone_number: String,

    @SerializedName("imageUrl")
    var imageUrl: String

)