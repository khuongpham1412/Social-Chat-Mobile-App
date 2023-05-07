package com.android.socialchat.di

import com.android.socialchat.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
//    viewModel { SearchViewModel(get()) }
    viewModel { UserFragmentViewModel(get(), get()) }
    viewModel { LoginViewModel(get(),get(),get()) }
    viewModel { RegisterViewModel(get(),get(),get()) }
    viewModel { MessageHomeViewModel(get()) }
//    viewModel{ StatusViewModel(get(), get()) }
//    viewModel { CommunicateViewModel() }
//    viewModel { PostStatusViewModel(get()) }
//    viewModel { CommentViewModel(get()) }
//    viewModel { ListUserViewModel(get()) }
    viewModel { ConversationViewModel(get()) }
//    viewModel { SendConversationViewModel(get()) }
//    viewModel { GuidanceEventListViewModel(get()) }
//    viewModel { CompanyListViewModel(get()) }
}