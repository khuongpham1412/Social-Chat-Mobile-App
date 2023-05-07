package com.android.socialchat.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.socialchat.base.onFailure
import com.android.socialchat.base.onLoading
import com.android.socialchat.base.onSuccess
import com.android.socialchat.models.BaseResponseModel
import com.android.socialchat.models.MessageModel
import com.android.socialchat.models.RoomModel
import com.android.socialchat.repo.ConversationRepo
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ConversationViewModel(private var conversationRepo: ConversationRepo) : ViewModel() {
    private val _conversation = MutableLiveData<BaseResponseModel<RoomModel>>()
    val conversation: LiveData<BaseResponseModel<RoomModel>> get() = _conversation

    fun getMessage(idPhong: String) {
        viewModelScope.launch {
            conversationRepo.getMessage(idPhong)
                .onSuccess { conver ->
                    _conversation.postValue(conver)
                }
                .onLoading {
                    Log.d("DEBUG", "DEBUG")
                }
                .onFailure {
                    Log.d("ERROR", "ERROR" + it.message.toString())
                }
        }
    }


    private val _send = MutableLiveData<BaseResponseModel<MessageModel>>()
    val send: LiveData<BaseResponseModel<MessageModel>> get() = _send

    fun sendMessage(id_phong: String, message: String) {
        viewModelScope.launch {
            conversationRepo.sendMessage(id_phong, message)
                .onSuccess { message ->
                    _send.postValue(message)
                }
                .onLoading {
                    Log.d("DEBUG", "LOADING")
                }
                .onFailure {
                    Log.d("ERROR", "ERROR" + it.message.toString())
                }
        }
    }

    private val _sendImage = MutableLiveData<BaseResponseModel<MessageModel>>()
    val sendImage: LiveData<BaseResponseModel<MessageModel>> get() = _sendImage

    fun sendImage(
        idphong: RequestBody,
        image: MultipartBody.Part,
    ) {
        viewModelScope.launch {
            conversationRepo.sendImage(idphong, image)
                .onSuccess { image ->
                    _sendImage.postValue(image)
                }
                .onLoading {
                    Log.d("DEBUG", "DEBUG")
                }
                .onFailure {
                    Log.d("ERROR", "ERROR" + it.message.toString())
                }
        }
    }

    private val _sendVideo = MutableLiveData<BaseResponseModel<MessageModel>>()
    //val sendVideo: LiveData<BaseResponseModel<MessageModel>> get() = _sendVideo

    fun sendVideo(
        idphong: RequestBody,
        video: MultipartBody.Part,
    ) {
        viewModelScope.launch {
            conversationRepo.sendVideo(idphong, video)
                .onSuccess { image ->
                    _sendVideo.postValue(image)
                }
                .onLoading {
                    Log.d("DEBUG", "DEBUG")
                }
                .onFailure {
                    Log.d("ERROR", "ERROR" + it.message.toString())
                }
        }
    }
}