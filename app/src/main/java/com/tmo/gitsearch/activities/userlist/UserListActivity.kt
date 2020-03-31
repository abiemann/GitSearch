package com.tmo.gitsearch.activities.userlist

import UserListPresenter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.tmo.gitsearch.R
import com.tmo.gitsearch.activities.BaseActivity
import com.tmo.gitsearch.activities.userdetail.UserDetailActivity
import com.tmo.gitsearch.model.ResponseSearchUser
import kotlinx.android.synthetic.main.activity_user_list.*

/**
 * This activity shows a list of users that were found on Github, given a search term
 */
class UserListActivity : BaseActivity<UserListContract.View, UserListPresenter>(), UserListContract.View {

    private var adapter: UserListAdapter? = null
    override var presenter: UserListPresenter = UserListPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        setTitle(getString(R.string.app_title))

        // initialize
        setUpRecyclerView()
        setUpEditText()
    }

    private fun setUpRecyclerView() {
        adapter = UserListAdapter(ArrayList(), {
            // for click events - launches the next activity
            startActivity(UserDetailActivity.newIntent(this, it.login))
        })
        recycler_repo_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_repo_list.adapter = adapter
    }

    private fun setUpEditText() {
        username_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                // if the user has typed at least 3 characters then search API is triggered
                if (p0.toString().length > 2) {
                    presenter.searchUsers(p0.toString())
                    showProgress()
                }
            }
        })
    }

    override fun showUsersList(searchResult: ResponseSearchUser) {
        adapter?.addUsers(searchResult.items)
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