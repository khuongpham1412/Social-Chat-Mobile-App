package com.android.socialchat.models

import com.google.gson.annotations.SerializedName

data class MessageModel (
    @SerializedName("message")
    val message:String
)