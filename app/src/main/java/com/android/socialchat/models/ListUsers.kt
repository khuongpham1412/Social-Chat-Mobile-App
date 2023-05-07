package com.android.socialchat.models

import com.google.gson.annotations.SerializedName

data class ListUsers (
    @SerializedName("_id")
    val _id:String,
    @SerializedName("name")
    val name:String,
    @SerializedName("address")
    val address:String,
    @SerializedName("avatar")
    val avatar:String
)