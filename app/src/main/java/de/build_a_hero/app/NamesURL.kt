package de.build_a_hero.app

import java.io.BufferedReader
import java.io.IOException
import java.io.StringReader
import java.util.*

class NamesURL {

    private var myURL = "http://www.berlin.de/daten/liste-der-vornamen-2016/mitte.csv"
    private var getRequest = HttpGetRequest()
    private var result: String? = null
    private var female: MutableList<String>? = null
    private var male: MutableList<String>? = null
    private var allNames: MutableList<String>? = null

    fun read() {
        val separator = ";"
        val caseFemale = "w"
        val caseMale = "m"

        female = ArrayList()
        male = ArrayList()
        allNames = ArrayList()

        val reader: StringReader
        val br: BufferedReader

        try {
            result = getRequest.execute(myURL).get()

            reader = StringReader(result!!)

            br = BufferedReader(reader)
            var line: String

            while (true) {
                line = br.readLine() ?: break

                val row = line.split(separator.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (row[2].equals(caseFemale, ignoreCase = true)) {
                    female!!.add(row[0])
                } else if (row[2].equals(caseMale, ignoreCase = true)) {
                    male!!.add(row[0])
                }
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        female!!.sort()
        male!!.sort()
        allNames!!.addAll(female!!)
        allNames!!.addAll(male!!)
        allNames!!.sort()
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