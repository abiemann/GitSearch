package com.tmo.gitsearch.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.tmo.gitsearch.fromJson
import retrofit2.Response
import java.io.IOException

data class ErrorBody(val code:Int, @Json(name = "error") private val message:String) {

    override fun toString():String = "{Code:$code, Msg:\"$message\"}"

    /**
     * If you need to write a function that can be called without having a class instance but needs
     * access to the internals of a class, you can write it as a member of a companion object
     */
    companion object {

        val UNKNOWN_ERROR = 0
        private val moshi = Moshi.Builder().build()

        fun parseError(response: Response<*>?): ErrorBody? {
            return (response?.errorBody())?.let {
                try {
                    moshi.fromJson(it.string())
                } catch (ignored: IOException) {
                    null
                }
            }
        }
    }

}