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

class CharEditActivity : AppCompatActivity() {
    private val tag = "Text"
    //Layout: whole table
    //Header: most top row
    //Wert: total percentage of trait class
    //total points available that you can spend on traits
    private lateinit var availablePoints: TextView

    private lateinit var handelnLayout: TableLayout
    private lateinit var handelnHeader: TableRow
    private lateinit var handelnWert: TextView

    private lateinit var wissenLayout: TableLayout
    private lateinit var wissenHeader: TableRow
    private lateinit var wissenWert: TextView

    private lateinit var interagLayout: TableLayout
    private lateinit var interagHeader: TableRow
    private lateinit var interagWert: TextView

    private lateinit var charDetails: String
    private val loadText: String? = null
    private var nameSpinner: Spinner? = null
    private var genderSpinner: Spinner? = null
    private var formList: ArrayList<View> = ArrayList()
    private var spinnerPositions: ArrayList<Int> = ArrayList()

    private val gender = arrayOf("", "weiblich", "männlich", "anderes", "unbestimmt")
    private var male: List<String>? = null
    private var female: List<String>? = null
    private var allNames: List<String>? = null

    private val filename = "charDetails7.txt"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_char_edit)

        handelnLayout = findViewById(R.id.tableHandeln)
        handelnHeader = handelnLayout.getChildAt(0) as TableRow
        handelnWert = handelnHeader.getChildAt(1) as TextView


        wissenLayout = findViewById(R.id.tableWissen)
        wissenHeader = wissenLayout.getChildAt(0) as TableRow
        wissenWert = wissenHeader.getChildAt(1) as TextView


        interagLayout = findViewById(R.id.tableInterag)
        interagHeader = interagLayout.getChildAt(0) as TableRow
        interagWert = interagHeader.getChildAt(1) as TextView


        availablePoints = findViewById(R.id.availPointsNum)

        genderSpinner = findViewById(R.id.gender)
        nameSpinner = findViewById(R.id.name)



        getAllForms()
        setCharDetails()


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


        val genderAdapter = ArrayAdapter(this@CharEditActivity, android.R.layout.simple_spinner_item, gender)
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner!!.adapter = genderAdapter
        genderSpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val gender = parent.getItemAtPosition(position).toString()
                var nameAdapter: ArrayAdapter<String>? = null
                if (gender == "weiblich") {
                    nameAdapter = ArrayAdapter(this@CharEditActivity, android.R.layout.simple_spinner_item, female!!)
                    nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    nameSpinner!!.adapter = nameAdapter
                } else if (gender == "männlich") {
                    nameAdapter = ArrayAdapter(this@CharEditActivity, android.R.layout.simple_spinner_item, male!!)
                    nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    nameSpinner!!.adapter = nameAdapter
                } else if (gender == "anders" || gender == "unbestimmt") {
                    nameAdapter = ArrayAdapter(this@CharEditActivity, android.R.layout.simple_spinner_item, allNames!!)
                    nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    nameSpinner!!.adapter = nameAdapter
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }



        configureCancelButton()
        configureSaveButton()
        configureValueButton()
    }

    private fun configureCancelButton() {

        val cancelButton = findViewById<Button>(R.id.cancelCreation)
        cancelButton.setOnClickListener { finish() }
    }

    private fun configureSaveButton() {

        val saveButton = findViewById<Button>(R.id.finishCreation)
        saveButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                charDetails = ""

                val file = File("/data/user/0/com.example.ninad.buildahero/files", filename)

                if (file.exists()) {
                    val p = intent.getStringExtra("charDetail")
                    val c = p.split(";")

                    for (i in formList.indices) {

                        val input = formList[i]

                        if (input is Spinner) {
                            if (input.selectedItem != null) {
                                charDetails = charDetails + input.selectedItem.toString() + ";"
                            } else {
                                //charDetails = charDetails + "null;"
                                charDetails = charDetails + c[i] + ";"
                            }

                        } else if (input is EditText) {
                            if (input.text == null) {
                                charDetails = charDetails + "null;"
                            } else {
                                charDetails = charDetails + input.text + ";"
                            }
                        } else if (input is TextView) {
                            if (input.text == null || input.text == "") {
                                charDetails = charDetails + "null;"
                            } else {
                                charDetails = charDetails + input.text + ";"
                            }
                        }
                    }

                    if (charDetails.length > 0 && charDetails.get(charDetails.length - 1) == ';') {
                        charDetails = charDetails.substring(0, charDetails.length - 1)
                    }


                    //charDetails = charDetails + "ÜÄÖ"

                    //CHARDETAILS NOW NEW INPUT


                    val loadedText = load(filename)

                    var allCharDetails: ArrayList<String> = ArrayList(loadedText.split("ÜÄÖ"))
                    Log.v("allCharDetails", "as ArrayList before changing" + allCharDetails)
                    Log.v("allCharDetails", "as ArrayList.toString() before changing" + allCharDetails.toString())


                    val position = intent.getStringExtra("position").toInt()
                    Log.v("position", "when saving" + position)

                    allCharDetails.set(position, charDetails)
                    Log.v("allCharDetails", "as ArrayList after changing" + allCharDetails)
                    Log.v("allCharDetails", "as ArrayList.toString() after changing" + allCharDetails.toString())

                    var allCharacterString = ""

                    for (i in 0 until allCharDetails.size - 1) {

                        allCharDetails.set(i, allCharDetails[i] + "ÜÄÖ")
                        allCharacterString = allCharacterString + allCharDetails[i]
                    }


                    allCharDetails.toString()
                    Log.v("allCharacterString", "endresult: " + allCharacterString)

                    save(filename, allCharacterString)
                    finish()

                } else {

                    Log.v("FILEEXISTS?", "file doesnt exist")

                }

            }
        }
        )

    }


    //configures "+" and "-" buttons to add or substract 10 off the current value in that row
    private fun configureValueButton() {

        for (i in 1..5) {
            val handelnRow = handelnLayout.getChildAt(i) as TableRow
            val handelnAddButton = handelnRow.getChildAt(3) as Button
            val handelnDecreaseButton = handelnRow.getChildAt(1) as Button

            handelnAddButton.setOnClickListener {
                val availPoints = Integer.parseInt(availablePoints.getText().toString())

                if (availPoints == 0) {
                    availablePoints.setTextColor(Color.parseColor("#ffcc0000"))

                } else {

                    //TableRow row = (TableRow) addButton.getParent();
                    val cell = handelnRow.getChildAt(2) as TextView
                    var currentHandeln = Integer.parseInt(cell.text.toString())
                    currentHandeln = currentHandeln + 10
                    var klassenWert = Integer.parseInt(handelnWert.getText().toString())
                    if (currentHandeln == 80) {
                        klassenWert = klassenWert + 10
                    }

                    klassenWert = klassenWert + 1

                    availablePoints.setText(Integer.toString(availPoints - 10))
                    cell.text = Integer.toString(currentHandeln)
                    handelnWert.setText(Integer.toString(klassenWert))
                }
            }

            handelnDecreaseButton.setOnClickListener {
                availablePoints.setTextColor(Color.parseColor("#000000"))

                val cell = handelnRow.getChildAt(2) as TextView
                var currentHandeln = Integer.parseInt(cell.text.toString())
                currentHandeln = currentHandeln - 10
                var klassenWert = Integer.parseInt(handelnWert.getText().toString())

                if (currentHandeln == 70) {
                    klassenWert = klassenWert - 10
                }

                klassenWert = klassenWert - 1

                val availPoints = Integer.parseInt(availablePoints.getText().toString())

                availablePoints.setText(Integer.toString(availPoints + 10))
                cell.text = Integer.toString(currentHandeln)
                handelnWert.setText(Integer.toString(klassenWert))
            }

            val wissenRow = wissenLayout.getChildAt(i) as TableRow
            val wissenAddButton = wissenRow.getChildAt(3) as Button
            val wissenDecreaseButton = wissenRow.getChildAt(1) as Button

            wissenAddButton.setOnClickListener {
                val availPoints = Integer.parseInt(availablePoints.getText().toString())

                if (availPoints == 0) {
                    availablePoints.setTextColor(Color.parseColor("#ffcc0000"))

                } else {

                    val cell = wissenRow.getChildAt(2) as TextView
                    var currentWissen = Integer.parseInt(cell.text.toString())
                    currentWissen = currentWissen + 10
                    var klassenWert = Integer.parseInt(wissenWert.getText().toString())
                    if (currentWissen == 80) {
                        klassenWert = klassenWert + 10
                    }

                    klassenWert = klassenWert + 1


                    availablePoints.setText(Integer.toString(availPoints - 10))
                    cell.text = Integer.toString(currentWissen)
                    wissenWert.setText(Integer.toString(klassenWert))
                }
            }

            wissenDecreaseButton.setOnClickListener {
                availablePoints.setTextColor(Color.parseColor("#000000"))

                val cell = wissenRow.getChildAt(2) as TextView
                var currentWissen = Integer.parseInt(cell.text.toString())
                currentWissen = currentWissen - 10
                var klassenWert = Integer.parseInt(wissenWert.getText().toString())

                if (currentWissen == 70) {
                    klassenWert = klassenWert - 10
                }

                klassenWert = klassenWert - 1

                val availPoints = Integer.parseInt(availablePoints.getText().toString())

                availablePoints.setText(Integer.toString(availPoints + 10))
                cell.text = Integer.toString(currentWissen)
                wissenWert.setText(Integer.toString(klassenWert))
            }


            val interagRow = interagLayout.getChildAt(i) as TableRow
            val interagAddButton = interagRow.getChildAt(3) as Button
            val interagDecreaseButton = interagRow.getChildAt(1) as Button

            interagAddButton.setOnClickListener {
                val availPoints = Integer.parseInt(availablePoints.getText().toString())

                if (availPoints == 0) {
                    availablePoints.setTextColor(Color.parseColor("#ffcc0000"))

                } else {

                    val cell = interagRow.getChildAt(2) as TextView
                    var currentInterag = Integer.parseInt(cell.text.toString())
                    currentInterag = currentInterag + 10
                    var klassenWert = Integer.parseInt(interagWert.getText().toString())
                    if (currentInterag == 80) {
                        klassenWert = klassenWert + 10
                    }

                    klassenWert = klassenWert + 1

                    availablePoints.setText(Integer.toString(availPoints - 10))
                    cell.text = Integer.toString(currentInterag)
                    interagWert.setText(Integer.toString(klassenWert))
                }
            }

            interagDecreaseButton.setOnClickListener {
                availablePoints.setTextColor(Color.parseColor("#000000"))

                val cell = interagRow.getChildAt(2) as TextView
                var currentInterag = Integer.parseInt(cell.text.toString())
                currentInterag = currentInterag - 10
                var klassenWert = Integer.parseInt(interagWert.getText().toString())

                if (currentInterag == 70) {
                    klassenWert = klassenWert - 10
                }

                klassenWert = klassenWert - 1

                val availPoints = Integer.parseInt(availablePoints.getText().toString())

                availablePoints.setText(Integer.toString(availPoints + 10))
                cell.text = Integer.toString(currentInterag)
                interagWert.setText(Integer.toString(klassenWert))
            }
        }
    }


    //sets the saved data upon loading
    fun setCharDetails() {

        val s = intent.getStringExtra("charDetail")
        val character = s.split(";")

        for (i in 0 until formList.size) {
            val v = formList.get(i)
            if (v is Spinner) {
                var count = 0;
                val sp = v as Spinner
                Log.v("spinnersize", sp.count.toString())
                for (j in 0 until sp.count) {

                    if (sp.getItemAtPosition(i).toString().equals(character[i])) {
                        count = j
                        break
                    }
                }
                sp.setSelection(count)
            } else if (v is EditText) {
                val et = v as EditText
                et.setText(character[i])
            } else if (v is TextView) {
                val tv = v as TextView
                tv.setText(character[i])
            }
        }


    }

    //saves all Fields with valuable input for us (name, age, gender, traits with their respective
    //skillpoints etc.)
    fun getAllForms() {
        val compLayout = findViewById<ConstraintLayout>(R.id.compLayout)
        for (i in 0 until compLayout.getChildCount()) {
            Log.v(tag, Integer.toString(compLayout.getChildCount()))
            val child = compLayout.getChildAt(i)
            if (child is Spinner) {
                val spinner = child as Spinner
                formList.add(spinner)
            } else if (child is EditText) {
                formList.add(child as EditText)
            } else if (child is TableLayout) {
                val lyo = child as TableLayout
                for (j in 0 until lyo.getChildCount()) {
                    val tr = (lyo.getChildAt(j)) as TableRow
                    if (j == 0) {
                        formList.add(tr.getChildAt(1))
                    } else {
                        formList.add(tr.getChildAt(0))
                        formList.add(tr.getChildAt(2))
                    }
                }
            }
        }
        formList.add(findViewById(R.id.availPointsNum))

    }

    fun save(filename: String, text: String) {

        val fos: FileOutputStream

        try {
            fos = openFileOutput(filename, Context.MODE_PRIVATE)
            fos.write(text.toByteArray())
            fos.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

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

