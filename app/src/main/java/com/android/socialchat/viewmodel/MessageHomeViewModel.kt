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
import com.android.socialchat.models.ListUsersModel
import com.android.socialchat.models.listMess
import com.android.socialchat.repo.MessageHomeRepo
import kotlinx.coroutines.launch

class MessageHomeViewModel(
    private val messageHomeRepo: MessageHomeRepo,
) : ViewModel() {
    private val _listUser = MutableLiveData<BaseResponseModel<ListUsersModel>>()
    val listUsers: LiveData<BaseResponseModel<ListUsersModel>> get() = _listUser

    fun getListUser() {
        viewModelScope.launch {
            messageHomeRepo.getListUsers()
                .onSuccess { listUser ->
                    _listUser.postValue(listUser)
                }
                .onLoading {
                    Log.d("DEBUG", "LOADING")
                }
                .onFailure {
                    Log.d("ERROR", "ERROR")
                }
        }
    }

    private val _listMess = MutableLiveData<BaseResponseModel<listMess>>()
    val listMess: LiveData<BaseResponseModel<listMess>> get() = _listMess

    fun getListMess(id: String) {
        viewModelScope.launch {
            messageHomeRepo.getListMess(id)
                .onSuccess { listMess ->
                    _listMess.postValue(listMess)
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