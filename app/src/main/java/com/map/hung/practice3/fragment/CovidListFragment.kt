package com.map.hung.practice3.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.map.hung.practice3.R
import com.map.hung.practice3.activity.AddCovidStrainActivity
import com.map.hung.practice3.adapter.CovidStrainAdapter
import com.map.hung.practice3.dao.CovidStrainDAO
import com.map.hung.practice3.model.CovidStrain

class CovidListFragment : Fragment() {

    private lateinit var covidStrainDAO: CovidStrainDAO
    private lateinit var covidStrainAdapter: CovidStrainAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_covid_list, container, false)

        // Initialize DAO
        covidStrainDAO = CovidStrainDAO(requireContext())

        // Set up RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewCovidStrains)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize adapter with empty list
        covidStrainAdapter = CovidStrainAdapter(emptyList()) { strain ->
            showOptionsDialog(strain)
        }
        recyclerView.adapter = covidStrainAdapter

        loadCovidStrains()

        return view
    }

    private fun loadCovidStrains() {
        val strains = covidStrainDAO.getAllCovidStrains()
        covidStrainAdapter.updateStrains(strains)
    }

    override fun onResume() {
        super.onResume()
        loadCovidStrains()
    }

    private fun showOptionsDialog(strain: CovidStrain) {
        val options = arrayOf("Edit", "Delete")
        AlertDialog.Builder(requireContext())
            .setTitle(strain.virusName)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> editCovidStrain(strain)
                    1 -> deleteCovidStrain(strain)
                }
            }
            .show()
    }

    private fun editCovidStrain(strain: CovidStrain) {
        val intent = Intent(requireContext(), AddCovidStrainActivity::class.java)
        intent.putExtra("covidStrain", strain)
        startActivity(intent)
    }

    private fun deleteCovidStrain(strain: CovidStrain) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Covid Strain")
            .setMessage("Are you sure you want to delete ${strain.virusName}?")
            .setPositiveButton("Yes") { _, _ ->
                val deletedRows = covidStrainDAO.deleteCovidStrain(strain.id!!)
                if (deletedRows > 0) {
                    Toast.makeText(requireContext(), "Covid strain deleted", Toast.LENGTH_SHORT).show()
                    loadCovidStrains()
                } else {
                    Toast.makeText(requireContext(), "Failed to delete Covid strain", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("No", null)
            .show()
    }
}
