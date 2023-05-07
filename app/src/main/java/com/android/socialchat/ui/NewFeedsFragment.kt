package com.android.socialchat.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.socialchat.adapter.StatusAdapter
import com.android.socialchat.constants.Constants
import com.android.socialchat.databinding.NewsfeedFragmentBinding
import com.android.socialchat.datalocal.AppPrefs
import com.android.socialchat.models.UsersModel
import com.bumptech.glide.Glide
import com.google.gson.Gson
import org.koin.android.ext.android.inject

class NewFeedsFragment : Fragment() {
    private lateinit var binding: NewsfeedFragmentBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapte: StatusAdapter
    private val constants: Constants by inject()
    private val appPrefs: AppPrefs by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = NewsfeedFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFragment()
        eventOnclick()
    }

    private fun eventOnclick() {
        binding.sendMess.setOnClickListener {
            val intent = Intent(activity, MessageHome::class.java)
            startActivity(intent)
        }
    }

    private fun setupFragment() {
        adapte = activity?.let { StatusAdapter(it) }!!
        linearLayoutManager = LinearLayoutManager(activity)
        binding.newsFeedRecView.adapter
        binding.newsFeedRecView.layoutManager = linearLayoutManager
        val avatar =
            Gson().fromJson(appPrefs.getString(constants.USERINFO), UsersModel::class.java)?.avatar
        context?.let { Glide.with(it).load(constants.URL +"/"+ avatar).into(binding.imgAvatar) }
    }
}