package com.android.socialchat.models

import com.google.gson.annotations.SerializedName

data class ArrayConversationModel (
    @SerializedName("_id")
    val _id:String,
    @SerializedName("id")
    val id:String,
    @SerializedName("message")
    val message:String
)