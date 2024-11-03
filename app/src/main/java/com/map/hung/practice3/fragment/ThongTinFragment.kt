package com.map.hung.practice3.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.map.hung.practice3.R
import android.widget.ImageView
import android.widget.TextView

class ThongTinFragment : Fragment() {

    private lateinit var majorImage: ImageView
    private lateinit var majorName: TextView
    private lateinit var majorDescription: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_thong_tin, container, false)

        // Initialize views
        majorImage = view.findViewById(R.id.majorImage)
        majorName = view.findViewById(R.id.majorName)
        majorDescription = view.findViewById(R.id.majorDescription)

        // Set data (optional if already set in XML)
        // If you want to set or update data dynamically, you can do so here

        // Example:
        // majorImage.setImageResource(R.drawable.your_major_image)
        // majorName.text = "Computer Science"
        // majorDescription.text = "Detailed description about Computer Science..."

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set text content
        majorName.text = "Computer Science"
        majorDescription.text = """
        Computer Science is the study of computers and computational systems. 
        It involves theory, design, development, and application of software and software systems.
    """.trimIndent()
    }

}
