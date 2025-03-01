package com.example.onboardx.data.api

import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
object RetrofitClient {
    private const val BASE_URL = "https://run.mocky.io/"

    //Provide Interceptor to log the response
    @Provides
    @Singleton
    private fun provideInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // Provide OKHttpClient
    @Provides
    @Singleton
    private fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // Provide Retrofit Instance
    @Provides
    @Singleton
    private fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    // Provide OnBoard Service Instance
    @Provides
    @Singleton
    private fun provideOnBoardServiceInstance(retrofit: Retrofit): OnBoardingApiService {
        return retrofit.create(OnBoardingApiService::class.java)
    }

}