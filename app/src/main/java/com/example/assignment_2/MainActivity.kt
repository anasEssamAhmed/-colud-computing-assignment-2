package com.example.assignment_2

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    val db = Firebase.database
    val myRef = db.getReference("Upload")
    lateinit var storage: StorageReference
    lateinit var progressDialog: ProgressDialog
    lateinit var task: Task<Uri>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        storage = FirebaseStorage.getInstance().reference
        uploadFile.setOnClickListener {
            var i = Intent()
            i.type = "application/pdf"
            i.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(i, "Select PDF File ..."), 100)
        }
        showFiles.setOnClickListener {
            var i = Intent(this , showFilesPDF :: class.java)
            startActivity(i)
        }
        newButton.setOnClickListener {
            Toast.makeText(this, "New button clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()
            var ref: StorageReference = storage.child("Upload/" + UUID.randomUUID() + ".pdf")
            ref.putFile(data!!.data!!)
                .addOnSuccessListener { p0 ->
                    task = p0?.storage!!.downloadUrl.addOnSuccessListener {
                        var uri: Uri = task.result
                        var obj = classMyFile(pdfName.text.toString(), uri.toString())
                        myRef.child("${myRef.push().key!!}").setValue(obj)
                        Log.d("isMe", obj.toString())
                    }
                    Toast.makeText(applicationContext, "File Uploaded", Toast.LENGTH_LONG)
                        .show()
                    progressDialog.dismiss()
                }.addOnProgressListener { snapshot ->
                    var number = (100.0 * snapshot.bytesTransferred) / snapshot.totalByteCount
                    progressDialog.setMessage("Uploaded : ${number.toInt()} %")
                }
        }

    }

}