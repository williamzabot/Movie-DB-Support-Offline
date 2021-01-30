package com.williamzabot.fullmoviedb.model

data class User(
    val id : String,
    var fullName : String,
    var region : String,
    val email : String,
    var imageURL: String? = null
)