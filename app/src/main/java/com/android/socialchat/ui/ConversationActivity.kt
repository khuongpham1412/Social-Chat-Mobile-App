package com.android.socialchat.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.socialchat.adapter.ConversationAdapter
import com.android.socialchat.constants.Constants
import com.android.socialchat.databinding.ActivityConversationBinding
import com.android.socialchat.datalocal.AppPrefs
import com.android.socialchat.models.ConversationModel
import com.android.socialchat.models.UsersModel
import com.android.socialchat.viewmodel.ConversationViewModel
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class ConversationActivity : AppCompatActivity() {
    //socket
    private lateinit var mSocket: Socket

    //binding
    private lateinit var binding: ActivityConversationBinding

    //viewModel
    private val conversationViewModel: ConversationViewModel by viewModel()

    //ArrayList
    private var listMess: ArrayList<ConversationModel> = arrayListOf()

    //adapter
    private lateinit var conversationAdapter: ConversationAdapter
    private lateinit var progressBar: ProgressBar

    //layoutManager
    private val layoutManager = LinearLayoutManager(this)
    private val appPrefs: AppPrefs by inject()
    private val constants: Constants by inject()
    private var idRoom: String? = null
    private var name: String? = null
    private var realPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConversationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        connect()
        getDataFromIntent()
        setupView()
        setupViewModel()
        getMessage()
        eventEmitSocketJoinRoom()
        eventOnclick()
        eventOnSocket()
    }

    private fun getIdUser(): String? {
        return Gson().fromJson(appPrefs.getString(constants.USERINFO),
            UsersModel::class.java)?.id
    }

    private fun connect() {
        try {
            mSocket = IO.socket(constants.URL)
        } catch (ex: Exception) {
            Log.d("ERROR", ex.message.toString())
        }
        mSocket.connect()
    }

    private fun eventEmitSocketJoinRoom() {
        mSocket.emit(constants.JOIN, idRoom)
    }

    private fun getMessage() {
        idRoom?.let { conversationViewModel.getMessage(it) }
    }

    private fun getDataFromIntent() {
        val intent = intent
        val bundle: Bundle? = intent.getBundleExtra("DATA")
        name = bundle?.getString("NAME").toString()
        idRoom = bundle?.getString("ID_ROOM").toString()
    }


    private fun eventOnSocket() {
        mSocket.on(Socket.EVENT_CONNECT) {
        }
            .on(constants.SERVER_SEND_MESS) { parameters -> // do something on receiving a 'foo' event
                val idUserSend = parameters[0].toString().substring(0, 24)
                val mess = parameters[0].toString().substring(24)
                listMess.add(ConversationModel("1",
                    idUserSend, "message", mess))
                runOnUiThread {
                    val id: String? = getIdUser()
                    if (id != null) {
                        conversationAdapter.setUserList(listMess, id)
                        binding.conversationRec.scrollToPosition(listMess.size - 1)
                    }
                    saveMessDb()
                    binding.editMess.setText("")
                }
            }
            .on(constants.SERVER_SEND_IMAGE) { parameters ->
                runOnUiThread {
                    val idUserSend = parameters[0].toString().substring(0, 24)
                    val mess = parameters[0].toString().substring(24)
                    listMess.add(ConversationModel("1",
                        idUserSend, "image", mess))
                    val id: String? = getIdUser()
                    if (id != null) {
                        conversationAdapter.setUserList(listMess, id)
                        binding.conversationRec.scrollToPosition(listMess.size - 1)
                    }
                    progressBar.visibility = View.GONE
                }
            }
    }

    private fun saveMessDb() {
        idRoom?.let { it1 ->
            conversationViewModel.sendMessage(it1,
                binding.editMess.text.toString())
        }
    }

    private fun setupView() {
        progressBar =
            ProgressBar(this@ConversationActivity, null, android.R.attr.progressBarStyleLarge)
        val params = RelativeLayout.LayoutParams(100, 100)
        val relativeLayout = RelativeLayout(this)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        relativeLayout.addView(progressBar, params)
        binding.tvName.text = name
        conversationAdapter = ConversationAdapter(this, constants)
        binding.conversationRec.adapter = conversationAdapter
        binding.conversationRec.layoutManager = layoutManager
    }


    private fun eventOnclick() {
        binding.imgLib.setOnClickListener {
            intent = Intent()
            intent.type = ("image/* | video/*")
            intent.action = (Intent.ACTION_GET_CONTENT)
            resultLauncher.launch(intent)
        }
        binding.btnSend.setOnClickListener {
            val id: String? = getIdUser()
            if (id != null && binding.editMess.text.toString() != "") {
                userSendMess(id)
            } else {
                Toast.makeText(this, "Write your message...", Toast.LENGTH_LONG).show()
            }
        }


    }

    private fun userSendMess(id: String) {
        mSocket.emit(constants.CLIENT_GUI_MESS,
            id + idRoom + binding.editMess.text.toString())
    }

    private fun sendImage() {
        progressBar.visibility = View.VISIBLE
        val file = realPath?.let { File(it) }
        val requestBody = file?.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val requestBodyID = idRoom?.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val check = realPath?.indexOf(".")?.let { realPath!!.substring(it + 1) }
        if (check.equals("jpg")) {
            val body: MultipartBody.Part? =
                requestBody?.let { MultipartBody.Part.createFormData("image", file.name, it) }
            requestBodyID?.let {
                body?.let { it1 ->
                    conversationViewModel.sendImage(it,
                        it1)
                }
            }
        } else {
            val body =
                requestBody?.let { MultipartBody.Part.createFormData("video", file.name, it) }
            requestBodyID?.let {
                body?.let { it1 ->
                    conversationViewModel.sendVideo(it,
                        it1)
                }
            }
        }
    }


    private fun setupViewModel() {
        conversationViewModel.conversation.observe(this@ConversationActivity, { conversation ->
            if (conversation.error == null) {
                conversation.data?.let { listMess.addAll(it.conversation) }
            }
            val id: String? = getIdUser()
            if (id != null) {
                conversationAdapter.setUserList(listMess, id)
                binding.conversationRec.scrollToPosition(listMess.size - 1)
            }
        })

        conversationViewModel.sendImage.observe(this@ConversationActivity, { image ->
            if (image.error == null) {
                val id: String? = getIdUser()
                if (id != null) {
                    mSocket.emit(constants.USER_SEND_IMAGE, id)
                }
            } else {
                Toast.makeText(this, image.error.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                val uri: Uri? = data?.data
                realPath = uri?.let {
                    getPathFromUri(this, it)
                }
                sendImage()
            }
        }

    private fun getPathFromUri(context: Context, uri: Uri): String? {
        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                when (type) {
                    "image" -> {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    }
                    "video" -> {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    }
                    "audio" -> {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                }
                return contentUri.toString()

            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            // Return the remote address
            return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(context, uri)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    private fun getDataColumn(
        context: Context, uri: Uri?,
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(
            column
        )
        try {
            cursor = context.contentResolver.query(uri!!, projection, null, null,
                null)
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }


    //     param uri The Uri to check.
    //     return Whether the Uri authority is MediaProvider.
    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    //     param uri The Uri to check.
    //     return Whether the Uri authority is Google Photos.
    private fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }
}




