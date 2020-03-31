import com.tmo.gitsearch.activities.BasePresenterImpl
import com.tmo.gitsearch.activities.userlist.UserListContract
import com.tmo.gitsearch.data.remote.ApiManager
import com.tmo.gitsearch.data.remote.ErrorHandler
import rx.functions.Action1


class UserListPresenter : BasePresenterImpl<UserListContract.View>(), UserListContract.Presenter {

    companion object {
        private val SORTING_TYPE_REPO_COUNT = "repositories"
        private val SORTING_TYPE_FOLLOWER_COUNT = "followers"
    }

    override fun searchUsers(userName:String) {
        ApiManager.searchUsers(userName, SORTING_TYPE_REPO_COUNT)
            .doOnError { view?.showMessage(it.toString()) }
            .subscribe(
                Action1 { view?.showUsersList(it) },// reach back in to the View to show the data
                ErrorHandler(view, true, { throwable, errorBody, isNetwork -> view?.showError(throwable.message) })
            )
    }
}