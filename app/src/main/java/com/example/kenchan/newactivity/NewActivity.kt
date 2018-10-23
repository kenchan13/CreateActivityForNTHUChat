package com.example.kenchan.newactivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_new.*


class NewActivity : AppCompatActivity() {

    var database = FirebaseDatabase.getInstance()
    var myRef = database.getReference("message")

    lateinit var editTextActivityName: EditText
    lateinit var editTextStartDate: EditText
    lateinit var editTextStartTime: EditText
    lateinit var editTextEndDate: EditText
    lateinit var editTextEndTime: EditText
    lateinit var editTextAddress: EditText
    lateinit var editTextNotes: EditText
    lateinit var buttonSend: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

        editTextActivityName = findViewById(R.id.editTextActivityName)
        editTextStartDate = findViewById(R.id.editTextStartDate)
        editTextStartTime = findViewById(R.id.editTextStartTime)
        editTextEndDate = findViewById(R.id.editTextEndDate)
        editTextEndTime = findViewById(R.id.editTextEndTime)
        editTextAddress = findViewById(R.id.editTextAddress)
        editTextNotes = findViewById(R.id.editTextNotes)

        buttonSend = findViewById(R.id.buttonSend)
        buttonSend.setOnClickListener {
            sendSchedule()
        }
    }

    fun sendSchedule() {
        val name = editTextActivityName.text.toString()
        val startDate = editTextStartDate.text.toString()
        val startTime = editTextStartTime.text.toString()
        val endDate = editTextStartDate.text.toString()
        val endTime = editTextEndDate.text.toString()
        val notes = editTextNotes.text.toString()
        val address = editTextAddress.text.toString()

        if (name.isEmpty()) {
            editTextActivityName.error = "Empty"
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("Schedule")
        val scheduleId = ref.push().key.toString()

        val schedule = Schedule(name, startDate, startTime, endDate, endTime, address, notes)

        ref.child(scheduleId).setValue(schedule).addOnCompleteListener() {
            Toast.makeText(applicationContext, "Schedule Add Success!", Toast.LENGTH_LONG).show()
        }

    }
}
