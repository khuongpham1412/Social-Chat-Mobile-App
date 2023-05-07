package com.android.socialchat.repo

import com.android.socialchat.api.Api
import com.android.socialchat.base.Response
import com.android.socialchat.base.ResponseError
import com.android.socialchat.models.BaseResponseModel
import com.android.socialchat.models.MessageModel
import com.android.socialchat.models.UsersModel

class LoginRepo(private val api: Api) {
    suspend fun login(username: String, password: String): Response<BaseResponseModel<UsersModel>> {
        return try {
            Response.success(api.login(username, password))
        } catch (e: Exception) {
            Response.error(ResponseError(101, e.message.toString()))
        }
    }

    suspend fun autoLogin(): Response<BaseResponseModel<MessageModel>> {
        return try {
            Response.success(api.autoLogin())
        } catch (e: Exception) {
            Response.error(ResponseError(101, e.message.toString()))
        }
    }
}