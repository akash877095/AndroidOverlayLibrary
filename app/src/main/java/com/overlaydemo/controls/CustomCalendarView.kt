package com.overlaydemo.controls

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CalendarView
import android.widget.FrameLayout
import android.widget.Spinner
import com.overlaydemo.R
import java.util.*

class CustomCalendarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var calendarView: CalendarView
    private var monthSpinner: Spinner
    private var yearSpinner: Spinner
    private var onDateChangeListener: OnDateChangeListener? = null

    init {
        View.inflate(context, R.layout.custom_calendar_view, this)

        calendarView = findViewById(R.id.calendar_view)
        monthSpinner = findViewById(R.id.month_spinner)
        yearSpinner = findViewById(R.id.year_spinner)

        // Set up month spinner
        val monthAdapter = ArrayAdapter.createFromResource(
            context,
            R.array.months_array,
            android.R.layout.simple_spinner_item
        )
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        monthSpinner.adapter = monthAdapter
        monthSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val month = position // Month is zero-based
                val year = yearSpinner.selectedItem.toString().toInt()
                setSelectedDate(
                    year,
                    month,
                    1
                ) // Set the selected date to the first day of the selected month
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        // Set up year spinner
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val yearsList = mutableListOf<String>()

        for (i in currentYear - 10..currentYear + 20) {
            yearsList.add(i.toString())
        }
        val yearAdapter = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_item,
            yearsList
        )
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        yearSpinner.adapter = yearAdapter
        yearSpinner.setSelection(currentYear) // Select the current year
        yearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val year = yearSpinner.selectedItem.toString().toInt()
                val month = monthSpinner.selectedItemPosition // Month is zero-based
                setSelectedDate(
                    year,
                    month,
                    1
                ) // Set the selected date to the first day of the selected month
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        // Set the initial selected date to the current date
        val currentDate = Calendar.getInstance()
        val initialYear = currentDate.get(Calendar.YEAR)
        val initialMonth = currentDate.get(Calendar.MONTH)
        val initialDay = currentDate.get(Calendar.DAY_OF_MONTH)
        setSelectedDate(initialYear, initialMonth, initialDay)

        // Set the click listener for the calendar view
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            onDateChangeListener?.onSelectedDayChange(calendarView, year, month, dayOfMonth)
        }
    }

    fun setSelectedDate(year: Int, month: Int, day: Int) {
        val selectedDate = Calendar.getInstance()
        selectedDate.set(year, month, day)
        calendarView.setDate(selectedDate.timeInMillis, false, true)

        onDateChangeListener?.onSelectedDayChange(calendarView, year, month, day)
    }

    fun setOnDateChangeListener(listener: OnDateChangeListener?) {
        onDateChangeListener = listener
    }

    interface OnDateChangeListener {
        fun onSelectedDayChange(view: CalendarView, year: Int, month: Int, day: Int)
    }

}
