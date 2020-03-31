package com.tmo.gitsearch.model

data class ResponseSearchUser(
    val total_count:Int,
    val incomplete_results:Boolean,
    val items:List<ResponseSearchUserItem>
)

