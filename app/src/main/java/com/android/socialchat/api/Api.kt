package com.android.socialchat.api

import com.android.socialchat.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface Api {
    @FormUrlEncoded
    @POST("Users/Login")
    suspend fun login(@Field("username") username:String, @Field("password") password:String): BaseResponseModel<UsersModel>

    @FormUrlEncoded
    @POST("Users/getUser")
    suspend fun getUser(@Field("id") id:String): BaseResponseModel<UsersModel>


    @FormUrlEncoded
    @POST("Users/Register")
    suspend fun register(@Field("username") username:String, @Field("password") password:String, @Field("name") name:String, @Field("address") address:String): BaseResponseModel<UsersModel>

    @POST("Users/AutoLogin")
    suspend fun autoLogin() : BaseResponseModel<MessageModel>

    @POST("Users/Logout")
    suspend fun logout(): BaseResponseModel<MessageModel>

    @POST("Users/GetListUser")
    suspend fun getListUser() : BaseResponseModel<ListUsersModel>

    @FormUrlEncoded
    @POST("UserInteractions/getListMessage")
    suspend fun getListMess(@Field("id") id:String) : BaseResponseModel<listMess>

    @FormUrlEncoded
    @POST("UserInteractions/SendMessage")
    suspend fun sendMessage(@Field("id") id_phong: String,@Field("message") message:String) :BaseResponseModel<MessageModel>

    @FormUrlEncoded
    @POST("UserInteractions/GetMessage")
    suspend fun getMessage(@Field("id") id_phong:String) : BaseResponseModel<RoomModel>

    @Multipart
    @POST("Users/SetAvatar")
    suspend fun setAvatar(@Part avt: MultipartBody.Part) : BaseResponseModel<MessageModel>

    @Multipart
    @POST("UserInteractions/SendImage")
    suspend fun sendImage(@Part("idphong") idphong : RequestBody,
                            @Part image: MultipartBody.Part) : BaseResponseModel<MessageModel>

    @Multipart
    @POST("UserInteractions/SendVideo")
    suspend fun sendVideo(@Part("idphong") idphong : RequestBody,
                          @Part video: MultipartBody.Part) : BaseResponseModel<MessageModel>

}