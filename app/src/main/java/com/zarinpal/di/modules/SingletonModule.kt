package com.zarinpal.di.modules

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.zarinpal.BuildConfig
import com.zarinpal.data.server.ExceptionInterceptor
import com.zarinpal.data.server.LoggingInterceptor
import com.zarinpal.data.server.WebServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.zarinpal.utils.CredentialManager
import com.zarinpal.utils.SharedPreferencesHelper
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Inject
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

    @Provides
    @Singleton
    fun provideOkHttpClient(
        exceptionInterceptor: ExceptionInterceptor,
        loggingInterceptor: LoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(30, TimeUnit.SECONDS)
        writeTimeout(60, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        addInterceptor(exceptionInterceptor)
        if (BuildConfig.DEBUG)
            addInterceptor(loggingInterceptor)
    }.build()

    @Provides
    @Singleton
    fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient =
        ApolloClient.builder()
            .serverUrl("https://api.github.com/graphql")
            .okHttpClient(okHttpClient)
            .build()
}