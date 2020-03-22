package com.aku00.githubusersearch.network

import com.aku00.githubusersearch.data.GithubResult
import com.aku00.githubusersearch.data.GithubUserInfo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubAPI {
    @GET("/search/users?")
    fun searchUsers(
        @Query("q") q: String,
        @Query("sort") sort: String,
        @Query("order") order: String): Single<GithubResult>
}