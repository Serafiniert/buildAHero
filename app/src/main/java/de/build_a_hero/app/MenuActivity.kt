package de.build_a_hero.app

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException

class MenuActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val charList = findViewById<View>(R.id.charListView) as ListView

        val arrayList = getAllChars()


        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_single_choice, arrayList)

        charList.setAdapter(adapter)


        configureSettingsButton()
        configureAddButton()
        configureTestButton()
    }

    override fun onStart() {

        super.onStart()
        val charList = findViewById<View>(R.id.charListView) as ListView

        val arrayList = getAllChars()


        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_single_choice, arrayList)

        charList.setAdapter(adapter)


    }

    fun getAllChars(): ArrayList<String> {

        val loading = CharCreationActivity()

        val charNames = ArrayList<String>()

        val file = File("/data/user/0/com.example.ninad.buildahero/files", "charDetails2.txt")

        if (file.exists()) {


            val loadedText = load("charDetails2.txt")

            val allCharDetails = loadedText.split("ÜÄÖ")
            var count = 0

            for (char in allCharDetails) {
                if (!char.equals(allCharDetails[allCharDetails.size - 1])) {

                    Log.v("allchardetails: ", char)

                    val singleCharDetails = char.split(";")
                    Log.v("singlechardetails: ", singleCharDetails[1])

                    charNames.add(singleCharDetails[1])

                }

            }

        } else {

            Log.v("FILEEXISTS?", "file doesnt exist")

        }


        return charNames
    }

    private fun configureSettingsButton() {

        val settingsButton = findViewById<Button>(R.id.settingsButton)
        settingsButton.setOnClickListener { startActivity(Intent(this@MenuActivity, SettingsActivity::class.java)) }
    }

    private fun configureTestButton() {

        val testButton = findViewById<Button>(R.id.testButton)
        testButton.setOnClickListener { startActivity(Intent(this@MenuActivity, TestActivity::class.java)) }
    }

    private fun configureAddButton() {

        val addButton = findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener { startActivity(Intent(this@MenuActivity, CharCreationActivity::class.java)) }
    }

    fun load(filename: String): String {
        var text = ""
        val fis: FileInputStream
        try {
            fis = openFileInput(filename)
            val dataArray = ByteArray(fis.available())
            while (fis.read(dataArray) !== -1) {
                text = String(dataArray)
            }
            fis.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            //Log.v("IOException caught: ", e.getMessage())
            //System.err.println("IOException caught: " + e.getMessage())
            e.printStackTrace()
        }
        return text
    }
}
