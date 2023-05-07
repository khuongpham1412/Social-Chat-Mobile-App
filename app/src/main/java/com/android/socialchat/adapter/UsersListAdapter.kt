package com.android.socialchat.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.socialchat.R
import com.android.socialchat.constants.Constants
import com.android.socialchat.databinding.UsersListBinding
import com.android.socialchat.models.ListUsers
import com.android.socialchat.ui.ConversationActivity
import com.bumptech.glide.Glide


class UsersListAdapter(private var context: Context, private val constants: Constants) :
    RecyclerView.Adapter<UsersListAdapter.ViewHolder>() {
    private var usersList: ArrayList<ListUsers> = arrayListOf()
    private var idUser: String? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setUserList(list: ArrayList<ListUsers>, idUser: String) {
        this.usersList = list
        this.idUser = idUser
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(UsersListBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.users.setImageResource(R.drawable.tomioka)
        Glide.with(context).load(constants.URL + "/" + usersList[position].avatar)
            .into(holder.binding.users)
        holder.binding.name.text = usersList[position].name
        holder.binding.users.setOnClickListener {
            val intent = Intent(context, ConversationActivity::class.java)
            val bundle = Bundle()
            bundle.putString("ID_ROOM", idUser + usersList[position]._id)
            bundle.putString("NAME", usersList[position].name)
            intent.putExtra("DATA", bundle)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    class ViewHolder(val binding: UsersListBinding) : RecyclerView.ViewHolder(binding.root) {

    }

}