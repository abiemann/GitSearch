package com.tmo.gitsearch.activities.userdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tmo.gitsearch.R
import com.tmo.gitsearch.model.ResponseRepository
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_repository.*


class UserDetailAdapter(private val repoList:MutableList<ResponseRepository>, private val onClick:(String) -> Unit)
    : RecyclerView.Adapter<UserDetailAdapter.ViewHolder>() {

    private val backupList:MutableList<ResponseRepository> = mutableListOf<ResponseRepository>()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(repoList[position])
    }

    override fun getItemCount(): Int = repoList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false).let {
                ViewHolder(it, onClick)
            }
    }

    class ViewHolder(override val containerView: View,
                     private val onClick: (String) -> Unit) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindData(response:ResponseRepository) {
            with(response) {

                // these are the field names in layout XML - requires experimental flag
                repo_name.text = name
                fork_count.text = forks_count.toString()
                star_count.text = stargazers_count.toString()
                containerView.setOnClickListener { onClick(html_url) }
            }
        }
    }

    fun addRepos(listRepos: List<ResponseRepository>) {
        backupList.clear()
        backupList.addAll(listRepos)
        repoList.clear()
        repoList.addAll(listRepos)
    }

    // should only be called after addRepos
    fun filterRepos(search:String) {
        val tempList = mutableListOf<ResponseRepository>()
        for (item in backupList) {
            if (item.name.contains(search,true)) {
                tempList.add(item)
            }
        }
        repoList.clear()
        repoList.addAll(tempList)
    }
}