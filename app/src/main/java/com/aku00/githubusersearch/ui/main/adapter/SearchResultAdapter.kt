package com.aku00.githubusersearch.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aku00.githubusersearch.R
import com.aku00.githubusersearch.data.GithubUserInfo
import com.aku00.githubusersearch.ui.main.viewmodel.GithubSearchViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_user_info.view.*

class SearchResultAdapter(val viewModel: GithubSearchViewModel): RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {

    private var data: MutableList<GithubUserInfo> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_info, parent, false)
        return SearchResultViewHolder(view, viewModel)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val userInfo: GithubUserInfo = data[position]
        holder.avatar.loadUrl(userInfo.avatar_url)
        holder.login.text = userInfo.login
        holder.favorite.isSelected = userInfo.isFavorite
    }

    override fun getItemCount() = data.size

    fun setData(it: MutableList<GithubUserInfo>?) {
        data.clear()
        data.addAll(it?: listOf())
        notifyDataSetChanged()
    }

    fun getData(position: Int): GithubUserInfo {
        return data[position]
    }

    class SearchResultViewHolder(view: View, viewModel: GithubSearchViewModel) : RecyclerView.ViewHolder(view) {
        val avatar: ImageView = view.avatar
        val login: TextView = view.login
        val favorite: ImageView = view.favorite

        init {
            favorite.setOnClickListener({
                it.isSelected = !it.isSelected
                viewModel.onSetFavorite(adapterPosition, it.isSelected)
            })
        }
    }

    fun ImageView.loadUrl(url: String?, placeholder: Int? = null) {
        Glide.with(context)
            .load(url)
            .into(this)
    }
}
