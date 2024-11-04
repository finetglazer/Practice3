// CovidStrainAdapter.kt
package com.map.hung.practice3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.recyclerview.widget.RecyclerView
import com.map.hung.practice3.R
import com.map.hung.practice3.model.CovidStrain

class CovidStrainAdapter(
    private var strains: List<CovidStrain>,
    private val onStrainClick: (CovidStrain) -> Unit
) : RecyclerView.Adapter<CovidStrainAdapter.CovidStrainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CovidStrainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_covid_strain, parent, false)
        return CovidStrainViewHolder(view)
    }

    override fun onBindViewHolder(holder: CovidStrainViewHolder, position: Int) {
        holder.bind(strains[position])
    }

    override fun getItemCount() = strains.size

    fun updateStrains(newStrains: List<CovidStrain>) {
        strains = newStrains
        notifyDataSetChanged()
    }

    inner class CovidStrainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvVirusName: TextView = itemView.findViewById(R.id.tvVirusName)
        private val tvStructure: TextView = itemView.findViewById(R.id.tvStructure)
        private val tvAppearanceDate: TextView = itemView.findViewById(R.id.tvAppearanceDate)
        private val tvVaccineStatus: TextView = itemView.findViewById(R.id.tvVaccineStatus)
        private val tvWorldInfectionCount: TextView = itemView.findViewById(R.id.tvWorldInfectionCount)
        private val tvVietnamInfectionCount: TextView = itemView.findViewById(R.id.tvVietnamInfectionCount)

        fun bind(strain: CovidStrain) {
            tvVirusName.text = strain.virusName
            tvStructure.text = "Structure: ${strain.structure?.joinToString(", ")}"
            tvAppearanceDate.text = "Appearance Date: ${strain.appearanceDate}"
            tvVaccineStatus.text = "Vaccine Available: ${if (strain.vaccineStatus) "Yes" else "No"}"
            tvWorldInfectionCount.text = "World Infection Count: ${strain.worldInfectionCount}"
            tvVietnamInfectionCount.text = "Vietnam Infection Count: ${strain.vietnamInfectionCount}"

            itemView.setOnClickListener {
                onStrainClick(strain)
            }
        }
    }
}
