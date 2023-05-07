package com.android.socialchat.models

import com.google.gson.annotations.SerializedName

data class ConversationModel (
    @SerializedName("_id")
    val _id:String,
    @SerializedName("id")
    val id:String,
    @SerializedName("type")
    val type:String,
    @SerializedName("message")
    val message:String
)