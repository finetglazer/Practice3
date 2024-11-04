package com.map.hung.practice3.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.map.hung.practice3.model.CovidStrain

class CovidStrainDAO(context: Context) {

    private val dbHelper: DbHelper = DbHelper(context)

    // Function to insert a new Covid strain
    fun insert(covidStrain: CovidStrain): Long {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put("virusName", covidStrain.virusName)
            put("structure", covidStrain.structure?.joinToString(","))
            put("appearanceDate", covidStrain.appearanceDate)
            put("vaccineStatus", if (covidStrain.vaccineStatus) 1 else 0)
            put("worldInfectionCount", covidStrain.worldInfectionCount)
            put("vietnamInfectionCount", covidStrain.vietnamInfectionCount)
        }

        return db.insert("covid_strain", null, values)
    }

    // Function to get all Covid strains
    fun getAllCovidStrains(): List<CovidStrain> {
        val db = dbHelper.readableDatabase

        val query = """
            SELECT id, virusName, structure, appearanceDate, vaccineStatus, worldInfectionCount, vietnamInfectionCount
            FROM covid_strain
        """
        val cursor: Cursor = db.rawQuery(query, null)

        val strains = mutableListOf<CovidStrain>()
        while (cursor.moveToNext()) {
            val strain = CovidStrain(
                id = cursor.getInt(0),
                virusName = cursor.getString(1),
                structure = cursor.getString(2)?.split(","),
                appearanceDate = cursor.getString(3),
                vaccineStatus = cursor.getInt(4) == 1,
                worldInfectionCount = cursor.getInt(5),
                vietnamInfectionCount = cursor.getInt(6)
            )
            strains.add(strain)
        }
        cursor.close()
        return strains
    }

    // Function to update a Covid strain
    fun updateCovidStrain(covidStrain: CovidStrain): Int {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put("virusName", covidStrain.virusName)
            put("structure", covidStrain.structure?.joinToString(","))
            put("appearanceDate", covidStrain.appearanceDate)
            put("vaccineStatus", if (covidStrain.vaccineStatus) 1 else 0)
            put("worldInfectionCount", covidStrain.worldInfectionCount)
            put("vietnamInfectionCount", covidStrain.vietnamInfectionCount)
        }

        return db.update("covid_strain", values, "id = ?", arrayOf(covidStrain.id.toString()))
    }

    // Function to delete a Covid strain
    fun deleteCovidStrain(id: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete("covid_strain", "id = ?", arrayOf(id.toString()))
    }

    // Implement search and statistics methods as per your requirements

    // Add this method to CovidStrainDAO
// Update this method in CovidStrainDAO

    fun getStatisticsByStructure(startDate: String, endDate: String, selectedRegion: String): Map<String, Int> {
        val db = dbHelper.readableDatabase

        val column = when (selectedRegion) {
            "vietnam" -> "vietnamInfectionCount"
            "world" -> "worldInfectionCount"
            else -> "0"
        }

        val query = """
        SELECT structure, SUM($column) as total
        FROM covid_strain
        WHERE appearanceDate BETWEEN ? AND ?
        GROUP BY structure
    """

        val cursor: Cursor = db.rawQuery(query, arrayOf(startDate, endDate))

        val statistics = mutableMapOf<String, Int>()
        while (cursor.moveToNext()) {
            val structure = cursor.getString(0)
            val total = cursor.getInt(1)
            statistics[structure] = total
        }
        cursor.close()
        return statistics
    }

}
