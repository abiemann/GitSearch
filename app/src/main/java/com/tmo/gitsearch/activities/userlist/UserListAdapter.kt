package com.tmo.gitsearch.activities.userlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tmo.gitsearch.R
import com.tmo.gitsearch.model.ResponseSearchUserItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_user.*


class UserListAdapter(private val userList:MutableList<ResponseSearchUserItem>, private val onClick:(ResponseSearchUserItem) -> Unit)
    : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false).let {
                ViewHolder(it, onClick)
            }
    }

    class ViewHolder(override val containerView: View,
                     private val onClick: (ResponseSearchUserItem) -> Unit) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindData(user:ResponseSearchUserItem) {
            with(user) {

                // these are the field names in layout XML - requires experimental flag
                Picasso.get().load(avatar_url).fit().into(avatar)
                username.text = login //username
                repo_count.text = "0"//TODO find out where the count is in the Github API response data
                containerView.setOnClickListener { onClick(this) }
            }
        }
    }

    fun addUsers(newUsers: List<ResponseSearchUserItem>) {
        userList.clear()
        userList.addAll(newUsers)
    }
}