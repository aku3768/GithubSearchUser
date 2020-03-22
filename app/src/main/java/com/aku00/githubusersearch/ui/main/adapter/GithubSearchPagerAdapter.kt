package com.aku00.githubusersearch.ui.main.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.aku00.githubusersearch.R
import com.aku00.githubusersearch.data.source.SearchRepository
import com.aku00.githubusersearch.db.GithubRoomDB
import com.aku00.githubusersearch.network.GithubRetrofit
import com.aku00.githubusersearch.ui.main.view.GithubSearchFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

class GithubSearchPagerAdapter(private val context: Context, private val searchRepository: SearchRepository, fm: FragmentManager) :
    FragmentStatePagerAdapter(fm) {

    private val searchFragment: Fragment by lazy {
        GithubSearchFragment.getInstance(GithubSearchFragment.REPO_TYPE_API, searchRepository)
    }

    private val favoriteFragment: Fragment by lazy {
        GithubSearchFragment.getInstance(GithubSearchFragment.REPO_TYPE_ROOM, searchRepository)
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> searchFragment
            1 -> favoriteFragment
            else -> Fragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }

    fun getFragment(position: Int): GithubSearchFragment {
        return when(position) {
            0 -> searchFragment as GithubSearchFragment
            else -> favoriteFragment as GithubSearchFragment
        }
    }
}