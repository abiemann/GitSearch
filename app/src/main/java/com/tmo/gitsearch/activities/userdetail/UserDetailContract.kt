package com.tmo.gitsearch.activities.userdetail

import com.tmo.gitsearch.activities.BasePresenter
import com.tmo.gitsearch.activities.BaseView
import com.tmo.gitsearch.model.ResponseOwner
import com.tmo.gitsearch.model.ResponseRepository

object UserDetailContract {

    interface View : BaseView {
        fun showUserDetail(userDetail: ResponseOwner)
        fun showRepoList(searchResult: List<ResponseRepository>)
    }

    interface Presenter : BasePresenter<View> {
        fun loadUserDetails(name:String)
        fun loadUserRepos(name:String)
    }

}