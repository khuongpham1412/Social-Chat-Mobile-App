package com.android.socialchat.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.socialchat.base.onFailure
import com.android.socialchat.base.onLoading
import com.android.socialchat.base.onSuccess
import com.android.socialchat.datalocal.AppPrefs
import com.android.socialchat.models.BaseResponseModel
import com.android.socialchat.models.MessageModel
import com.android.socialchat.models.UsersModel
import com.android.socialchat.repo.UserFragmentRepo
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class UserFragmentViewModel(
    private val userFragmentRepo: UserFragmentRepo,
    private val appPrefs: AppPrefs,
) : ViewModel() {
    private val _avt = MutableLiveData<BaseResponseModel<MessageModel>>()
    val avt: LiveData<BaseResponseModel<MessageModel>> get() = _avt

    fun setAvatar(avt: MultipartBody.Part) {
        viewModelScope.launch {
            userFragmentRepo.setAvatar(avt)
                .onSuccess { avatar ->
                    _avt.postValue(avatar)
                }
                .onLoading {
                    Log.d("DEBUG", "LOADING")
                }
                .onFailure {
                    Log.d("ERROR", "ERROR")
                }
        }
    }

    private val _user = MutableLiveData<BaseResponseModel<UsersModel>>()
    val user: LiveData<BaseResponseModel<UsersModel>> get() = _user

//    fun getUser(id: String) {
//        viewModelScope.launch {
//            userFragmentRepo.getUser(id)
//                .onSuccess { user ->
//                    _user.postValue(user)
//                }
//                .onLoading {
//                    Log.d("DEBUG", "LOADING")
//                }
//                .onFailure {
//                    Log.d("ERROR", "ERROR")
//                }
//        }
//    }

    private val _logout = MutableLiveData<BaseResponseModel<MessageModel>>()
    val logout: LiveData<BaseResponseModel<MessageModel>> get() = _logout

    fun logout() {
        viewModelScope.launch {
            userFragmentRepo.logout()
                .onSuccess { message ->
                    _logout.postValue(message)
                    if (message.error == null) {
                        appPrefs.clearData()
                    }
                }
                .onLoading {
                    Log.d("DEBUG", "LOADING")
                }
                .onFailure {
                    Log.d("ERROR", "ERROR")
                }
        }
    }
}