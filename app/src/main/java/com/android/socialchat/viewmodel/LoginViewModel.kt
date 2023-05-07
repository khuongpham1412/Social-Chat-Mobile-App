package com.android.socialchat.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.socialchat.base.onFailure
import com.android.socialchat.base.onLoading
import com.android.socialchat.base.onSuccess
import com.android.socialchat.constants.Constants
import com.android.socialchat.datalocal.AppPrefs
import com.android.socialchat.models.BaseResponseModel
import com.android.socialchat.models.MessageModel
import com.android.socialchat.models.UsersModel
import com.android.socialchat.repo.LoginRepo
import com.google.gson.Gson
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepo: LoginRepo,
    private val appPrefs: AppPrefs,
    private val constants: Constants,
) : ViewModel() {
    private val _user = MutableLiveData<BaseResponseModel<UsersModel>>()
    val user: LiveData<BaseResponseModel<UsersModel>> get() = _user

    fun login(username: String, password: String) {
        viewModelScope.launch {
            loginRepo.login(username, password)
                .onSuccess { user ->
                    _user.postValue(user)
                    saveData(user)
                }
                .onLoading {
                    Log.d("DEBUG", "LOADING")
                }
                .onFailure {
                    Log.d("ERROR", "ERROR")
                }
        }
    }

    private val _autoLogin = MutableLiveData<BaseResponseModel<MessageModel>>()
    val autoLogin: LiveData<BaseResponseModel<MessageModel>> get() = _autoLogin
    fun autoLogin() {
        viewModelScope.launch {
            loginRepo.autoLogin()
                .onSuccess { message ->
                    _autoLogin.postValue(message)
                }
                .onLoading {
                    Log.d("DEBUG", "LOADING")
                }
                .onFailure {
                    Log.d("ERROR", "ERROR")
                }
        }
    }

    private fun saveData(userInfo: BaseResponseModel<UsersModel>) {
        val user = userInfo.data?.let {
            UsersModel(it.id, it.username, it.name, it.address,it.avatar,
                it.token)
        }
        appPrefs.putString(constants.USERINFO, Gson().toJson(user))
    }
}