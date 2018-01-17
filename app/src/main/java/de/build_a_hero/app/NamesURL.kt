package de.build_a_hero.app

import java.io.BufferedReader
import java.io.IOException
import java.io.StringReader
import java.util.ArrayList
import java.util.Collections
import java.util.concurrent.ExecutionException

/**
 * Created by ninad on 15.01.2018.
 */

class NamesURL {

    internal var myURL = "http://www.berlin.de/daten/liste-der-vornamen-2016/mitte.csv"
    internal var getRequest = HttpGetRequest()
    internal var result: String? = null
    private var female: MutableList<String>? = null
    private var male: MutableList<String>? = null
    private var allNames: MutableList<String>? = null

    @Throws(ExecutionException::class)
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

            if (result != null) {
                reader = StringReader(result!!)

            } else {
                reader = StringReader("")
            }

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

        Collections.sort(female!!)
        Collections.sort(male!!)
        allNames!!.addAll(female!!)
        allNames!!.addAll(male!!)
        Collections.sort(allNames!!)
    }


    fun getFemale(): List<String>? {
        return female
    }

    fun setFemale(female: MutableList<String>) {
        this.female = female
    }

    fun getMale(): List<String>? {
        return male
    }

    fun setMale(male: MutableList<String>) {
        this.male = male
    }

    fun getAllNames(): List<String>? {
        return allNames
    }

    fun setAllNames(allNames: MutableList<String>) {
        this.allNames = allNames
    }
}
