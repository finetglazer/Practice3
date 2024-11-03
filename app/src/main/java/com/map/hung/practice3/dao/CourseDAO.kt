package com.map.hung.practice3.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.map.hung.practice3.dao.DbHelper
import com.map.hung.practice3.model.Course


class CourseDAO(context: Context) {

    private val dbHelper: DbHelper = DbHelper(context)

    // Function to insert a new course
    fun insert(name: String, startDate: String, major: String, isActive: Boolean, fee: Int) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put("name", name)
            put("start_date", startDate)
            put("major", major)
            put("is_active", if (isActive) 1 else 0)
            put("fee", fee)
        }

        db.insert("course", null, values)
    }

    // Function to search courses by activation status
    fun searchByStatus(isActive: Boolean): List<Course> {
        val db = dbHelper.readableDatabase
        val status = if (isActive) 1 else 0

        val query = """
            SELECT id, name, start_date, major, is_active, fee
            FROM course
            WHERE is_active = ?
        """
        val cursor: Cursor = db.rawQuery(query, arrayOf(status.toString()))

        val courses = mutableListOf<Course>()
        while (cursor.moveToNext()) {
            val course = Course(
                id = cursor.getInt(0),
                name = cursor.getString(1),
                startDate = cursor.getString(2),
                major = cursor.getString(3),
                isActive = cursor.getInt(4) == 1,
                fee = cursor.getInt(5)
            )
            courses.add(course)
        }
        cursor.close()
        return courses
    }

    // Function to search a course by name
    fun searchByName(name: String): Course? {
        val db = dbHelper.readableDatabase

        val query = """
            SELECT id, name, start_date, major, is_active, fee
            FROM course
            WHERE name = ?
        """
        val cursor: Cursor = db.rawQuery(query, arrayOf(name))

        if (cursor.moveToNext()) {
            val course = Course(
                id = cursor.getInt(0),
                name = cursor.getString(1),
                startDate = cursor.getString(2),
                major = cursor.getString(3),
                isActive = cursor.getInt(4) == 1,
                fee = cursor.getInt(5)
            )
            cursor.close()
            return course
        }
        cursor.close()
        return null
    }

    fun getMonthlyStatistics(currentYear: String): List<Pair<String, Int>> {
        val db = dbHelper.readableDatabase

        val query = """
        SELECT strftime('%m', start_date) AS month, COUNT(*) AS total
        FROM course
        WHERE strftime('%Y', start_date) = ?
        GROUP BY month
        ORDER BY month ASC
    """
        val cursor: Cursor = db.rawQuery(query, arrayOf(currentYear))

        val statistics = mutableListOf<Pair<String, Int>>()
        while (cursor.moveToNext()) {
            val month = cursor.getString(0)
            val total = cursor.getInt(1)
            statistics.add(Pair(month, total))
        }
        cursor.close()
        return statistics
    }


    // Function to delete a course by ID
    fun deleteCourse(courseId: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete("course", "id = ?", arrayOf(courseId.toString()))
    }

    // get ALL courses
    fun getAllCourses(): List<Course> {
        val db = dbHelper.readableDatabase

        val query = """
            SELECT id, name, start_date, major, is_active, fee
            FROM course
        """
        val cursor: Cursor = db.rawQuery(query, null)

        val courses = mutableListOf<Course>()
        while (cursor.moveToNext()) {
            val course = Course(
                id = cursor.getInt(0),
                name = cursor.getString(1),
                startDate = cursor.getString(2),
                major = cursor.getString(3),
                isActive = cursor.getInt(4) == 1,
                fee = cursor.getInt(5)
            )
            courses.add(course)
        }
        cursor.close()
        return courses
    }
    // Function to update an existing course
    fun updateCourse(course: Course): Int {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put("name", course.name)
            put("start_date", course.startDate)
            put("major", course.major)
            put("is_active", if (course.isActive) 1 else 0)
            put("fee", course.fee)
        }

        return db.update("course", values, "id = ?", arrayOf(course.id.toString()))
    }

}
