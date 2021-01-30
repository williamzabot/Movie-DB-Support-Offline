package com.williamzabot.fullmoviedb.data.db.remotedb

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.williamzabot.fullmoviedb.model.User

object RemoteUserDAO {

    private val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser
    private val db = FirebaseDatabase.getInstance().getReference("user/${currentUser?.uid}/")

    fun saveUser(name: String, region : String): Task<Void>? {
        val key = db.push().key
        val user = if (key != null && currentUser != null && currentUser.email != null) {
            User(key, name, currentUser.email!!, region)
        } else {
            null
        }

        return if (user != null) {
            db.setValue(user)
        } else {
            null
        }
    }


}