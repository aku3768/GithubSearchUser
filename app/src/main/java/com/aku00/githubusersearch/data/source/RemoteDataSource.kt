package com.aku00.githubusersearch.data.source.search

import com.aku00.githubusersearch.data.GithubResult
import com.aku00.githubusersearch.data.GithubUserInfo
import com.aku00.githubusersearch.network.GithubAPI
import io.reactivex.Single

class RemoteDataSource(private val githubApi: GithubAPI) {

    fun searchUsers(keyword: String, sort: String, order: String): Single<GithubResult> =
            githubApi.searchUsers(keyword, "", "")
}