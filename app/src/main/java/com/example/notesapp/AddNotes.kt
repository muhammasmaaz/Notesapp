package com.example.notesapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesapp.Models.Note
import com.example.notesapp.databinding.ActivityAddNotesBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date

@AndroidEntryPoint

class AddNotes : AppCompatActivity() {
    private lateinit var binding: ActivityAddNotesBinding

    lateinit var note:Note

    lateinit var old_note:Note
    var isUpdate=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            old_note=intent.getSerializableExtra("current_note")as Note
            binding.etTitlenotes.setText(old_note.title)
            binding.etNotes.setText(old_note.note)
            isUpdate=true
        }catch (e:Exception){
            e.printStackTrace()
        }
        binding.imgSchecknotes.setOnClickListener {
            val title=binding.etTitlenotes.text.toString()
            val note_desc=binding.etNotes.text.toString()

            if (title.isNotEmpty()||note_desc.isNotEmpty()){
                val formatter=SimpleDateFormat("EEE, d MMM YYYY HH:mm a")

                if (isUpdate)
                {
                    note=Note(old_note.id,title,note_desc,formatter.format(Date()))
                }
                else{

                    note=Note(null,title,note_desc,formatter.format(Date()))
                }

                val intent= Intent()
                intent.putExtra("note",note)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }else{
                Toast.makeText(this@AddNotes,"Please enter some data ",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


        }

        binding.imgBackArrowNotes.setOnClickListener {
            onBackPressed()
        }


    }
}