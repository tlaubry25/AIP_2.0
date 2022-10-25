package com.authentic.aip.di

import com.authentic.aip.common.Constant.BASE_URL
import com.authentic.aip.data.remote.api.AipApi
import com.authentic.aip.data.repository.AppRepositoryImpl
import com.authentic.aip.domain.repository.AppRepository
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
val interceptorHost = HostSelectionInterceptor()

    @Provides
    @Singleton
    fun provideAipAPi(@AipOkHttpClient okHttpClient: OkHttpClient): AipApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .build()
        .create(AipApi::class.java)

    @AipOkHttpClient
    @Provides
    fun provideGlobalInterceptorOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AddCookieInterceptor())
        .addInterceptor(ReceivedCookieInterceptor())
        .addInterceptor(interceptorHost)
        .build()

    @Provides
    @Singleton
    fun provideAppRepository(api: AipApi): AppRepository = AppRepositoryImpl(appApi = api)
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AipOkHttpClient