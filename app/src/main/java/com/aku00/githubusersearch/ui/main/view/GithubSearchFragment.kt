package com.aku00.githubusersearch.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aku00.githubusersearch.R
import com.aku00.githubusersearch.data.GithubUserInfo
import com.aku00.githubusersearch.data.source.SearchRepository
import com.aku00.githubusersearch.ui.main.adapter.SearchResultAdapter
import com.aku00.githubusersearch.ui.main.viewmodel.GithubSearchViewModel
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A placeholder fragment containing a simple view.
 */
class GithubSearchFragment : Fragment() {

    private lateinit var searchViewModel: GithubSearchViewModel
    private lateinit var searchRepository: SearchRepository

    companion object {
        private const val ARG_REPO_TYPE = "arg_repo_type"
        const val REPO_TYPE_API = 1001
        const val REPO_TYPE_ROOM = 1002

        @JvmStatic
        fun getInstance(repoType: Int, searchRepository: SearchRepository): GithubSearchFragment {
            return GithubSearchFragment()
                .apply {
                    this.searchRepository = searchRepository
                    arguments = Bundle().apply {
                        putInt(ARG_REPO_TYPE, repoType)
                    }
                }
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        searchViewModel = ViewModelProviders.of(this).get(GithubSearchViewModel::class.java).apply {
            setRepository(searchRepository, arguments?.getInt(ARG_REPO_TYPE) ?: REPO_TYPE_API)
        }
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SearchResultAdapter(searchViewModel)
        recyclerUserList.layoutManager = LinearLayoutManager(activity)
        recyclerUserList.adapter = adapter

        searchViewModel.result.observe(viewLifecycleOwner, Observer<MutableList<GithubUserInfo>> {
            adapter.setData(it)
        })
    }

    fun getSearchUsers(keyword: String) {
        searchViewModel.onSearchUser(keyword)
    }

}