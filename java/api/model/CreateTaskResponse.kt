package com.zoltanlorinczi.project_retrofit.api.model

import com.google.gson.annotations.SerializedName


data class CreateTaskResponse(

    @SerializedName("ID")
    var id: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("description")
    var description : String,
    @SerializedName("assigneeToUserId")
    var assigneeToUserId : Int,
    @SerializedName("priority")
    var priority: Int,
    @SerializedName("deadline")
    var deadline: Long,
    @SerializedName("departmentId")
    var departmentId : Int,
    @SerializedName("status")
    var status: Int

)