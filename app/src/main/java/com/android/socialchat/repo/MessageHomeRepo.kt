package com.android.socialchat.repo

import com.android.socialchat.api.Api
import com.android.socialchat.base.Response
import com.android.socialchat.base.ResponseError
import com.android.socialchat.models.BaseResponseModel
import com.android.socialchat.models.ListUsersModel
import com.android.socialchat.models.listMess

class MessageHomeRepo(private val api: Api) {
    suspend fun getListUsers(): Response<BaseResponseModel<ListUsersModel>> {
        return try {
            Response.success(api.getListUser())
        } catch (e: Exception) {
            Response.error(ResponseError(101, e.message.toString()))
        }
    }

    suspend fun getListMess(id: String): Response<BaseResponseModel<listMess>> {
        return try {
            Response.success(api.getListMess(id))
        } catch (e: Exception) {
            Response.error(ResponseError(101, e.message.toString()))
        }
    }
}