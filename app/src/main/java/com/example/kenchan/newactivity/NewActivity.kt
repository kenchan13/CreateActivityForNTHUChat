package com.example.kenchan.newactivity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_new.*
import java.text.SimpleDateFormat
import java.util.*


class NewActivity : AppCompatActivity() {

    var database = FirebaseDatabase.getInstance()
    var myRef = database.getReference("message")

    lateinit var eventTitleUI: EditText
    lateinit var startDateUI: TextView
    lateinit var endDateUI: TextView
    lateinit var startTimeUI: TextView
    lateinit var endTimeUI: TextView
    lateinit var addressUI: EditText
    lateinit var notesUI: EditText
    lateinit var buttonSendUI: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

        eventTitleUI = findViewById(R.id.eventTitle)
        startDateUI = findViewById(R.id.startDate)
        startTimeUI  = findViewById(R.id.startTime)
        endDateUI = findViewById(R.id.endDate)
        endTimeUI = findViewById(R.id.endTime)
        addressUI = findViewById(R.id.address)
        notesUI = findViewById(R.id.notes)

        // Call Calendar
        startDateUI.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view, mYear, mMonth, mDate ->
                startDateUI.setText(""+ mDate + "/" + mMonth + "/" + mYear)
            }, year, month, day)
            dpd.show()
        }

        endDateUI.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view, mYear, mMonth, mDate ->
                endDateUI.setText(""+ mDate + "/" + mMonth + "/" + mYear)
            }, year, month, day)
            dpd.show()
        }

        // Call TimePicker
        startTimeUI.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListenner = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                //set time to textview
                startTimeUI.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListenner, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }



        endTimeUI.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListenner = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                //set time to textview
                endTimeUI.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListenner, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }



        buttonSendUI = findViewById(R.id.buttonSend)
        buttonSendUI.setOnClickListener {
            sendSchedule()
        }
    }

    fun sendSchedule() {
        val name = eventTitleUI.text.toString()
        val startDate = startDateUI.text.toString()
        val startTime = startTimeUI.text.toString()
        val endDate = endDateUI.text.toString()
        val endTime = endTimeUI.text.toString()
        val notes = notesUI.text.toString()
        val address = notesUI.text.toString()




        if (name.isEmpty()) {
            TODO(" Empty Function")
            eventTitleUI.error = "Empty"
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
