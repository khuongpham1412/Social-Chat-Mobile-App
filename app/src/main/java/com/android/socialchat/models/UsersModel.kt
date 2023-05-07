package com.android.socialchat.models

import com.google.gson.annotations.SerializedName

data class UsersModel (
    @SerializedName("_id")
    val id:String,
    @SerializedName("username")
    val username:String,
    @SerializedName("name")
    val name:String,
    @SerializedName("address")
    val address:String,
    @SerializedName("avatar")
    val avatar:String,
    @SerializedName("token")
    val token:String,
)