package com.tmo.gitsearch.data.remote

import androidx.annotation.StringRes
import com.tmo.gitsearch.R
import com.tmo.gitsearch.activities.BaseView
import retrofit2.adapter.rxjava.HttpException
import rx.functions.Action1
import java.lang.ref.WeakReference
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ErrorHandler(view:BaseView? = null,
                   private val mShowError:Boolean = false,
                   val onFailure:(Throwable, ErrorBody?, Boolean) -> Unit)
    : Action1<Throwable> {

    private val viewReference: WeakReference<BaseView>

    init {
        // complains about view:BaseView not being required - but works. To refactor...
        // TODO 1. make view:BaseView non-null
        // TODO 2. when creating the presenter must pass the view as an argument In its constructor
        // However, this will remove the attach/detach feature of the View
        viewReference = WeakReference<BaseView>(view)
    }

    override fun call(throwable: Throwable) {
        var isNetwork = false
        var errorBody: ErrorBody? = null
        if (isNetworkError(throwable)) {
            isNetwork = true
            showMessage(R.string.internet_connection_not_available)
        } else if (throwable is HttpException) {
            errorBody = ErrorBody.parseError(throwable.response())
            if (errorBody != null) {
                handleError(errorBody)
            }
        }
        onFailure(throwable, errorBody, isNetwork)
    }

    private fun isNetworkError(throwable: Throwable): Boolean {
        return throwable is SocketException
                || throwable is UnknownHostException
                || throwable is SocketTimeoutException
    }

    private fun handleError(errorBody: ErrorBody) {
        if (errorBody.code != ErrorBody.UNKNOWN_ERROR) {
            showErrorIfRequired(R.string.server_error)
        }
    }

    private fun showMessage(@StringRes strResId: Int) {
        viewReference.get()?.showMessage(strResId)
    }

    private fun showErrorIfRequired(@StringRes strResId: Int) {
        if (mShowError) {
            viewReference.get()?.showError(strResId)
        }
    }
}