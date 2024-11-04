// SearchStatsFragment.kt
package com.map.hung.practice3.fragment

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.map.hung.practice3.R
import com.map.hung.practice3.dao.CovidStrainDAO

class SearchStatsFragment : Fragment() {

    private lateinit var etStartDate: EditText
    private lateinit var etEndDate: EditText
    private lateinit var btnSearch: Button
    private lateinit var rgRegion: RadioGroup
    private lateinit var rbVietnam: RadioButton
    private lateinit var rbWorld: RadioButton
    private lateinit var tableLayoutStatistics: TableLayout
    private lateinit var covidStrainDAO: CovidStrainDAO

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_search_stats, container, false)

        // Initialize DAO
        covidStrainDAO = CovidStrainDAO(requireContext())

        // Initialize views
        etStartDate = view.findViewById(R.id.etStartDate)
        etEndDate = view.findViewById(R.id.etEndDate)
        btnSearch = view.findViewById(R.id.btnSearch)
        rgRegion = view.findViewById(R.id.rgRegion)
        rbVietnam = view.findViewById(R.id.rbVietnam)
        rbWorld = view.findViewById(R.id.rbWorld)
        tableLayoutStatistics = view.findViewById(R.id.tableLayoutStatistics)

        // Set up DatePickerDialogs
        etStartDate.setOnClickListener { showDatePickerDialog(etStartDate) }
        etEndDate.setOnClickListener { showDatePickerDialog(etEndDate) }

        // Set up search button
        btnSearch.setOnClickListener {
            performSearchAndDisplayStatistics()
        }

        return view
    }

    private fun showDatePickerDialog(editText: EditText) {
        val calendar = java.util.Calendar.getInstance()
        val year = calendar.get(java.util.Calendar.YEAR)
        val month = calendar.get(java.util.Calendar.MONTH)
        val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)

        val datePickerDialog = android.app.DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format(
                    "%04d-%02d-%02d",
                    selectedYear,
                    selectedMonth + 1,
                    selectedDay
                )
                editText.setText(formattedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun performSearchAndDisplayStatistics() {
        val startDate = etStartDate.text.toString()
        val endDate = etEndDate.text.toString()

        if (startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(requireContext(), "Please select both start and end dates", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedRegion = when (rgRegion.checkedRadioButtonId) {
            R.id.rbVietnam -> "vietnam"
            R.id.rbWorld -> "world"
            else -> {
                Toast.makeText(requireContext(), "Please select a region", Toast.LENGTH_SHORT).show()
                return
            }
        }

        // Fetch data from the database
        val statistics = covidStrainDAO.getStatisticsByStructure(startDate, endDate, selectedRegion)

        // Clear existing rows
        val childCount = tableLayoutStatistics.childCount
        if (childCount > 1) {
            tableLayoutStatistics.removeViews(1, childCount - 1)
        }

        // Populate the table with statistics
        for ((structure, count) in statistics) {
            val tableRow = TableRow(requireContext())

            val tvStructure = TextView(requireContext()).apply {
                text = structure
                setPadding(8, 8, 8, 8)
            }

            val tvCount = TextView(requireContext()).apply {
                text = count.toString()
                setPadding(8, 8, 8, 8)
            }

            tableRow.addView(tvStructure)
            tableRow.addView(tvCount)

            tableLayoutStatistics.addView(tableRow)
        }
    }
}
