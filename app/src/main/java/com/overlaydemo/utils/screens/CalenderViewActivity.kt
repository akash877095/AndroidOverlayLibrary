package com.overlaydemo.utils.screens

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.overlaydemo.R
import com.overlaydemo.databinding.ActivityCalenderViewBinding
import java.time.YearMonth
import java.util.Calendar

class CalenderViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalenderViewBinding

    private var selectDay = 0

    private var finalDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalenderViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {

        val monthsArray = resources.getStringArray(R.array.months_array)
        val monthAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, monthsArray)
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spSelectMonth.adapter = monthAdapter

        val calendarInstance = Calendar.getInstance()

        val currentDayOfTheMonth = calendarInstance.get(Calendar.DAY_OF_MONTH)
        selectDay = currentDayOfTheMonth

        val currentMonth = calendarInstance.get(Calendar.MONTH)
        binding.spSelectMonth.setSelection(currentMonth)

        binding.spSelectMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val year = binding.spSelectYear.selectedItem.toString().toInt()
                val calInstance = Calendar.getInstance()
                val maxDayOfMonth = YearMonth.of(year, position + 1).lengthOfMonth()

                finalDate = "$year-${position + 1}-$selectDay"
                binding.tvDateView.text = finalDate

                if (maxDayOfMonth < 30) {
                    calInstance.set(year, position, maxDayOfMonth)
                } else {
                    calInstance.set(year, position, selectDay)
                }

                binding.cvCalendarView.setDate(calInstance.timeInMillis, false, true)

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}

        }

        // Set Year Spinner
        val currentYear = calendarInstance.get(Calendar.YEAR)
        val yearsList = mutableListOf<String>()

        for (i in currentYear - 10..currentYear + 10) { yearsList.add(i.toString()) }

        val yearAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, yearsList)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spSelectYear.adapter = yearAdapter

        val yearPosition = yearsList.indexOf(currentYear.toString())
        binding.spSelectYear.setSelection(yearPosition)

        binding.spSelectYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {

                val year = binding.spSelectYear.selectedItem.toString().toInt()
                val month = binding.spSelectMonth.selectedItemPosition

                val calInstance = Calendar.getInstance()

                finalDate = "$year-${month + 1}-$selectDay"
                binding.tvDateView.text = finalDate

                calInstance.set(year, month, selectDay)

                binding.cvCalendarView.setDate(calInstance.timeInMillis, false, true)

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}

        }

        binding.cvCalendarView.setOnDateChangeListener { _, year, month, day ->
            val selectedDate = "$year-${month + 1}-$day"
            binding.tvDateView.text = selectedDate
            selectDay = day
            binding.spSelectYear.setSelection(yearsList.indexOf(year.toString()))
            binding.spSelectMonth.setSelection(month)
            Log.e("CalendarView", "Selected date: $selectedDate")
        }


    }

}