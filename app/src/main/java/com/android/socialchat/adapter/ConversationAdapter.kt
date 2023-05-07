package com.android.socialchat.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.android.socialchat.constants.Constants
import com.android.socialchat.databinding.*
import com.android.socialchat.models.ConversationModel
import com.bumptech.glide.Glide

class ConversationAdapter(private var context: Context, private var constants: Constants) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object{
        const val TYPE_MESSAGE_SEND = 1
        const val TYPE_MESSAGE_RECEIVES = 2
        const val TYPE_IMAGE_SEND = 3
        const val TYPE_IMAGE_RECEIVES = 4
        const val TYPE_VIDEO_SEND = 5
        const val TYPE_VIDEO_RECEIVES = 6
    }

    private lateinit var id: String
    private var list: List<ConversationModel> = arrayListOf()

    class ViewHolder(val binding: MessUserBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    class ViewHolder1(val binding: MessFriendBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    class ViewHolder2(val binding: UserSendImageBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    class ViewHolder3(val binding: FriendGetImageBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    class ViewHolder4(val binding: UserSendVideoBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    class ViewHolder5(val binding: FriendGetVideoBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun getItemViewType(position: Int): Int {
        val list1 = list[position]
        when(list1.type){
            "message"->{
                return if (list1.id == id) {
                    TYPE_MESSAGE_SEND
                }else{
                    TYPE_MESSAGE_RECEIVES
                }
            }

            "image" ->{
                return if (list1.id == id) {
                    TYPE_IMAGE_SEND
                }else{
                    TYPE_IMAGE_RECEIVES
                }
            }
            "video" ->{
                return if (list1.id == id) {
                    TYPE_VIDEO_SEND
                }else{
                    TYPE_VIDEO_RECEIVES
                }
            }
        }
        return 1
    }

    override fun onCreateViewHolder(
        @NonNull parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_MESSAGE_SEND -> {
                return ViewHolder(MessUserBinding.inflate(LayoutInflater.from(parent.context),
                    parent,
                    false))
            }

            TYPE_MESSAGE_RECEIVES -> {
                return ViewHolder1(MessFriendBinding.inflate(LayoutInflater.from(parent.context),
                    parent,
                    false))
            }

            TYPE_IMAGE_SEND -> {
                return ViewHolder2(UserSendImageBinding.inflate(LayoutInflater.from(parent.context),
                    parent,
                    false))
            }

            TYPE_IMAGE_RECEIVES -> {
                return ViewHolder3(FriendGetImageBinding.inflate(LayoutInflater.from(parent.context),
                    parent,
                    false))
            }

            TYPE_VIDEO_SEND -> {
                return ViewHolder4(UserSendVideoBinding.inflate(LayoutInflater.from(parent.context),
                    parent,
                    false))
            }

            TYPE_VIDEO_RECEIVES -> {
                return ViewHolder5(FriendGetVideoBinding.inflate(LayoutInflater.from(parent.context),
                    parent,
                    false))
            }
        }
        return ViewHolder1(MessFriendBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }


    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setUserList(list: ArrayList<ConversationModel>, id: String) {
        this.list = list
        this.id = id
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val listMess = list[position]
        if(listMess.type == "message"){
            if (listMess.id == id) {
                if (TYPE_MESSAGE_SEND == holder.itemViewType) {
                    val user: ViewHolder = holder as ViewHolder
                    user.binding.tvName.text = listMess.message
                }

            }
            else {
                if (TYPE_MESSAGE_RECEIVES == holder.itemViewType) {
                    val user1: ViewHolder1 = holder as ViewHolder1
                    user1.binding.tvName.text = listMess.message
                }
            }
        }else if(listMess.type == "image"){
            if (listMess.id == id) {
                if (TYPE_IMAGE_SEND == holder.itemViewType) {
                    val user: ViewHolder2 = holder as ViewHolder2
                    Glide.with(context).load(constants.URL+"/"+listMess.message).into(user.binding.image)
                }

            }
            else {
                if (TYPE_IMAGE_RECEIVES == holder.itemViewType) {
                    val user1: ViewHolder3 = holder as ViewHolder3
                    Glide.with(context).load(constants.URL+"/"+listMess.message).into(user1.binding.image)
                }
            }
        }else{
            if (listMess.id == id) {
                if (TYPE_VIDEO_SEND == holder.itemViewType) {
                    val user: ViewHolder4 = holder as ViewHolder4
                    val uri: Uri = Uri.parse(listMess.message)
                    user.binding.video.setVideoURI(uri)
                    user.binding.video.start()
                }

            }
            else {
                if (TYPE_VIDEO_RECEIVES == holder.itemViewType) {
                    val user1: ViewHolder5 = holder as ViewHolder5
                    val uri: Uri = Uri.parse(listMess.message)
                    user1.binding.video.setVideoURI(uri)
                    user1.binding.video.start()
                }
            }
        }

    }
}