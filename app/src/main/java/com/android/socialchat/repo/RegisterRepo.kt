package com.android.socialchat.repo

import com.android.socialchat.api.Api
import com.android.socialchat.base.Response
import com.android.socialchat.base.ResponseError
import com.android.socialchat.models.BaseResponseModel
import com.android.socialchat.models.UsersModel

class RegisterRepo(private val api: Api) {
    suspend fun register(
        username: String,
        password: String,
        name: String,
        address: String,
    ): Response<BaseResponseModel<UsersModel>> {
        return try {
            Response.success(api.register(username, password, name, address))
        } catch (e: Exception) {
            Response.error(ResponseError(101, e.message.toString()))
        }
    }
}