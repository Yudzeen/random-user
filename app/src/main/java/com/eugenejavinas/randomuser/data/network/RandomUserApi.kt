package com.eugenejavinas.randomuser.data.network

import android.util.Log
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApi {

    @GET("api/")
    fun fetchUsers(@Query("results") count: Int): Single<FetchUsersResponse>

    companion object {
        private const val TAG = "RandomUserApi"
        private const val BASE_URL = "https://randomuser.me/"

        fun create(): RandomUserApi {
            val logger =
                HttpLoggingInterceptor.Logger { message -> Log.d(TAG, message) }

            val interceptor = HttpLoggingInterceptor(logger)
                .apply { level = HttpLoggingInterceptor.Level.BODY }

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(RandomUserApi::class.java)
        }
    }

}