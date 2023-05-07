//package com.android.socialchat.adapter
//
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.android.socialchat.databinding.MessFriendBinding
//import com.android.socialchat.models.ListUsers
//import com.chow.instatus.ui.ConversationActivity
//
//class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.ViewHolder> {
//
//    private var list: List<ListUsers> = arrayListOf()
//    private var context: Context
//    private lateinit var user: String
//
//    constructor(context: Context) {
//        this.context = context
//    }
//
//    class ViewHolder(val binding: MessFriendBinding) : RecyclerView.ViewHolder(binding.root) {
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(MessFriendBinding.inflate(LayoutInflater.from(parent.context),
//            parent,
//            false))
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        if (!list[position]._id.equals(user)) {
//            holder.binding.tvName.setText(list.get(position).name)
//        }
//        holder.binding.parent.setOnClickListener(View.OnClickListener {
//            val intent = Intent(context, ConversationActivity::class.java)
//            var bundle = Bundle()
//            bundle.putString("id_phong", user + list[position]._id)
//            bundle.putString("name", list[position].name)
//            intent.putExtra("data", bundle)
//            context.startActivity(intent)
//        })
//    }
//
//    override fun getItemCount(): Int {
//        return list.size
//    }
//
//    fun setUserList(list: List<ListUserModel>, user: String) {
//        this.list = list
//        this.user = user
//        notifyDataSetChanged()
//    }
//
//}