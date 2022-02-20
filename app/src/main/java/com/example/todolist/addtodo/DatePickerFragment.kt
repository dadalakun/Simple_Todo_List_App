package com.example.todolist.addtodo

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.todolist.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var calendar: Calendar

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Initialize a calendar instance
        calendar  = Calendar.getInstance()

        // Get current time
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Return a date picker dialog
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    // When date set and press ok button in date picker dialog
    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
//        Toast.makeText(
//            activity,
//            "Date Set : ${formatDate(year,month,day)}"
//            ,Toast.LENGTH_SHORT
//        ).show()

        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        val selectedDate = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(calendar.time)

        val selectedDateBundle = Bundle()
        selectedDateBundle.putString("SELECTED_DATE", selectedDate)

        setFragmentResult("REQUEST_KEY", selectedDateBundle)
    }

    // Custom method to format date
    private fun formatDate(year:Int, month:Int, day:Int):String{
        // Create a Date variable/object with user chosen date
        calendar.set(year, month, day, 0, 0, 0)
        val chosenDate = calendar.time

        // Format the date picker selected date
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM)
        return df.format(chosenDate)
    }
}