package com.android.socialchat.di

import android.content.Context
import android.content.SharedPreferences
import com.android.socialchat.constants.Constants
import com.android.socialchat.datalocal.AppPrefs
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {
//      single { GuidanceEventListDetailRepo(get()) }
//      single { CompanyListRepo(get()) }
    single { provideSharePreferences(androidApplication() as App)}
    single { AppPrefs(get()) }
    single { Constants() }
}
fun provideSharePreferences(app: App):SharedPreferences {
    return app.applicationContext.getSharedPreferences(
        app.applicationContext.packageName,
        Context.MODE_PRIVATE
    )
}
//fun provideSecurePreferences(app: MyApplication): SecurePreferences {
//    return SecurePreferences(app, "", "miretty.xml")
//}
//fun provideAppSharePreferences(sharedPreferences: SecurePreferences) : AppPreferences {
//    return AppPreferences(sharedPreferences)
//}
