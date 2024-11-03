import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.map.hung.practice3.R
import com.map.hung.practice3.dao.CourseDAO
import java.text.SimpleDateFormat
import java.util.*

class TimKiemFragment : Fragment() {

    private lateinit var radioGroup: RadioGroup
    private lateinit var tvTotal: TextView
    private lateinit var tableLayoutStatistics: TableLayout
    private lateinit var courseDAO: CourseDAO

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_tim_kiem, container, false)

        // Initialize views
        radioGroup = view.findViewById(R.id.radioGroup)
        tvTotal = view.findViewById(R.id.tvTotal)
        tableLayoutStatistics = view.findViewById(R.id.tableLayoutStatistics)

        courseDAO = CourseDAO(requireContext())

        // Set up RadioGroup listener
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val isActive = when (checkedId) {
                R.id.rbActivated -> true
                R.id.rbNotActivated -> false
                else -> null
            }

            isActive?.let {
                val courses = courseDAO.searchByStatus(it)
                tvTotal.text = "Total Courses: ${courses.size}"
            }
        }

        // Load statistics
        loadStatistics()

        return view
    }

    private fun loadStatistics() {
        // Get current year
        val currentYear = SimpleDateFormat("yyyy", Locale.getDefault()).format(Date())

        // Fetch statistics from the database
        val statistics = courseDAO.getMonthlyStatistics(currentYear)

        // Clear any existing rows (except the header)
        val childCount = tableLayoutStatistics.childCount
        if (childCount > 1) {
            tableLayoutStatistics.removeViews(1, childCount - 1)
        }

        // Populate the table with statistics
        for ((month, total) in statistics) {
            val tableRow = TableRow(requireContext())

            val tvMonth = TextView(requireContext()).apply {
                text = month.toInt().toString() // Convert month to integer to remove leading zeros
                setPadding(8, 8, 8, 8)
            }

            val tvTotal = TextView(requireContext()).apply {
                text = total.toString()
                setPadding(8, 8, 8, 8)
            }

            tableRow.addView(tvMonth)
            tableRow.addView(tvTotal)

            tableLayoutStatistics.addView(tableRow)
        }
    }
}
