package com.aku00.githubusersearch.data.source.search

import com.aku00.githubusersearch.data.GithubUserInfo
import com.aku00.githubusersearch.db.GithubRoomDB
import com.aku00.githubusersearch.db.GithubUserInfoDao
import io.reactivex.Single

class LocalDataSource(githubRoomDb: GithubRoomDB) {

    private val githubUserDao: GithubUserInfoDao = githubRoomDb.githubUserDao()

    fun insertGithubUser(item: GithubUserInfo) {
        githubUserDao.insert(item)
    }

    fun getAllUser() = githubUserDao.selectUser()

    fun searchUserRoom(q: String): Single<MutableList<GithubUserInfo>> = githubUserDao.searchUser(q)

    fun removeUser(login: String) {
        githubUserDao.deleteUser(login)
    }
}