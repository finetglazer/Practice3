package com.map.hung.practice3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.map.hung.practice3.R
import com.map.hung.practice3.model.Course

class CourseAdapter(
    private var courses: List<Course>,
    private val onCourseClick: (Course) -> Unit
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(courses[position])
    }

    override fun getItemCount() = courses.size

    fun updateCourses(newCourses: List<Course>) {
        courses = newCourses
        notifyDataSetChanged()
    }

    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val courseName: TextView = itemView.findViewById(R.id.courseName)
        private val courseMajor: TextView = itemView.findViewById(R.id.courseMajor)
        private val courseStartDate: TextView = itemView.findViewById(R.id.courseStartDate)
        private val courseFee: TextView = itemView.findViewById(R.id.courseFee)
        private val courseIsActive: TextView = itemView.findViewById(R.id.courseIsActive)

        fun bind(course: Course) {
            courseName.text = "Name: ${course.name}"
            courseMajor.text = "Major: ${course.major}"
            courseStartDate.text = "Start Date: ${course.startDate}"
            courseFee.text = "Fee: ${course.fee}"
            courseIsActive.text = "Is Active: ${if (course.isActive) "Yes" else "No"}"

            itemView.setOnClickListener {
                onCourseClick(course)
            }
        }
    }
}
