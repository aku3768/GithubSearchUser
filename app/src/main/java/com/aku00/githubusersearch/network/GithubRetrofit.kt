package com.aku00.githubusersearch.network

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GithubRetrofit {
    companion object {
        private const val BASE_URL = "https://api.github.com"

        val githubApi: GithubAPI by lazy {
            createRetrofit(GithubAPI::class.java, BASE_URL)
        }

        private fun <T> createRetrofit(cls: Class<T>, baseUrl: String): T {
            val okHttpClient = OkHttpClient.Builder()
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(
                    GsonConverterFactory
                        .create()
                )
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()

            return retrofit.create(cls)
        }
    }

    fun users(query: String, sort: String, order: String) = githubApi.searchUsers(query, sort, order)
}