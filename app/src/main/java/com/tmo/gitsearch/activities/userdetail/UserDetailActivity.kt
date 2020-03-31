package com.tmo.gitsearch.activities.userdetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.tmo.gitsearch.R
import com.tmo.gitsearch.activities.BaseActivity
import com.tmo.gitsearch.formatDate
import com.tmo.gitsearch.model.ResponseOwner
import com.tmo.gitsearch.model.ResponseRepository
import kotlinx.android.synthetic.main.activity_repo_detail.*

class UserDetailActivity : BaseActivity<UserDetailContract.View, UserDetailPresenter>(), UserDetailContract.View {

    private var adapter: UserDetailAdapter? = null
    override var presenter: UserDetailPresenter = UserDetailPresenter()

    companion object {
        private const val EXTRA_NAME = "extra_name"

        fun newIntent(context: Context, name: String): Intent =
            Intent(context, UserDetailActivity::class.java).apply {
                putExtra(EXTRA_NAME, name)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_detail)
        setTitle(getString(R.string.app_title))

        // initialize
        setUpRecyclerView()
        setUpEditText()

        // make initial API call
        presenter.loadUserDetails(intent.getStringExtra(EXTRA_NAME))
        showProgress()
    }

    private fun setUpRecyclerView() {
        adapter = UserDetailAdapter(
            ArrayList(),
            {
                // for click events - launch the webview with the repo URL
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse(it)
                startActivity(openURL)
            }
        )
        recycler_repo_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_repo_list.adapter = adapter
    }

    private fun setUpEditText() {
        repo_name_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                // Since there is no API call triggered (data is cached)
                //  the user only needs to type 1 character to filter the list
                if (p0.toString().length > 0) {
                    adapter?.filterRepos(p0.toString())
                    adapter?.notifyDataSetChanged()
                } else {
                    // reset list
                    adapter?.filterRepos("")
                    adapter?.notifyDataSetChanged()
                }
            }
        })
    }

    override fun showUserDetail(userDetail: ResponseOwner) {
        // with so many fields, my strategy is to build a string and set it to TextView
        val builder = StringBuilder()
        with(userDetail) {

            // Username
            builder.append(login).append(System.lineSeparator())

            // Email
            if (null != email && email!!.length > 0) builder.append(email).append(System.lineSeparator())

            // Location
            if (null != location && location!!.length > 0) builder.append(location).append(System.lineSeparator())

            // Join Date
            builder.append(created_at.formatDate()).append(System.lineSeparator())

            // x Followers
            builder.append(followers).append(getString(R.string.user_detail_followers)).append(System.lineSeparator())

            // Following x
            builder.append(getString(R.string.user_detail_following)).append(following).append(System.lineSeparator())

            // Biography
            if (null != bio && bio!!.length > 0) builder.append(bio)

            // NOTE: no System.lineSeparator() for the last line added
        }

        user_details.text = builder.toString()

        Picasso.get().load(userDetail.avatar_url).into(avatar)

        // load users' repos next
        presenter.loadUserRepos(intent.getStringExtra(EXTRA_NAME))
    }

    override fun showRepoList(searchResult: List<ResponseRepository>) {
        adapter?.addRepos(searchResult)
        adapter?.notifyDataSetChanged()
        hideProgress()
    }

    private fun showProgress() {
        recycler_repo_list.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        recycler_repo_list.visibility = View.VISIBLE
        progress_bar.visibility = View.GONE
    }

    override fun showError(error: String?) {
        super.showError(error)
        progress_bar.visibility = View.GONE
    }
}