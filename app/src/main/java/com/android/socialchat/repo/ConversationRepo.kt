package com.android.socialchat.repo

import com.android.socialchat.api.Api
import com.android.socialchat.base.Response
import com.android.socialchat.base.ResponseError
import com.android.socialchat.models.BaseResponseModel
import com.android.socialchat.models.MessageModel
import com.android.socialchat.models.RoomModel
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ConversationRepo(private val api: Api) {
    suspend fun getMessage(idPhong: String): Response<BaseResponseModel<RoomModel>> {
        return try {
            Response.success(api.getMessage(idPhong))
        } catch (ex: Exception) {
            Response.error(ResponseError(101, ex.message.toString()))
        }
    }

    suspend fun sendMessage(
        id_phong: String,
        message: String,
    ): Response<BaseResponseModel<MessageModel>> {
        return try {
            Response.success(api.sendMessage(id_phong, message))
        } catch (ex: Exception) {
            Response.error(ResponseError(101, ex.message.toString()))
        }
    }

    suspend fun sendImage(
        idphong: RequestBody,
        image: MultipartBody.Part,
    ): Response<BaseResponseModel<MessageModel>> {
        return try {
            Response.success(api.sendImage(idphong, image))
        } catch (ex: Exception) {
            Response.error(ResponseError(101, ex.message.toString()))
        }
    }

    suspend fun sendVideo(
        idphong: RequestBody,
        video: MultipartBody.Part,
    ): Response<BaseResponseModel<MessageModel>> {
        return try {
            Response.success(api.sendVideo(idphong, video))
        } catch (ex: Exception) {
            Response.error(ResponseError(101, ex.message.toString()))
        }
    }

}