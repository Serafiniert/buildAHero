package de.build_a_hero.app

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import java.io.*
import java.util.*
import java.util.concurrent.ExecutionException

class CharCreationActivity : AppCompatActivity() {
    private var nameAdapter: ArrayAdapter<String>? = null
    //Layout: whole table
    //Header: most top row
    //Wert: total percentage of trait class
    //total points available that you can spend on traits
    private var availablePoints: TextView? = null

    private var handelnLayout: TableLayout? = null
    private var handelnHeader: TableRow? = null
    private var handelnWert: TextView? = null

    private var wissenLayout: TableLayout? = null
    private var wissenHeader: TableRow? = null
    private var wissenWert: TextView? = null

    private var interagLayout: TableLayout? = null
    private var interagHeader: TableRow? = null
    private var interagWert: TextView? = null

    private var charDetails: String? = ""
    private val loadText: String? = null
    private var nameSpinner: Spinner? = null
    private var genderSpinner: Spinner? = null
    private var formList: ArrayList<View>? = null
    private var spinnerPositions: ArrayList<Int>? = null

    private val gender = arrayOf("", "weiblich", "männlich", "anderes", "unbestimmt")
    private var male: List<String>? = null
    private var female: List<String>? = null
    private var allNames: List<String>? = null

    private val filename = "charDetails7.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_char_creation)


        handelnLayout = findViewById(R.id.tableHandeln)
        handelnHeader = handelnLayout!!.getChildAt(0) as TableRow
        handelnWert = handelnHeader!!.getChildAt(1) as TextView


        wissenLayout = findViewById(R.id.tableWissen)
        wissenHeader = wissenLayout!!.getChildAt(0) as TableRow
        wissenWert = wissenHeader!!.getChildAt(1) as TextView


        interagLayout = findViewById(R.id.tableInterag)
        interagHeader = interagLayout!!.getChildAt(0) as TableRow
        interagWert = interagHeader!!.getChildAt(1) as TextView


        availablePoints = findViewById(R.id.availPointsNum)

        nameSpinner = findViewById(R.id.nameSpinner)
        genderSpinner = findViewById(R.id.gender)

        formList = ArrayList()
        spinnerPositions = ArrayList()

        allNames = ArrayList()

        val namesUrl = NamesURL()

        try {
            namesUrl.read()
            male = namesUrl.male
            female = namesUrl.female
            allNames = namesUrl.allNames
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }

        val genderAdapter = ArrayAdapter(this@CharCreationActivity, android.R.layout.simple_spinner_item, gender)
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner!!.adapter = genderAdapter
        genderSpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val gender = parent.getItemAtPosition(position).toString()

                if (gender == "weiblich") {
                    nameAdapter = ArrayAdapter(this@CharCreationActivity, android.R.layout.simple_spinner_item, female!!)
                    nameAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    nameSpinner!!.adapter = nameAdapter
                } else if (gender == "männlich") {
                    nameAdapter = ArrayAdapter(this@CharCreationActivity, android.R.layout.simple_spinner_item, male!!)
                    nameAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    nameSpinner!!.adapter = nameAdapter
                } else if (gender == "anders" || gender == "unbestimmt") {
                    nameAdapter = ArrayAdapter(this@CharCreationActivity, android.R.layout.simple_spinner_item, allNames!!)
                    nameAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    nameSpinner!!.adapter = nameAdapter
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


        configureCancelButton()
        // configures + and -, so it adds to or substracts 10 of the current Value
        configureValueButton()
        // configures the button "Fertig" to save the character
        configureSaveButton()
        // configures Load Button, not necessary here
        //configureLoadButton();


    }


    private fun configureCancelButton() {

        val cancelButton = findViewById<Button>(R.id.cancelCreation)
        cancelButton.setOnClickListener { finish() }
    }

