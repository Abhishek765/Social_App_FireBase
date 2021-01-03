package com.example.social_media_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.social_media_app.userDao.PostDao
import kotlinx.android.synthetic.main.activity_create_post.*

class CreatePostActivity : AppCompatActivity() {
    private lateinit var postDao: PostDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        postDao = PostDao()

        bt_postButton.setOnClickListener {
            val input = et_postInput.text.toString()
            if(input.isNotEmpty()){
                postDao.addPosts(input)
                finish()
            }
        }
    }
}