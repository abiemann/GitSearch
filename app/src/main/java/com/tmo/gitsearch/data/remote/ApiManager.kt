package com.tmo.gitsearch.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


object ApiManager {
    private const val BASE_URL: String = "https://api.github.com/"
    private const val USER_AGENT: String = "abiemann"//can be anything: username, project name, etc

    private lateinit var githubService: GithubService

    init {
        val retrofit = initRetrofit()
        initServices(retrofit)
    }

    private fun initRetrofit(): Retrofit {
        val interceptorLogging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder().apply {
            networkInterceptors().add(Interceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .method(original.method(), original.body())
                    .build()
                chain.proceed(request)
            })
            addInterceptor(interceptorLogging)
            addInterceptor(InterceptorUserAgent(USER_AGENT))//required by github
        }

        return Retrofit.Builder().baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(createMoshiConverter())
            .client(client.build())
            .build()
    }

    private fun createMoshiConverter(): MoshiConverterFactory = MoshiConverterFactory.create()

    private fun initServices(retrofit: Retrofit) {
        githubService = retrofit.create(GithubService::class.java)
    }

    // for list view
    fun searchUsers(usernameSearch:String, sortingType:String) =
        githubService.getSearchUsers(usernameSearch, sortingType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!

    // for detail view
    fun userDetails(name:String) =
        githubService.getUserDetails(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!

    // for detail view
    fun userRepos(name:String) =
        githubService.getUserRepos(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!

}