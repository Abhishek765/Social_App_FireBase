package com.example.social_media_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.social_media_app.models.Post
import com.example.social_media_app.models.User
import com.example.social_media_app.userDao.PostDao
import com.example.social_media_app.userDao.UserDao
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : AppCompatActivity(), IPostAdapter {

    private lateinit var postDao: PostDao
    private lateinit var userDao: UserDao
    private lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            val postIntent = Intent(this, CreatePostActivity::class.java)
            startActivity(postIntent)
        }
        userDao = UserDao()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        postDao = PostDao()

        val postCollections = postDao.postCollections
        val query = postCollections.orderBy("createdAt", Query.Direction.DESCENDING)
        val recyclerViewOptions =
            FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java).build()

        adapter = PostAdapter(recyclerViewOptions, this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onLikedClicked(postId: String) {
        postDao.updateLikes(postId)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.items_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.it_signOut -> {
                val auth = Firebase.auth

                auth.signOut()
//
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
                Toast.makeText(this, "Signed Out Successfully", Toast.LENGTH_SHORT).show()
            }
        }

        return super.onOptionsItemSelected(item)

    }
}