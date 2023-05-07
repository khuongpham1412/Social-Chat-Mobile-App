package com.android.socialchat.models

import com.google.gson.annotations.SerializedName

data class RoomModel (
    @SerializedName("_id")
    val _id:String,
    @SerializedName("idphong")
    val idphong:String,
    @SerializedName("userSend")
    val userSend:String,
    @SerializedName("userGet")
    val userGet:String,
    @SerializedName("conversation")
    val conversation:List<ConversationModel>,
    @SerializedName("__v")
    val __v:Int
)