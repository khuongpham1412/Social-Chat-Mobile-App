package com.android.socialchat.models

import com.google.gson.annotations.SerializedName

data class ListUsersModel (
    @SerializedName("listUser")
    val listUser:List<ListUsers>
)