package com.example.todolist.addtodo

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
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

        // Setup date picker  dialog
        val datePicker = DatePickerDialog(requireContext(), this, year, month, day)
        datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000)
        // Return a date picker dialog
        return datePicker
    }

    // When date set and press ok button in date picker dialog
    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        calendar.set(year, month, day)
//        val selectedDate = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(calendar.time)

        val selectedDateBundle = Bundle()
        selectedDateBundle.putSerializable("SELECTED_DATE", calendar.time)
        setFragmentResult("REQUEST_KEY", selectedDateBundle)
    }

}