// InformationFragment.kt
package com.map.hung.practice3.fragment

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.map.hung.practice3.R

class InformationFragment : Fragment() {

    private lateinit var ivStrainImage: ImageView
    private lateinit var tvStrainName: TextView
    private lateinit var tvStrainInfo: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_information, container, false)

        // Initialize views
        ivStrainImage = view.findViewById(R.id.ivStrainImage)
        tvStrainName = view.findViewById(R.id.tvStrainName)
        tvStrainInfo = view.findViewById(R.id.tvStrainInfo)

        // Set data
        ivStrainImage.setImageResource(R.drawable.baseline_coronavirus_24) // Replace with your image resource
        tvStrainName.text = "SARS-CoV-2"
        tvStrainInfo.text = "Severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2) is the virus that causes COVID-19."

        return view
    }
}
