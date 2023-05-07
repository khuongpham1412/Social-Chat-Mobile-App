package com.android.socialchat.ui

import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.android.socialchat.constants.Constants
import com.android.socialchat.databinding.UserFragmentBinding
import com.android.socialchat.datalocal.AppPrefs
import com.android.socialchat.models.UsersModel
import com.android.socialchat.viewmodel.UserFragmentViewModel
import com.bumptech.glide.Glide
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class UserFragment : Fragment() {
    private lateinit var binding: UserFragmentBinding
    private val userFragmentViewModel: UserFragmentViewModel by viewModel()
    private val appPrefs: AppPrefs by inject()
    private val constants: Constants by inject()
    private var username: String? = null
    private var avatar: String? = null
    private var realPath: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = UserFragmentBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        eventOnclick()
        setupViewModel()
    }

    private fun setupView() {
        val user = Gson().fromJson(appPrefs.getString(constants.USERINFO), UsersModel::class.java)
        username = user.name
        avatar = user.avatar
        binding.tvName.text = username
        context?.let {
            Glide.with(it)
                .load(constants.URL + "/" + Gson().fromJson(appPrefs.getString(constants.USERINFO),
                    UsersModel::class.java).avatar).into(binding.avt)
        }
    }

    private fun setupViewModel() {
        userFragmentViewModel.logout.observe(viewLifecycleOwner, { message ->
            if (message.error == null) {
                startActivity(Intent(activity, LoginActivity::class.java))
            } else {
                Toast.makeText(activity, message.error, Toast.LENGTH_SHORT).show()
            }
        })

        userFragmentViewModel.avt.observe(viewLifecycleOwner, { avatar ->
            if (avatar.error == null) {
                Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, avatar.error, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun eventOnclick() {
        binding.btnLogout.setOnClickListener {
            userFragmentViewModel.logout()
        }
        binding.setAvatar.setOnClickListener {
            val intent = Intent()
            intent.type = ("image/*")
            intent.action = (Intent.ACTION_GET_CONTENT)
            resultLauncher.launch(intent)
        }
    }

    private fun setAvatar() {
        val file = realPath?.let { File(it) }
        val requestBodyAvt = file?.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body =
            requestBodyAvt?.let { MultipartBody.Part.createFormData("", file.name, it) }
        body?.let { userFragmentViewModel.setAvatar(it) }
    }

    private fun getPathFromUri(context: Context, uri: Uri): String? {
        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageState() + "/" + split[1]
                }

                // TODO handle non-primary volumes
            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    java.lang.Long.valueOf(id))
                return getDataColumn(context, contentUri)
            } else if (isMediaDocument(uri)) {
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

//                return getDataColumn(context, contentUri, selection, selectionArgs)
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            // Return the remote address
            return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(context,
                uri)
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

    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    private fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val uri: Uri? = data?.data
                realPath = uri?.let { context?.let { it1 -> getPathFromUri(it1, it) } }
                setAvatar()
            }
        }
}