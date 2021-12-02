package com.zarinpal.di.modules

import android.content.Context
import com.zarinpal.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.zarinpal.data.server.ExceptionInterceptor
import com.zarinpal.data.server.LoggingInterceptor
import com.zarinpal.data.server.WebServices
import com.zarinpal.utils.CredentialManager
import com.zarinpal.utils.SharedPreferencesHelper
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    @Provides
    @Singleton
    fun provideSharedPreferencesHelper(@ApplicationContext context: Context) =
        SharedPreferencesHelper(context)

    @Provides
    @Singleton
    fun provideCredentialManager(@ApplicationContext context: Context) = CredentialManager(context)

    @Singleton
    @Provides
    fun provideExceptionInterceptor() = ExceptionInterceptor()

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() = LoggingInterceptor()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        exceptionInterceptor: ExceptionInterceptor,
        loggingInterceptor: LoggingInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.writeTimeout(60, TimeUnit.SECONDS)
        builder.readTimeout(60, TimeUnit.SECONDS)
        builder.addInterceptor(exceptionInterceptor)
        if (BuildConfig.DEBUG)
            builder.addInterceptor(loggingInterceptor)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("")
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): WebServices =
        retrofit.create(WebServices::class.java)
}