    private fun configureValueButton() {

        //final Button addButton = findViewById(R.id.plusButton);
        for (i in 1..5) {
            val handelnRow = handelnLayout!!.getChildAt(i) as TableRow
            val handelnAddButton = handelnRow.getChildAt(3) as Button
            val handelnDecreaseButton = handelnRow.getChildAt(1) as Button

            handelnAddButton.setOnClickListener {
                val availPoints = Integer.parseInt(availablePoints!!.text.toString())

                if (availPoints == 0) {
                    availablePoints!!.setTextColor(Color.parseColor("#ffcc0000"))

                } else {

                    //TableRow row = (TableRow) addButton.getParent();
                    val cell = handelnRow.getChildAt(2) as TextView
                    var currentHandeln = Integer.parseInt(cell.text.toString())
                    currentHandeln += 10
                    var klassenWert = Integer.parseInt(handelnWert!!.text.toString())
                    if (currentHandeln == 80) {
                        klassenWert += 10
                    }

                    klassenWert = klassenWert + 1

                    availablePoints!!.text = Integer.toString(availPoints - 10)
                    cell.text = Integer.toString(currentHandeln)
                    handelnWert!!.text = Integer.toString(klassenWert)
                }
            }

            handelnDecreaseButton.setOnClickListener {
                availablePoints!!.setTextColor(Color.parseColor("#000000"))

                val cell = handelnRow.getChildAt(2) as TextView
                var currentHandeln = Integer.parseInt(cell.text.toString())
                currentHandeln -= 10
                var klassenWert = Integer.parseInt(handelnWert!!.text.toString())

                if (currentHandeln == 70) {
                    klassenWert -= 10
                }

                klassenWert -= 1

                val availPoints = Integer.parseInt(availablePoints!!.text.toString())

                availablePoints!!.text = Integer.toString(availPoints + 10)
                cell.text = Integer.toString(currentHandeln)
                handelnWert!!.text = Integer.toString(klassenWert)
            }

            val wissenRow = wissenLayout!!.getChildAt(i) as TableRow
            val wissenAddButton = wissenRow.getChildAt(3) as Button
            val wissenDecreaseButton = wissenRow.getChildAt(1) as Button

            wissenAddButton.setOnClickListener {
                val availPoints = Integer.parseInt(availablePoints!!.text.toString())

                if (availPoints == 0) {
                    availablePoints!!.setTextColor(Color.parseColor("#ffcc0000"))

                } else {

                    val cell = wissenRow.getChildAt(2) as TextView
                    var currentWissen = Integer.parseInt(cell.text.toString())
                    currentWissen = currentWissen + 10
                    var klassenWert = Integer.parseInt(wissenWert!!.text.toString())
                    if (currentWissen == 80) {
                        klassenWert = klassenWert + 10
                    }

                    klassenWert = klassenWert + 1


                    availablePoints!!.text = Integer.toString(availPoints - 10)
                    cell.text = Integer.toString(currentWissen)
                    wissenWert!!.text = Integer.toString(klassenWert)
                }
            }

            wissenDecreaseButton.setOnClickListener {
                availablePoints!!.setTextColor(Color.parseColor("#000000"))

                val cell = wissenRow.getChildAt(2) as TextView
                var currentWissen = Integer.parseInt(cell.text.toString())
                currentWissen = currentWissen - 10
                var klassenWert = Integer.parseInt(wissenWert!!.text.toString())

                if (currentWissen == 70) {
                    klassenWert = klassenWert - 10
                }

                klassenWert = klassenWert - 1

                val availPoints = Integer.parseInt(availablePoints!!.text.toString())

                availablePoints!!.text = Integer.toString(availPoints + 10)
                cell.text = Integer.toString(currentWissen)
                wissenWert!!.text = Integer.toString(klassenWert)
            }


            val interagRow = interagLayout!!.getChildAt(i) as TableRow
            val interagAddButton = interagRow.getChildAt(3) as Button
            val interagDecreaseButton = interagRow.getChildAt(1) as Button

            interagAddButton.setOnClickListener {
                val availPoints = Integer.parseInt(availablePoints!!.text.toString())

                if (availPoints == 0) {
                    availablePoints!!.setTextColor(Color.parseColor("#ffcc0000"))

                } else {

                    val cell = interagRow.getChildAt(2) as TextView
                    var currentInterag = Integer.parseInt(cell.text.toString())
                    currentInterag = currentInterag + 10
                    var klassenWert = Integer.parseInt(interagWert!!.text.toString())
                    if (currentInterag == 80) {
                        klassenWert = klassenWert + 10
                    }

                    klassenWert = klassenWert + 1

                    availablePoints!!.text = Integer.toString(availPoints - 10)
                    cell.text = Integer.toString(currentInterag)
                    interagWert!!.text = Integer.toString(klassenWert)
                }
            }

            interagDecreaseButton.setOnClickListener {
                availablePoints!!.setTextColor(Color.parseColor("#000000"))

                val cell = interagRow.getChildAt(2) as TextView
                var currentInterag = Integer.parseInt(cell.text.toString())
                currentInterag = currentInterag - 10
                var klassenWert = Integer.parseInt(interagWert!!.text.toString())

                if (currentInterag == 70) {
                    klassenWert = klassenWert - 10
                }

                klassenWert = klassenWert - 1

                val availPoints = Integer.parseInt(availablePoints!!.text.toString())

                availablePoints!!.text = Integer.toString(availPoints + 10)
                cell.text = Integer.toString(currentInterag)
                interagWert!!.text = Integer.toString(klassenWert)
            }
        }
    }

