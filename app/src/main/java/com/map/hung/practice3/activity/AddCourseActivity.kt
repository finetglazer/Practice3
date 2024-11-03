package com.map.hung.practice3.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.map.hung.practice3.R
import com.map.hung.practice3.dao.CourseDAO
import com.map.hung.practice3.model.Course
import java.util.Calendar

class AddCourseActivity : AppCompatActivity() {

    private lateinit var etCourseName: EditText
    private lateinit var etStartDate: EditText
    private lateinit var spinnerMajor: Spinner
    private lateinit var cbIsActive: CheckBox
    private lateinit var etFee: EditText
    private lateinit var btnSaveCourse: Button
    private lateinit var courseDAO: CourseDAO

    private var courseToEdit: Course? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        courseDAO = CourseDAO(this)

        // Initialize views
        etCourseName = findViewById(R.id.etCourseName)
        etStartDate = findViewById(R.id.etStartDate)
        spinnerMajor = findViewById(R.id.spinnerMajor)
        cbIsActive = findViewById(R.id.cbIsActive)
        etFee = findViewById(R.id.etFee)
        btnSaveCourse = findViewById(R.id.btnSaveCourse)

        // Set up spinner
        val majors = arrayOf("CNTT", "Kế toán", "Thiết kế đồ hoạ", "Quản trị kinh doanh")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, majors)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMajor.adapter = adapter

        // Set up save button
        btnSaveCourse.setOnClickListener {
            saveCourse()
        }

        // Check if we're editing an existing course
        courseToEdit = intent.getParcelableExtra("course")

        etStartDate.setOnClickListener {
            showDatePickerDialog()
        }

        if (courseToEdit != null) {
            // We're editing, populate the fields
            populateFieldsForEditing()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year: Int
        val month: Int
        val day: Int

        if (courseToEdit != null) {
            // If editing, use the existing date
            val parts = courseToEdit?.startDate?.split("-")
            if (parts != null && parts.size == 3) {
                year = parts[0].toInt()
                month = parts[1].toInt() - 1 // Months are 0-based
                day = parts[2].toInt()
            } else {
                year = calendar.get(Calendar.YEAR)
                month = calendar.get(Calendar.MONTH)
                day = calendar.get(Calendar.DAY_OF_MONTH)
            }
        } else {
            year = calendar.get(Calendar.YEAR)
            month = calendar.get(Calendar.MONTH)
            day = calendar.get(Calendar.DAY_OF_MONTH)
        }

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Format selected date as YYYY-MM-DD
                val formattedDate = String.format(
                    "%04d-%02d-%02d",
                    selectedYear,
                    selectedMonth + 1,
                    selectedDay
                )
                etStartDate.setText(formattedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun populateFieldsForEditing() {
        etCourseName.setText(courseToEdit?.name)
        etStartDate.setText(courseToEdit?.startDate)
        spinnerMajor.setSelection(getMajorIndex(courseToEdit?.major))
        cbIsActive.isChecked = courseToEdit?.isActive ?: false
        etFee.setText(courseToEdit?.fee?.toString())
        btnSaveCourse.text = "Update" // Change button text to 'Update'
    }

    private fun getMajorIndex(major: String?): Int {
        val majors = arrayOf("CNTT", "Kế toán", "Thiết kế đồ hoạ", "Quản trị kinh doanh")
        return majors.indexOf(major ?: "CNTT")
    }


    private fun saveCourse() {
        val name = etCourseName.text.toString()
        val startDate = etStartDate.text.toString()
        val major = spinnerMajor.selectedItem.toString()
        val isActive = cbIsActive.isChecked
        val fee = etFee.text.toString().toIntOrNull()

        if (name.isEmpty() || startDate.isEmpty() || fee == null) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (courseToEdit == null) {
            // Adding a new course
            courseDAO.insert(name, startDate, major, isActive, fee)
            Toast.makeText(this, "Course added successfully", Toast.LENGTH_SHORT).show()
        } else {
            // Updating an existing course
            courseToEdit?.apply {
                this.name = name
                this.startDate = startDate
                this.major = major
                this.isActive = isActive
                this.fee = fee
            }
            courseDAO.updateCourse(courseToEdit!!)
            Toast.makeText(this, "Course updated successfully", Toast.LENGTH_SHORT).show()
        }

        finish() // Close activity after saving
    }

}
