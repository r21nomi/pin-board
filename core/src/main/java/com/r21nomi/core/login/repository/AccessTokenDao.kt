package com.r21nomi.core.login.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

/**
 * Created by r21nomi on 2017/03/11.
 */
class AccessTokenDao @Inject constructor(val firebaseDatabase: FirebaseDatabase) {

    companion object {
        val NAME = "access_token"
    }

    fun set(token: String) {
        getDatabaseReference().setValue(token)
    }

    private fun getDatabaseReference(): DatabaseReference {
        return firebaseDatabase.getReference(NAME)
    }
}