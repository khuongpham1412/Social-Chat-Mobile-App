package com.android.socialchat.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.socialchat.adapter.MessageListAdapter
import com.android.socialchat.adapter.UsersListAdapter
import com.android.socialchat.constants.Constants
import com.android.socialchat.databinding.ActivityMessageHomeBinding
import com.android.socialchat.datalocal.AppPrefs
import com.android.socialchat.models.*
import com.android.socialchat.viewmodel.MessageHomeViewModel
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessageHome : AppCompatActivity() {
    private lateinit var binding: ActivityMessageHomeBinding
    private lateinit var layoutManagerUsers: LinearLayoutManager
    private lateinit var layoutManagerMess: LinearLayoutManager
    private lateinit var usersListAdapter: UsersListAdapter
    private lateinit var messageListAdapter: MessageListAdapter
    private val messageHomeViewModel: MessageHomeViewModel by viewModel()
    private var listUsers: ArrayList<ListUsers> = arrayListOf()
    private var listMessa: ArrayList<ListMessModel> = arrayListOf()
    private val appPrefs: AppPrefs by inject()
    private val constants: Constants by inject()
    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        getData()
        setupViewModel()
    }

    private fun getData() {
        id = appPrefs.getString(constants.USERINFO)
        if (id != null) {
            val id1 = Gson().fromJson(id, UsersModel::class.java).id
            messageHomeViewModel.getListMess(id1)
        }
        messageHomeViewModel.getListUser()
    }

    private fun setupView() {
        layoutManagerMess = LinearLayoutManager(this)
        layoutManagerUsers = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        usersListAdapter = UsersListAdapter(this, constants)
        messageListAdapter = MessageListAdapter(this, constants)
        binding.recUserList.layoutManager = layoutManagerUsers
        binding.recMessList.layoutManager = layoutManagerMess
    }

    private fun setupViewModel() {
        val idUser:String? =
            Gson().fromJson(appPrefs.getString(constants.USERINFO), UsersModel::class.java)?.id

        messageHomeViewModel.listUsers.observe(this@MessageHome, { list ->
            if (list.error == null) {
                list.data?.let { it -> listUsers.addAll(it.listUser) }

                if (idUser != null)
                    usersListAdapter.setUserList(listUsers, idUser)
                binding.recUserList.adapter = usersListAdapter
            } else {
                Toast.makeText(this, list.error.toString(), Toast.LENGTH_LONG).show()
            }
        })

        messageHomeViewModel.listMess.observe(this@MessageHome, { list ->
            if (list.error == null) {
                list.data?.let { it -> listMessa.addAll(it.listMess) }
                if (idUser != null)
                    messageListAdapter.setMessList(listMessa, idUser)
                binding.recMessList.adapter = messageListAdapter
            } else {
                Toast.makeText(this, list.error.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }
}