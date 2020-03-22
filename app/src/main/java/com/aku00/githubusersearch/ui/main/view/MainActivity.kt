package com.aku00.githubusersearch.ui.main.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import androidx.lifecycle.ViewModelProviders
import com.aku00.githubusersearch.R
import com.aku00.githubusersearch.data.source.SearchRepository
import com.aku00.githubusersearch.db.GithubRoomDB
import com.aku00.githubusersearch.network.GithubRetrofit
import com.aku00.githubusersearch.ui.main.adapter.GithubSearchPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val githubSearchRepository: SearchRepository by lazy {
        SearchRepository.getInstance(GithubRetrofit.githubApi, GithubRoomDB.getInstance(application))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        val sectionsPagerAdapter = GithubSearchPagerAdapter(this, githubSearchRepository, supportFragmentManager)
        tabs.setupWithViewPager(viewPager)
        viewPager.adapter = sectionsPagerAdapter

        viewPager.addOnPageChangeListener(pageChangeListener)
        searchText.addTextChangedListener(textWatcher)
    }

    private val pageChangeListener = object: ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {}
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
        override fun onPageSelected(position: Int) {
            (viewPager.adapter as GithubSearchPagerAdapter).getFragment(viewPager.currentItem).getSearchUsers(searchText.text.toString())
        }
    }

    private val textWatcher = object: TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            (viewPager.adapter as GithubSearchPagerAdapter).getFragment(viewPager.currentItem).getSearchUsers(s.toString())
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

}