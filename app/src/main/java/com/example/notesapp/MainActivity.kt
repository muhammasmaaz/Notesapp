package com.example.notesapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.Adapter.NotesAdapter
import com.example.notesapp.Database.NoteDatabase
import com.example.notesapp.Models.Note
import com.example.notesapp.Models.NoteViewModel
import com.example.notesapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() ,NotesAdapter.Notesclicklistner,PopupMenu.OnMenuItemClickListener{

    private lateinit var binding: ActivityMainBinding

    lateinit var database:NoteDatabase

    val noteviewModel:NoteViewModel by viewModels()
    lateinit var adapter: NotesAdapter

    lateinit var selectedNote:Note

    private val updateNotes=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        if (result.resultCode==Activity.RESULT_OK){
            val note=result.data?.getSerializableExtra("note")as? Note
            if (note!=null){
                noteviewModel.updateNote(note)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
//        noteviewModel=ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        noteviewModel.allNotes.observe(this){list->
            list?.let {
                adapter.updateList(list)
            }
        }
        database=NoteDatabase.getDatabase(this)
    }

    private fun initUI() {

        binding.recyclerviewnotes.setHasFixedSize(true)
        binding.recyclerviewnotes.layoutManager=StaggeredGridLayoutManager(2,LinearLayout.VERTICAL)
        adapter= NotesAdapter(this,this)
        binding.recyclerviewnotes.adapter=adapter

        val getContent=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->

            if (result.resultCode==Activity.RESULT_OK)
            {
                val note=result.data?.getSerializableExtra("note")as? Note
                if (note!=null){
                    noteviewModel.insertNote(note)
                }


            }

        }

        binding.fbAddNote.setOnClickListener{
            val intent= Intent(this,AddNotes::class.java)
            getContent.launch(intent)
        }
        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!=null){
                    adapter.filterList(newText)
                }
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
        })

    }

    override fun onItemClicked(note: Note) {
        val intent=Intent(this@MainActivity,AddNotes::class.java)
        intent.putExtra("current_note",note)
        updateNotes.launch(intent)
    }

    override fun onLongItemClicked(note: Note, cardView: CardView) {
        selectedNote=note
        popUpDisplay(cardView)
    }

    private fun popUpDisplay(cardView: CardView) {

        val popup=PopupMenu(this,cardView)
        popup.setOnMenuItemClickListener(this@MainActivity)
        popup.inflate(R.menu.pop_up_menu)
        popup.show()

    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId==R.id.delete_note){
            noteviewModel.deleteNote(selectedNote)
            return true
        }
        return false
    }

}


