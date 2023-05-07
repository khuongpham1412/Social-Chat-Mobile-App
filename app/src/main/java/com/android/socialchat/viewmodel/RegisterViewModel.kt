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
import com.android.socialchat.models.UsersModel
import com.android.socialchat.repo.RegisterRepo
import com.google.gson.Gson
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerRepo: RegisterRepo,
    private val appPrefs: AppPrefs,
    private val constants: Constants,
) : ViewModel() {
    private val _register = MutableLiveData<BaseResponseModel<UsersModel>>()
    val register: LiveData<BaseResponseModel<UsersModel>> get() = _register

    fun register(username: String, password: String, name: String, address: String) {
        viewModelScope.launch {
            registerRepo.register(username, password, name, address)
                .onSuccess { register ->
                    _register.postValue(register)
                    saveData(register)
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
            Log.d("SUCCESS","SUCCESSFULLY")
            UsersModel(it.id, it.username, it.name, it.address, it.avatar,
                it.token)
        }
        appPrefs.putString(constants.USERINFO, Gson().toJson(user))
    }
}