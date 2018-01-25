package de.build_a_hero.app

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

class CSVFile(private val inputStream: InputStream) {
    private var female: MutableList<String>? = null
    private var male: MutableList<String>? = null
    private var allNames: MutableList<String>? = null

    private val caseFemale = "w"
    private val caseMale = "m"
    private val separator = ";"

    fun read() {
        val reader = BufferedReader(InputStreamReader(inputStream))
        female = ArrayList()
        male = ArrayList()
        allNames = ArrayList()

        try {
            var csvLine: String

            loop@ while (true) {

                csvLine = reader.readLine() ?: break

                val row = csvLine.split(separator.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (row[2].equals(caseFemale, ignoreCase = true)) {
                    female!!.add(row[0])
                } else if (row[2].equals(caseMale, ignoreCase = true)) {
                    male!!.add(row[0])
                }
            }
            allNames!!.addAll(female!!)
            allNames!!.addAll(male!!)
            allNames!!.sort()
            female!!.sort()
            male!!.sort()

        } catch (ex: IOException) {
            throw RuntimeException("Error in reading CSV file: " + ex)
        } finally {
            try {
                inputStream.close()
            } catch (e: IOException) {
                throw RuntimeException("Error while closing input stream: " + e)
            }
        }
    }

    fun getFemale(): List<String>? {
        return female
    }

    fun getMale(): List<String>? {
        return male
    }

    fun getAllNames(): List<String>? {
        return allNames
    }
}