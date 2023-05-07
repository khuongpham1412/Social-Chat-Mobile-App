package com.android.socialchat.models

import com.google.gson.annotations.SerializedName

data class test (
    @SerializedName("id")
    val id:String,
    @SerializedName("image")
    val image:ByteArray,
)