package com.example.time_project.bean

import com.google.gson.annotations.SerializedName

data class Share(
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("description")
    val description: String? = ""
)
