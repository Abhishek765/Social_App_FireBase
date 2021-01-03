package com.example.social_media_app.models

data class Post (
    val text: String = "",
    val createdBy: User= User(),
    val createdAt: Long = 0L,
    val likedBy: ArrayList<String> = ArrayList()
)