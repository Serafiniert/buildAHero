package de.build_a_hero.app

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
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
    private val nameOptional = "(Optional) Name wählen"

    //total points available that you can spend on traits
    private lateinit var availablePoints: TextView

    //Layout: whole table
    //Header: most top row
    //Wert: total percentage of trait class
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
    private lateinit var nameSpinner: Spinner
    private var genderSpinner: Spinner? = null
    private var formList: ArrayList<View> = ArrayList()
    private var nameField: EditText? = null

    private val gender = arrayOf("Geschlecht wählen", "weiblich", "männlich", "anderes", "unbestimmt")
    private var male = ArrayList<String>()
    private var female = ArrayList<String>()
    private var allNames = ArrayList<String>()

    private val filename = "charDetails11.txt"

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

        genderSpinner = findViewById(R.id.genderSpinner)
        nameSpinner = findViewById(R.id.nameSpinner)
        nameField = findViewById(R.id.name)

        allNames = ArrayList()

        try {
            if (isNetworkAvailable()) {
                val namesUrl = NamesURL()
                namesUrl.read()

                male = ArrayList(namesUrl.getMale())
                female = ArrayList(namesUrl.getFemale())
                allNames = ArrayList(namesUrl.getAllNames())
            } else {
                val inputStream = resources.openRawResource(R.raw.mitte)
                val csv = CSVFile(inputStream)

                csv.read()
                male = ArrayList(csv.getMale())
                female = ArrayList(csv.getFemale())
                allNames = ArrayList(csv.getAllNames())
            }

            male[0] = nameOptional
            female[0] = nameOptional
            allNames[0] = nameOptional

        } catch (e: ExecutionException) {
            e.printStackTrace()
        }

        val genderAdapter = ArrayAdapter(this@CharEditActivity, android.R.layout.simple_spinner_item, gender)
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner!!.adapter = genderAdapter
        genderSpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val gender = parent.getItemAtPosition(position).toString()
                val nameAdapter: ArrayAdapter<String>?
                if (gender == "weiblich") {
                    nameAdapter = ArrayAdapter(this@CharEditActivity, android.R.layout.simple_spinner_item, female)
                    nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    nameSpinner.adapter = nameAdapter
                } else if (gender == "männlich") {
                    nameAdapter = ArrayAdapter(this@CharEditActivity, android.R.layout.simple_spinner_item, male)
                    nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    nameSpinner.adapter = nameAdapter
                } else if (gender == "anders" || gender == "unbestimmt" || gender == "Geschlecht wählen") {
                    nameAdapter = ArrayAdapter(this@CharEditActivity, android.R.layout.simple_spinner_item, allNames)
                    nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    nameSpinner.adapter = nameAdapter
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        nameSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                if (position > 0) {
                    val name = parent.getItemAtPosition(position).toString()
                    nameField!!.setText(name)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        getAllForms()
        setCharDetails()

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
        saveButton.setOnClickListener {
            charDetails = ""

            val file = File("/data/user/0/com.example.ninad.buildahero/files", filename)

            if (file.exists()) {
                val p = intent.getStringExtra("charDetail")
                val c = p.split(";")

                for (i in formList.indices) {

                    val input = formList[i]

                    if (input is Spinner) {
                        charDetails = if (input.selectedItem == null) {
                            charDetails + c[i] + ";"

                        } else {
                            //charDetails = charDetails + "null;"
                            charDetails + input.selectedItem.toString() + ";"
                        }
                    } else if (input is EditText) {
                        if (input.text == null) {
                            charDetails += "null;"
                        } else {
                            charDetails = charDetails + input.text + ";"
                        }
                    } else if (input is TextView) {
                        if (input.text == null || input.text == "") {
                            charDetails += "null;"
                        } else {
                            charDetails = charDetails + input.text + ";"
                        }
                    }
                }

                if (charDetails.isNotEmpty() && charDetails[charDetails.length - 1] == ';') {
                    charDetails = charDetails.substring(0, charDetails.length - 1)
                }

                //charDetails = charDetails + "ÜÄÖ"

                //CHARDETAILS NOW NEW INPUT

                val loadedText = load(filename)

                val allCharDetails: ArrayList<String> = ArrayList(loadedText.split("ÜÄÖ"))
                Log.v("allCharDetails", "as ArrayList before changing" + allCharDetails)
                Log.v("allCharDetails", "as ArrayList.toString() before changing" + allCharDetails.toString())


                val position = intent.getStringExtra("position").toInt()
                Log.v("position", "when saving" + position)

                allCharDetails[position] = charDetails
                Log.v("allCharDetails", "as ArrayList after changing" + allCharDetails)
                Log.v("allCharDetails", "as ArrayList.toString() after changing" + allCharDetails.toString())

                var allCharacterString = ""

                for (i in 0 until allCharDetails.size - 1) {

                    allCharDetails[i] = allCharDetails[i] + "ÜÄÖ"
                    allCharacterString += allCharDetails[i]
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


    //configures "+" and "-" buttons to add or substract 10 off the current value in that row
    @SuppressLint("SetTextI18n")
    private fun configureValueButton() {

        for (i in 1..5) {
            val handelnRow = handelnLayout.getChildAt(i) as TableRow
            val handelnAddButton = handelnRow.getChildAt(3) as Button
            val handelnDecreaseButton = handelnRow.getChildAt(1) as Button

            handelnAddButton.setOnClickListener {
                val availPoints = Integer.parseInt(availablePoints.text.toString())

                if (availPoints == 0) {
                    availablePoints.setTextColor(Color.parseColor("#ffcc0000"))

                } else {

                    //TableRow row = (TableRow) addButton.getParent();
                    val cell = handelnRow.getChildAt(2) as TextView
                    var currentHandeln = Integer.parseInt(cell.text.toString())
                    currentHandeln += 10
                    var klassenWert = Integer.parseInt(handelnWert.text.toString())
                    if (currentHandeln == 80) {
                        klassenWert += 10
                    }

                    klassenWert += 1

                    availablePoints.text = Integer.toString(availPoints - 10)
                    cell.text = Integer.toString(currentHandeln)
                    handelnWert.text = Integer.toString(klassenWert)
                }
            }

            handelnDecreaseButton.setOnClickListener {
                availablePoints.setTextColor(Color.parseColor("#000000"))

                val cell = handelnRow.getChildAt(2) as TextView
                var currentHandeln = Integer.parseInt(cell.text.toString())
                currentHandeln -= 10
                var klassenWert = Integer.parseInt(handelnWert.text.toString())

                if (currentHandeln == 70) {
                    klassenWert -= 10
                }

                klassenWert -= 1

                val availPoints = Integer.parseInt(availablePoints.text.toString())

                availablePoints.text = Integer.toString(availPoints + 10)
                cell.text = Integer.toString(currentHandeln)
                handelnWert.text = Integer.toString(klassenWert)
            }

            val wissenRow = wissenLayout.getChildAt(i) as TableRow
            val wissenAddButton = wissenRow.getChildAt(3) as Button
            val wissenDecreaseButton = wissenRow.getChildAt(1) as Button

            wissenAddButton.setOnClickListener {
                val availPoints = Integer.parseInt(availablePoints.text.toString())

                if (availPoints == 0) {
                    availablePoints.setTextColor(Color.parseColor("#ffcc0000"))

                } else {

                    val cell = wissenRow.getChildAt(2) as TextView
                    var currentWissen = Integer.parseInt(cell.text.toString())
                    currentWissen += 10
                    var klassenWert = Integer.parseInt(wissenWert.text.toString())
                    if (currentWissen == 80) {
                        klassenWert += 10
                    }

                    klassenWert += 1

                    availablePoints.text = Integer.toString(availPoints - 10)
                    cell.text = Integer.toString(currentWissen)
                    wissenWert.text = Integer.toString(klassenWert)
                }
            }

            wissenDecreaseButton.setOnClickListener {
                availablePoints.setTextColor(Color.parseColor("#000000"))

                val cell = wissenRow.getChildAt(2) as TextView
                var currentWissen = Integer.parseInt(cell.text.toString())
                currentWissen -= 10
                var klassenWert = Integer.parseInt(wissenWert.text.toString())

                if (currentWissen == 70) {
                    klassenWert -= 10
                }

                klassenWert -= 1

                val availPoints = Integer.parseInt(availablePoints.text.toString())

                availablePoints.text = Integer.toString(availPoints + 10)
                cell.text = Integer.toString(currentWissen)
                wissenWert.text = Integer.toString(klassenWert)
            }


            val interagRow = interagLayout.getChildAt(i) as TableRow
            val interagAddButton = interagRow.getChildAt(3) as Button
            val interagDecreaseButton = interagRow.getChildAt(1) as Button

            interagAddButton.setOnClickListener {
                val availPoints = Integer.parseInt(availablePoints.text.toString())

                if (availPoints == 0) {
                    availablePoints.setTextColor(Color.parseColor("#ffcc0000"))

                } else {

                    val cell = interagRow.getChildAt(2) as TextView
                    var currentInterag = Integer.parseInt(cell.text.toString())
                    currentInterag += 10
                    var klassenWert = Integer.parseInt(interagWert.text.toString())
                    if (currentInterag == 80) {
                        klassenWert += 10
                    }

                    klassenWert += 1

                    availablePoints.text = Integer.toString(availPoints - 10)
                    cell.text = Integer.toString(currentInterag)
                    interagWert.text = Integer.toString(klassenWert)
                }
            }

            interagDecreaseButton.setOnClickListener {
                availablePoints.setTextColor(Color.parseColor("#000000"))

                val cell = interagRow.getChildAt(2) as TextView
                var currentInterag = Integer.parseInt(cell.text.toString())
                currentInterag -= 10
                var klassenWert = Integer.parseInt(interagWert.text.toString())

                if (currentInterag == 70) {
                    klassenWert -= 10
                }

                klassenWert -= 1

                val availPoints = Integer.parseInt(availablePoints.text.toString())

                availablePoints.text = Integer.toString(availPoints + 10)
                cell.text = Integer.toString(currentInterag)
                interagWert.text = Integer.toString(klassenWert)
            }
        }
    }


    //sets the saved data upon loading
    private fun setCharDetails() {

        val s = intent.getStringExtra("charDetail")
        val character = s.split(";")

        for (i in 0 until formList.size) {
            val v = formList[i]
            if (v is Spinner) {
                var count = 0
                Log.v("spinnersize", v.count.toString())
                for (j in 0 until v.count) {

                    if (v.getItemAtPosition(i).toString() == character[i]) {
                        count = j
                        break
                    }
                }
                v.setSelection(count)
            } else if (v is EditText) {
                v.setText(character[i])
            } else if (v is TextView) {
                v.text = character[i]
            }
        }
    }

    //saves all Fields with valuable input for us (name, age, gender, traits with their respective
    //skillpoints etc.)
    private fun getAllForms() {
        val compLayout = findViewById<ConstraintLayout>(R.id.compLayout)
        for (i in 0 until compLayout.childCount) {
            Log.v(tag, Integer.toString(compLayout.childCount))
            val child = compLayout.getChildAt(i)
            when (child) {
                is Spinner -> {
                    formList.add(child)
                }
                is EditText -> formList.add(child)
                is TableLayout -> {
                    for (j in 0 until child.childCount) {
                        val tr = (child.getChildAt(j)) as TableRow
                        if (j == 0) {
                            formList.add(tr.getChildAt(1))
                        } else {
                            formList.add(tr.getChildAt(0))
                            formList.add(tr.getChildAt(2))
                        }
                    }
                }
            }
        }
        formList.add(findViewById(R.id.availPointsNum))
    }


    private fun save(filename: String, text: String) {

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


    private fun load(filename: String): String {
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
            e.printStackTrace()
        }
        return text
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}