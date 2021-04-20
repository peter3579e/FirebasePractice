package com.peter.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import java.sql.Timestamp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val db = FirebaseFirestore.getInstance()
        val time = Timestamp(System.currentTimeMillis())

        val item = hashMapOf(
            "id" to "Peter",
            "title" to "Bishop",
            "content" to "article content",
            "tag" to "#Beauty",
            "author_id" to "3",
            "created_time" to time
        )

        db.collection("articles").document("HelloWorld")
            .set(item)
            .addOnSuccessListener { Log.d("Update", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w("Update", "Error writing document", e) }



        db.collection("articles")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        Log.d("Read", document.id + " => " + document.data)
                    }
                } else {
                    Log.w("Read", "Error getting documents.", task.exception)
                }
            }

        val docRef = db.collection("articles")
        docRef.addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, e ->
            if (e != null) {
                Log.w("Snap", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                for (document in snapshot!!) {
                    Log.d("Snap", document.id + " => " + document.data)
                }
            }
        }


    }
}