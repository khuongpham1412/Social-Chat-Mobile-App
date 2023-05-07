package com.android.socialchat.models

import com.google.gson.annotations.SerializedName

data class BaseResponseModel<T>(
    @SerializedName("data")
    val data:T?,
    @SerializedName("error")
    val error:String?
)