    private fun configureForms() {

        //formList

        val compLayout = findViewById<ConstraintLayout>(R.id.compLayout)

        for (i in 0 until compLayout.childCount) {
            Log.v(tag, Integer.toString(compLayout.childCount))
            val child = compLayout.getChildAt(i)

            if (child is Spinner) {
                formList!!.add(child)

            } else if (child is EditText) {
                formList!!.add(child)

            } else if (child is TableLayout) {
                for (j in 0 until child.childCount) {
                    val tr = child.getChildAt(j) as TableRow

                    if (j == 0) {
                        formList!!.add(tr.getChildAt(1))
                    } else {
                        formList!!.add(tr.getChildAt(0))

                        formList!!.add(tr.getChildAt(2))
                    }
                }
            }
        }
        formList!!.add(findViewById(R.id.availPointsNum))

        return
    }

    private fun configureSaveButton() {

        val saveButton = findViewById<Button>(R.id.finishCreation)
        saveButton.setOnClickListener {
            //Log.i(tag, text);

            val file = File(filename)

            if (file.exists()) {

                charDetails = load(filename)

            } else {

                charDetails = ""

            }

            configureForms()

            for (i in formList!!.indices) {

                val input = formList!![i]

                if (input is Spinner) {
                    if (input.selectedItem != null) {
                        charDetails = charDetails + input.selectedItem.toString() + ";"
                        spinnerPositions!!.add(input.selectedItemPosition)
                    } else {
                        charDetails = charDetails!! + "null;"
                    }

                } else if (input is EditText) {
                    if (input.text == null || input.text.equals("")) {
                        charDetails = charDetails!! + "null;"
                    } else {
                        charDetails = charDetails + input.text + ";"
                    }
                } else if (input is TextView) {
                    if (input.text == null || input.text == "") {
                        charDetails = charDetails!! + "null;"
                    } else {
                        charDetails = charDetails + input.text + ";"
                    }
                }
            }

            if (charDetails!!.length > 0 && charDetails!![charDetails!!.length - 1] == ';') {
                charDetails = charDetails!!.substring(0, charDetails!!.length - 1)
            }

            charDetails = charDetails!! + "ÜÄÖ"

            save(filename, charDetails)
            finish()
            Log.i(tag, filesDir.path.toString())


            //EditText temp = (EditText) formList.get(1);
            //temp.setText(loadTxt);
        }
    }

    private fun configureLoadButton() {

        val loadButton = findViewById<Button>(R.id.cancelCreation)
        loadButton.setOnClickListener {
            val loadTxt = load(filename)


            val inputs = load("androidsavetexttest.txt")!!.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            var spinnerCount = 0

            for (i in formList!!.indices) {

                val v = formList!![i]

                if (v is Spinner) {
                    if (spinnerPositions!!.size != 0) {
                        v.setSelection(spinnerPositions!![spinnerCount])
                        spinnerCount++
                    }
                } else if (v is EditText) {
                    v.setText(inputs[i])
                } else if (v is TextView) {
                    v.text = inputs[i]
                }


            }
        }
    }


    private fun save(filename: String, text: String?) {

        val fos: FileOutputStream

        try {
            fos = openFileOutput(filename, Context.MODE_APPEND)
            fos.write(text!!.toByteArray())
            fos.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun load(filename: String): String? {

        var text: String? = null
        val fis: FileInputStream

        try {
            fis = openFileInput(filename)
            val dataArray = ByteArray(fis.available())

            while (fis.read(dataArray) != -1) {
                text = String(dataArray)
            }
            fis.close()

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            Log.v("IOException caught: ", e.message)
            System.err.println("IOException caught: " + e.message)
            e.printStackTrace()
        }
        return text
    }

    companion object {

        private val tag = "Text"
    }
}
