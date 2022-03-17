package com.example.dobcalc

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var displayDate: TextView? = null
    private var minutesText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatepicker: Button = findViewById(R.id.btnDatePicker)
        displayDate = findViewById(R.id.displayDate)
        minutesText= findViewById(R.id.minutesText)

        btnDatepicker.setOnClickListener {
            clickDatePicker()
        }

    }

    private fun clickDatePicker() {
        Toast.makeText(this, "Date picker button pressed", Toast.LENGTH_LONG).show()
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val selectedDate = mutableMapOf<String, Int>()

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
            Toast.makeText(this, "Date has been set", Toast.LENGTH_LONG).show()
//            println(i.toString())
//            println(i2.toString())
//            println(i3.toString())
            displayDate?.text = "$i3/${i2+1}/$i"

//            My implementation
//            updateMinutes(i, i2, i3)

//            Course implementation
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val theDate = sdf.parse("$i3/${i2+1}/$i")
            var selectedDateInMinutes: Long = 0
            val currentDateInMinutes: Long = 0

            //The safe call operator together with let is used as SimpleDateFormat.parse() returns a value of type Date!, the ! mark means "the null-ability is unknown"
            theDate?.let{
                selectedDateInMinutes = theDate.time/60000
            }

            val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

            currentDate?.let{
                val currentDateInMinutes = currentDate.time/60000
            }
            val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes

            minutesText?.text = differenceInMinutes.toString()

        }, year, month, day)
        dpd.datePicker.maxDate = System.currentTimeMillis() - (24*60*60*1000)
        dpd.show()
    }

    private fun updateMinutes(year: Int, month: Int, day: Int){
        val DOB = Calendar.getInstance()
        DOB.set(year, month, day)
        val now = Calendar.getInstance()

        val minutes = (now.timeInMillis - DOB.timeInMillis)/(1000*60)
        println("Minutes = $minutes")

        val minutesText: TextView = findViewById(R.id.minutesText)
        minutesText?.setText("$minutesText")
    }

}
