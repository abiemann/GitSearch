package com.tmo.gitsearch.model

data class ResponseOwner(val login:String,//username
                         val id:Int,
                         val node_id:String,
                         val avatar_url:String,
                         val gravatar_id:String,
                         val url:String,
                         val html_url:String,
                         val followers_url:String,
                         val following_url:String,
                         val gists_url:String,
                         val starred_url:String,
                         val subscriptions_url:String,
                         val organizations_url:String,
                         val repos_url:String,
                         val events_url:String,
                         val received_events_url:String,
                         val type:String, //i.e. "User"
                         val site_admin:Boolean,
                         val name:String,
                         var company:String? = "", //CAN BE NULL
                         var blog:String? = "", //CAN BE NULL
                         var location:String? = "", //CAN BE NULL
                         var email:String? = "", //CAN BE NULL
                         var hireable:Boolean? = false,//CAN BE NULL
                         var bio:String? = "", //CAN BE NULL
                         val public_repos:Int,
                         val public_gists:Int,
                         val followers:Int,
                         val following:Int,
                         val created_at:String,
                         val updated_at:String
)