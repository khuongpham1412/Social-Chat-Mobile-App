package com.android.socialchat.di


import com.android.socialchat.api.Api
import com.android.socialchat.constants.Constants
import com.android.socialchat.datalocal.AppPrefs
import com.android.socialchat.models.UsersModel
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AuthInterceptor(private val appPrefs: AppPrefs, private val constants: Constants) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val newRequest = chain.request().newBuilder()
        val user = appPrefs.getString(constants.USERINFO)
        if (user != null) {
            newRequest.addHeader("Authorization",
                "Bearer " + Gson().fromJson(user, UsersModel::class.java).token)
        }
        return chain.proceed(newRequest.build())
    }
}


val retrofitModule = module {
    single { provideHttpClient(get(), get()) }
    single { provideRetrofit(get(), get()) }
    single { provideApiService(get()) }
}


fun provideHttpClient(appPrefs: AppPrefs, constants: Constants): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.level = if (BuildConfig.DEBUG)
        HttpLoggingInterceptor.Level.BODY
    else
        HttpLoggingInterceptor.Level.NONE
    return OkHttpClient().newBuilder()
        .readTimeout(10000, TimeUnit.MILLISECONDS)
        .writeTimeout(10000, TimeUnit.MILLISECONDS)
        .connectTimeout(10000, TimeUnit.MILLISECONDS)
        .retryOnConnectionFailure(true)
        .addInterceptor(logging)
        .addInterceptor(AuthInterceptor(appPrefs, constants))
        .build()
}

fun provideRetrofit(client: OkHttpClient, constants: Constants): Retrofit {
    return Retrofit.Builder()
        .baseUrl(constants.URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}

fun provideApiService(retrofit: Retrofit): Api {
    return retrofit.create(Api::class.java)
}