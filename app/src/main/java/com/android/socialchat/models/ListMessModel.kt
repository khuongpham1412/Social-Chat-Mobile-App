package com.android.socialchat.models

import com.google.gson.annotations.SerializedName

data class ListMessModel (
    @SerializedName("_id")
    val _id:String,
    @SerializedName("idphong")
    val idphong:String,
    @SerializedName("userSend")
    val userSend:String,
    @SerializedName("userGet")
    val userGet:String,
    @SerializedName("status")
    val status:String,
    @SerializedName("name")
    val name:String,
    @SerializedName("avatar")
    val avatar:String,
    @SerializedName("conversation")
    val conversation:List<ConversationModel>
)