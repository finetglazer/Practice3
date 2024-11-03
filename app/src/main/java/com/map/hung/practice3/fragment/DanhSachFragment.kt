package com.map.hung.practice3.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.map.hung.practice3.R
import com.map.hung.practice3.activity.AddCourseActivity
import com.map.hung.practice3.adapter.CourseAdapter
import com.map.hung.practice3.dao.CourseDAO
import com.map.hung.practice3.model.Course

class DanhSachFragment : Fragment() {

    private lateinit var courseDAO: CourseDAO
    private lateinit var courseAdapter: CourseAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_danh_sach, container, false)

        // Initialize DAO
        courseDAO = CourseDAO(requireContext())

        // Set up RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewCourses)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize adapter with empty list
        courseAdapter = CourseAdapter(emptyList()) { course ->
            showOptionsDialog(course)
        }
        recyclerView.adapter = courseAdapter

        // Load courses and set adapter
        loadCourses()

        return view
    }

    private fun loadCourses() {
        val courses = courseDAO.getAllCourses()
        Log.d("DanhSachFragment", "Number of courses retrieved: ${courses.size}")
        if (courses.isNotEmpty()) {
            courseAdapter.updateCourses(courses)
        } else {
            courseAdapter.updateCourses(emptyList())
        }
    }


    override fun onResume() {
        super.onResume()
        loadCourses() // Refresh the list when fragment resumes
    }

    private fun showOptionsDialog(course: Course) {
        // Display options to delete or update
        val options = arrayOf("Delete", "Edit")
        AlertDialog.Builder(requireContext())
            .setTitle(course.name)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> deleteCourse(course)
                    1 -> editCourse(course)
                }
            }
            .show()
    }

    private fun deleteCourse(course: Course) {
        val deletedRows = courseDAO.deleteCourse(course.id!!)
        if (deletedRows > 0) {
            Toast.makeText(requireContext(), "Course deleted", Toast.LENGTH_SHORT).show()
            loadCourses() // Refresh the list
        } else {
            Toast.makeText(requireContext(), "Failed to delete course", Toast.LENGTH_SHORT).show()
        }
    }


    private fun editCourse(course: Course) {
        val intent = Intent(requireContext(), AddCourseActivity::class.java)
        intent.putExtra("course", course)
        startActivity(intent)
    }

}
