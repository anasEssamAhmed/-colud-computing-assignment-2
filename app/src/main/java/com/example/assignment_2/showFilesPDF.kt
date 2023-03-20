package com.example.assignment_2

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_show_files_pdf.*

class showFilesPDF : AppCompatActivity() {
    var db = Firebase.database
    var arrayData = ArrayList<data>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_files_pdf)
        rec.layoutManager = LinearLayoutManager(this)
        val myRef = db.getReference("Upload")
        // Read from the database
        myRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = snapshot.children.forEach {
                    var name = it.child("name").value
                    var url = it.child("url").value
                    Log.d("aa" , name.toString())
                    Log.d("aa" , url.toString())
                    arrayData.add(data( name.toString(), Uri.parse(url.toString())))
                    var adapter = adapter(applicationContext, arrayData)
                    rec.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Failed to read value.", Toast.LENGTH_LONG).show()
            }

        })
    }
}