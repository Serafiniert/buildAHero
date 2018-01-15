package de.build_a_hero.app

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException

class MenuActivity : AppCompatActivity() {

    val filename = "charDetails2.txt"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val charList = findViewById<View>(R.id.charListView) as ListView

        val arrayList = getAllChars()


        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_single_choice, arrayList)

        charList.setAdapter(adapter)

        charList.setOnItemClickListener(object: AdapterView.OnItemClickListener {

            override fun onItemClick(parent:AdapterView<*>, view:View, position: Int, id: Long){
                val loadedText = load(filename)
                val allCharDetails = loadedText.split("ÜÄÖ")
                val singleCharDetails = allCharDetails[position]
                Log.v("onclick charDetails: ", singleCharDetails)

                val intent = Intent(this@MenuActivity, CharEditActivity::class.java).apply {
                    putExtra("charDetail", singleCharDetails)
                }

                startActivity(intent)

            }


        })


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

        val charNames = ArrayList<String>()

        val file = File("/data/user/0/com.example.ninad.buildahero/files", filename)

        if (file.exists()) {

                val loadedText = load(filename)

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
