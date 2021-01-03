package com.example.social_media_app.userDao

import android.util.Log
import android.widget.Toast
import com.example.social_media_app.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserDao {
    //    getting the database
    val db = FirebaseFirestore.getInstance()

    //    creating a collections
    val usersCollection = db.collection("users")

    //    function to add user
    fun addUsers(user: User?) {
        user?.let {
//            add the data into database firestore which should run in background thread
            GlobalScope.launch {
                usersCollection.document(user.uid).set(it)
            }
        }
    }

    fun getUserById(uId: String): Task<DocumentSnapshot> {
        return usersCollection.document(uId).get()
    }


}