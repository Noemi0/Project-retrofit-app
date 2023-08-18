package com.zoltanlorinczi.project_retrofit.api.model

import com.google.gson.annotations.SerializedName


data class ActivityResponse(
    @SerializedName("ID")
    var id: Int,

    @SerializedName("type")
    var type: Int,

    @SerializedName("sub_type")
    var sub_type: Int,

    @SerializedName("sub_ID")
    var sub_id: Int,

    @SerializedName("created_time")
    var createdTime: Long,

    @SerializedName("created_by_user_ID")
    var createdByUserID: Int,

    @SerializedName("sub_user_ID")
    var sub_user_id: Int,

    @SerializedName("note")
    var note: Int,

    @SerializedName("progress")
    var progress: Int
)
