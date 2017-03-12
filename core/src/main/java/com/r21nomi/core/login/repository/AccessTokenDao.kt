package com.r21nomi.core.login.repository

import com.google.firebase.database.*
import rx.Emitter
import rx.Observable
import javax.inject.Inject

/**
 * Created by r21nomi on 2017/03/11.
 */
class AccessTokenDao @Inject constructor(private val firebaseDatabase: FirebaseDatabase) {

    companion object {
        val NAME = "access_token"
    }

    fun set(token: String) {
        getDatabaseReference().setValue(token)
    }

    /**
     * Observe changes for accessToken on firebase database.
     * A value is emitted for many time until the Observable is unsubscribed.
     */
    fun observeChanges(): Observable<String> {
        var listener: ValueEventListener? = null

        return Observable.create<String>({ emitter ->
            listener = getDatabaseReference()
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val value = dataSnapshot.getValue(String::class.java)
                            emitter.onNext(value)
                        }

                        override fun onCancelled(error: DatabaseError) {
                            emitter.onError(error.toException())
                        }
                    })
        }, Emitter.BackpressureMode.LATEST).doOnUnsubscribe {
            getDatabaseReference().removeEventListener(listener)
        }
    }

    private fun getDatabaseReference(): DatabaseReference {
        return firebaseDatabase.getReference(NAME)
    }
}