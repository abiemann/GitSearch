package com.tmo.gitsearch.data.remote

import com.tmo.gitsearch.model.ResponseOwner
import com.tmo.gitsearch.model.ResponseRepository
import com.tmo.gitsearch.model.ResponseSearchUser
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

interface GithubService {

    // Find users via various criteria. This method returns up to 100 results per page.
    // see https://developer.github.com/v3/search/#search-users
    @GET("search/users")
    fun getSearchUsers(@Query("q") usernameSearch:String, @Query("sort") sorting:String): Observable<ResponseSearchUser>

    // Get information on a single user.
    // see https://developer.github.com/v3/users/#get-a-single-user
    @GET("users/{username}")
    fun getUserDetails(@Path("username") owner:String):Observable<ResponseOwner>

    // List repositories for the authenticated user
    // see https://developer.github.com/v3/repos/#list-repositories-for-the-authenticated-user
    @GET("users/{username}/repos")
    fun getUserRepos(@Path("username") owner:String): Observable<List<ResponseRepository>>

}