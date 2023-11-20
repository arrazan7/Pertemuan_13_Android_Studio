package com.example.pertemuan_13_2

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pertemuan_13_2.database.NoteDao
import com.example.pertemuan_13_2.database.NoteRoomDatabase
import com.example.pertemuan_13_2.database.Notes
import com.example.pertemuan_13_2.databinding.ActivityMain3Binding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityMain3Binding
    private lateinit var mNoteDao: NoteDao
    private lateinit var executorService: ExecutorService
    private var updateId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        executorService = Executors.newSingleThreadExecutor()
        val db = NoteRoomDatabase.getDatabase(this)
        mNoteDao = db!!.noteDao()!!

        with(binding) {
            val id = intent.getIntExtra(MainActivity.EXTRA_ID, 3)
            val title = intent.getStringExtra(MainActivity.EXTRA_TITLE)
            val desc = intent.getStringExtra(MainActivity.EXTRA_DESC)
            val date = intent.getStringExtra(MainActivity.EXTRA_DATE)

            txtTitle.setText(title)
            txtDesc.setText(desc)
            txtDate.setText(date)

            btnUpdate.setOnClickListener {
                update(
                    Notes(
                    id = id,
                    title = txtTitle.text.toString(),
                    description = txtDesc.text.toString(),
                    date = txtDate.text.toString())
                )
                updateId = 0
                startActivity(Intent(this@MainActivity3, MainActivity::class.java))
            }

            btnDelete.setOnClickListener {
                val noteToDelete = Notes(id = id, "","","")

                delete(noteToDelete)
                startActivity(Intent(this@MainActivity3, MainActivity::class.java))
            }
        }
    }

    private fun insert(note: Notes) {
        executorService.execute {mNoteDao.insert(note)}
    }
    private fun update(note: Notes) {
        executorService.execute {mNoteDao.update(note)}
    }
    private fun delete(note: Notes) {
        executorService.execute {mNoteDao.delete(note)}
    }
}