package com.example.passfort
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class FirebaseConnecting {

    public fun basicReadWrite() {
        val db = Firebase.firestore

        val user = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815
        )

        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Timber.d("DocumentSnapshot added with ID: " + documentReference.id)
            }


        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Timber.d(document.id + " => " + document.data)
                }
            }
            .addOnFailureListener { exception ->
                Timber.w(exception, "Error getting documents.")
            }
    }
}