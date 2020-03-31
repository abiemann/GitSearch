package com.tmo.gitsearch.activities.userdetail

import com.tmo.gitsearch.activities.BasePresenterImpl
import com.tmo.gitsearch.data.remote.ApiManager
import com.tmo.gitsearch.data.remote.ErrorHandler
import rx.functions.Action1

class UserDetailPresenter : BasePresenterImpl<UserDetailContract.View>(), UserDetailContract.Presenter {

    override fun loadUserDetails(name:String) {
        ApiManager.userDetails(name)
            .doOnError { view?.showMessage(it.toString()) }
            .subscribe(
                Action1 { view?.showUserDetail(it) },
                ErrorHandler(view, true,
                    { throwable, errorBody, isNetwork -> view?.showError(throwable.message) })
            )
    }

    override fun loadUserRepos(name: String) {
        ApiManager.userRepos(name)
            .doOnError { view?.showMessage(it.toString()) }
            .subscribe(
                Action1 { view?.showRepoList(it) },
                ErrorHandler(view, true, { throwable, errorBody, isNetwork -> view?.showError(throwable.message) })
            )
    }
}