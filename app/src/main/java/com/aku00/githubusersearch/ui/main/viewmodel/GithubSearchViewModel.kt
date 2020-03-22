package com.aku00.githubusersearch.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import com.aku00.githubusersearch.data.GithubUserInfo
import com.aku00.githubusersearch.data.source.SearchRepository
import com.aku00.githubusersearch.ui.base.BaseViewModel
import com.aku00.githubusersearch.ui.main.view.GithubSearchFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GithubSearchViewModel : BaseViewModel() {
    val result = MutableLiveData<MutableList<GithubUserInfo>>()
    private lateinit var repository: SearchRepository
    private var repoType: Int = GithubSearchFragment.REPO_TYPE_API;

    fun setRepository(searchRepository: SearchRepository, repoType: Int) {
        this.repository = searchRepository
        this.repoType = repoType
    }

    fun onSearchUser(keyword: String) {
        disposables.add(
            when (repoType) {
                GithubSearchFragment.REPO_TYPE_API -> {
                    repository.searchUser(keyword)
                }
                else -> {
                    if(keyword.isEmpty()) {
                        repository.getAllUserRoom()
                    } else {
                        repository.searchUserRoom(keyword)
                    }
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result.postValue(it) }){}
        )
    }

    fun onGetAllUser() {
        disposables.add(
            repository.getAllUserRoom()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result.postValue(it) }){}
        )
    }

    fun onSetFavorite(position: Int, isSelected: Boolean) {
        result.value?.let {userInfo ->
            userInfo[position].isFavorite = isSelected

            Observable.just(isSelected)
                .subscribeOn(Schedulers.io())
                .subscribe( {
                    if(isSelected) {
                        repository.setFavorite(userInfo[position])
                    } else {
                        repository.setFavorite(userInfo[position])
                    }
                    result.postValue(userInfo)
                }){}
        }
    }

}

