package com.android.socialchat.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.socialchat.constants.Constants
import com.android.socialchat.databinding.MessageListBinding
import com.android.socialchat.models.ListMessModel
import com.android.socialchat.ui.ConversationActivity
import com.bumptech.glide.Glide

class MessageListAdapter(private val context: Context, private val constants: Constants) :
    RecyclerView.Adapter<MessageListAdapter.ViewHolder>() {
    private var messList: ArrayList<ListMessModel> = arrayListOf()
    private var idUser: String? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setMessList(list: ArrayList<ListMessModel>, idUser: String) {
        this.messList = list
        this.idUser = idUser
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MessageListBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvName.text = messList[position].name
        if (messList[position].status == "true") {
            holder.binding.tvMess.text = messList[position].conversation[0].message
        } else {
            holder.binding.tvMess.setTypeface(null, Typeface.BOLD)
            holder.binding.tvMess.textSize = 20.0F
            holder.binding.tvMess.text = messList[position].conversation[0].message
        }
        Glide.with(context).load(constants.URL + "/" + messList[position].avatar)
            .into(holder.binding.imgAvatar)
        holder.binding.Mess.setOnClickListener {
            val intent = Intent(context, ConversationActivity::class.java)
            val bundle = Bundle()
            val idRoom: String = if (idUser == messList[position].userSend) {
                idUser + messList[position].userGet
            } else {
                idUser + messList[position].userSend
            }
            bundle.putString("ID_ROOM", idRoom)
            bundle.putString("NAME", messList[position].name)
            intent.putExtra("DATA", bundle)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return messList.size
    }

    class ViewHolder(val binding: MessageListBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}