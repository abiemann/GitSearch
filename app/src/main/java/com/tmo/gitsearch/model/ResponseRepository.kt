package com.tmo.gitsearch.model

data class ResponseRepository(val id:Long,
                              val name:String, // this is repo name
                              val html_url:String,
                              val forks_count:Int,
                              val stargazers_count:Int)