package com.aku00.githubusersearch.data.source

import com.aku00.githubusersearch.data.GithubUserInfo
import com.aku00.githubusersearch.data.source.search.LocalDataSource
import com.aku00.githubusersearch.data.source.search.RemoteDataSource
import com.aku00.githubusersearch.db.GithubRoomDB
import com.aku00.githubusersearch.network.GithubAPI
import io.reactivex.Single

class SearchRepository constructor(private val githubApi: GithubAPI,
                                  private val githubRoomDb: GithubRoomDB) {

    companion object {
        private var instance: SearchRepository? = null

        fun getInstance(githubApi: GithubAPI, githubRoomDb: GithubRoomDB) =
            instance ?: synchronized(this) {
                instance
                    ?: SearchRepository(githubApi, githubRoomDb).also { instance = it }
            }
    }

    fun searchUser(keyword: String): Single<MutableList<GithubUserInfo>> =
        RemoteDataSource(githubApi).searchUsers(keyword, "", "")
            .flatMap { githubResult ->
                searchUserRoom(keyword)
                    .map { roomList ->
                        githubResult.items.also { itemList ->
                            roomList.forEach { roomItem ->
                                // 동기화
                                itemList.filter {
                                    it.id == roomItem.id
                                }.forEach { it.isFavorite = true }
                            }
                        }
                    }
            }

    fun searchUserRoom(keyword: String): Single<MutableList<GithubUserInfo>> =
        LocalDataSource(githubRoomDb).searchUserRoom(keyword)

    fun getAllUserRoom(): Single<MutableList<GithubUserInfo>> =
        LocalDataSource(githubRoomDb).getAllUser()


    fun setFavorite(data: GithubUserInfo) {
        if(data.isFavorite) {
            LocalDataSource(githubRoomDb).insertGithubUser(data)
        } else {
            LocalDataSource(githubRoomDb).removeUser(data.login)
        }
    }
}