package com.map.hung.practice3.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.map.hung.practice3.R
import com.map.hung.practice3.dao.CovidStrainDAO
import com.map.hung.practice3.model.CovidStrain
import java.util.*

class AddCovidStrainActivity : AppCompatActivity() {

    private lateinit var etVirusName: EditText
    private lateinit var cbStructureARN: CheckBox
    private lateinit var cbStructureProteinS: CheckBox
    private lateinit var cbStructureProteinN: CheckBox
    private lateinit var etAppearanceDate: EditText
    private lateinit var cbVaccineStatus: CheckBox
    private lateinit var etWorldInfectionCount: EditText
    private lateinit var etVietnamInfectionCount: EditText
    private lateinit var btnSaveCovidStrain: Button

    private lateinit var covidStrainDAO: CovidStrainDAO
    private var covidStrainToEdit: CovidStrain? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_covid_strain)

        // Initialize DAO
        covidStrainDAO = CovidStrainDAO(this)

        // Initialize views
        etVirusName = findViewById(R.id.etVirusName)
        cbStructureARN = findViewById(R.id.cbStructureARN)
        cbStructureProteinS = findViewById(R.id.cbStructureProteinS)
        cbStructureProteinN = findViewById(R.id.cbStructureProteinN)
        etAppearanceDate = findViewById(R.id.etAppearanceDate)
        cbVaccineStatus = findViewById(R.id.cbVaccineStatus)
        etWorldInfectionCount = findViewById(R.id.etWorldInfectionCount)
        etVietnamInfectionCount = findViewById(R.id.etVietnamInfectionCount)
        btnSaveCovidStrain = findViewById(R.id.btnSaveCovidStrain)

        // Set up DatePickerDialog for appearance date
        etAppearanceDate.setOnClickListener {
            showDatePickerDialog()
        }

        // Check if we're editing an existing Covid strain
        covidStrainToEdit = intent.getParcelableExtra("covidStrain")
        if (covidStrainToEdit != null) {
            populateFieldsForEditing()
        }

        // Set up save button
        btnSaveCovidStrain.setOnClickListener {
            saveCovidStrain()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year: Int
        val month: Int
        val day: Int

        if (covidStrainToEdit != null && covidStrainToEdit?.appearanceDate != null) {
            val parts = covidStrainToEdit?.appearanceDate?.split("-")
            if (parts != null && parts.size == 3) {
                year = parts[0].toInt()
                month = parts[1].toInt() - 1
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
                val formattedDate = String.format(
                    "%04d-%02d-%02d",
                    selectedYear,
                    selectedMonth + 1,
                    selectedDay
                )
                etAppearanceDate.setText(formattedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun populateFieldsForEditing() {
        etVirusName.setText(covidStrainToEdit?.virusName)

        // Set structures
        covidStrainToEdit?.structure?.let {
            cbStructureARN.isChecked = it.contains("ARN")
            cbStructureProteinS.isChecked = it.contains("Protein-S")
            cbStructureProteinN.isChecked = it.contains("Protein-N")
        }

        etAppearanceDate.setText(covidStrainToEdit?.appearanceDate)
        cbVaccineStatus.isChecked = covidStrainToEdit?.vaccineStatus ?: false
        etWorldInfectionCount.setText(covidStrainToEdit?.worldInfectionCount?.toString())
        etVietnamInfectionCount.setText(covidStrainToEdit?.vietnamInfectionCount?.toString())

        btnSaveCovidStrain.text = "Update"
    }

    private fun saveCovidStrain() {
        val virusName = etVirusName.text.toString()
        val structures = mutableListOf<String>()
        if (cbStructureARN.isChecked) structures.add("ARN")
        if (cbStructureProteinS.isChecked) structures.add("Protein-S")
        if (cbStructureProteinN.isChecked) structures.add("Protein-N")

        val appearanceDate = etAppearanceDate.text.toString()
        val vaccineStatus = cbVaccineStatus.isChecked
        val worldInfectionCount = etWorldInfectionCount.text.toString().toIntOrNull()
        val vietnamInfectionCount = etVietnamInfectionCount.text.toString().toIntOrNull()

        if (virusName.isEmpty() || appearanceDate.isEmpty() || worldInfectionCount == null || vietnamInfectionCount == null) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (covidStrainToEdit == null) {
            // Adding a new strain
            val newStrain = CovidStrain(
                virusName = virusName,
                structure = structures,
                appearanceDate = appearanceDate,
                vaccineStatus = vaccineStatus,
                worldInfectionCount = worldInfectionCount,
                vietnamInfectionCount = vietnamInfectionCount
            )
            val result = covidStrainDAO.insert(newStrain)
            if (result != -1L) {
                Toast.makeText(this, "Covid strain added successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to add Covid strain", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Updating existing strain
            covidStrainToEdit?.apply {
                this.virusName = virusName
                this.structure = structures
                this.appearanceDate = appearanceDate
                this.vaccineStatus = vaccineStatus
                this.worldInfectionCount = worldInfectionCount
                this.vietnamInfectionCount = vietnamInfectionCount
            }
            val result = covidStrainDAO.updateCovidStrain(covidStrainToEdit!!)
            if (result > 0) {
                Toast.makeText(this, "Covid strain updated successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to update Covid strain", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
