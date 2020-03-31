package com.tmo.gitsearch.activities

interface BasePresenter<in V : BaseView> {
    fun attachView(view: V)
    fun detachView()
}