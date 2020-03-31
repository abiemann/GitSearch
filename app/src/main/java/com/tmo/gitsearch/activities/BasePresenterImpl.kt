package com.tmo.gitsearch.activities

/**
 * Allows attaching / detaching the view
 */
open class BasePresenterImpl<V:BaseView> :BasePresenter<V> {

    protected var view: V? = null

    override fun attachView(view:V) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }
}