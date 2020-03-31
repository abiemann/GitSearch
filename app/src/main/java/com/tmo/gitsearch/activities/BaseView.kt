package com.tmo.gitsearch.activities

import android.content.Context
import androidx.annotation.StringRes

interface BaseView {
    fun getContext(): Context
    fun showMessage(@StringRes srtResId: Int)
    fun showMessage(message: String)
    fun showError(error: String?)
    fun showError(@StringRes stringResId: Int)
}

