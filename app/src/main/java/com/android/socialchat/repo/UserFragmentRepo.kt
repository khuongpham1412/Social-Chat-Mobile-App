package com.android.socialchat.repo

import com.android.socialchat.api.Api
import com.android.socialchat.base.Response
import com.android.socialchat.base.ResponseError
import com.android.socialchat.models.BaseResponseModel
import com.android.socialchat.models.MessageModel
import com.android.socialchat.models.UsersModel
import okhttp3.MultipartBody

class UserFragmentRepo(private val api: Api) {
    suspend fun setAvatar(avt: MultipartBody.Part): Response<BaseResponseModel<MessageModel>> {
        return try {
            Response.success(api.setAvatar(avt))
        } catch (e: Exception) {
            Response.error(ResponseError(101, e.message.toString()))
        }
    }

    suspend fun logout(): Response<BaseResponseModel<MessageModel>> {
        return try {
            Response.success(api.logout())
        } catch (e: java.lang.Exception) {
            Response.error(ResponseError(101, e.message.toString()))
        }
    }

    suspend fun getUser(id: String): Response<BaseResponseModel<UsersModel>> {
        return try {
            Response.success(api.getUser(id))
        } catch (e: java.lang.Exception) {
            Response.error(ResponseError(101, e.message.toString()))
        }
    }
}