package com.android.socialchat.models

import com.google.gson.annotations.SerializedName

data class listMess (
    @SerializedName("listMess")
    val listMess:List<ListMessModel>
    )