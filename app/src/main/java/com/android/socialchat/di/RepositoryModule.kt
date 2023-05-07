package com.android.socialchat.di

import com.android.socialchat.repo.*
import com.android.socialchat.viewmodel.MessageHomeViewModel
import org.koin.dsl.module

val repositoryModule = module {
//    single { SearchRepo(get()) }
    single { LoginRepo(get()) }
    single { RegisterRepo(get()) }
    single { UserFragmentRepo(get()) }
    single { MessageHomeRepo(get()) }
//    single { AutoLoginRepo(get()) }
//    single { StatusRepo(get()) }
//    single { PostStatusRepo(get()) }
//    single { CommentRepo(get()) }
//    single { ListUserRepo(get()) }
    single { ConversationRepo(get()) }
//    single { SendConversationRepo(get()) }
//      single { GuidanceEventListDetailRepo(get()) }
    //single { provideAppSharePreferences(get()) }
//      single { CompanyListRepo(get()) }
//      single { provideSecurePreferences(androidApplication() as MyApplication)}
}