package com.tmo.gitsearch.activities.userlist

import com.tmo.gitsearch.activities.BasePresenter
import com.tmo.gitsearch.activities.BaseView
import com.tmo.gitsearch.model.ResponseSearchUser

object UserListContract {

    interface View : BaseView {
        fun showUsersList(searchResult: ResponseSearchUser)
    }

    interface Presenter : BasePresenter<View> {
        fun searchUsers(userName:String)
    }

}