package com.tmo.gitsearch.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Responsible for adding the User Agent header
 * required, see https://developer.github.com/v3/#user-agent-required
 */
class InterceptorUserAgent(private val userAgent: String) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .header("User-Agent", "abiemann")
            .build()
        return chain.proceed(authenticatedRequest)
    }
}