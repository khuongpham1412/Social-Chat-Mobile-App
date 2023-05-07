package com.android.socialchat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.socialchat.databinding.StatusRecListBinding

class StatusAdapter(private val context: Context) :
    RecyclerView.Adapter<StatusAdapter.ViewHolder>() {
    //private var statusList : ArrayList<>
    //private lateinit var context:Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(StatusRecListBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 3
    }

    class ViewHolder(binding: StatusRecListBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}