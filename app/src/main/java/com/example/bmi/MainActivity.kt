package com.example.bmi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*
import logic.Bmi
import logic.BmiForKgCm
import logic.BmiForLbIn
import logic.DataBmi
import java.lang.NumberFormatException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val RESULT_KEY = "Bmi result key"
        const val CATEGORY_KEY = "Bmi category key"
        const val SWITCHED_UNITS_KEY = "Switched units key"
        const val RESULTS_LIST_KEY = "Bmi results list key"
    }

    private val bmiKgCm = BmiForKgCm()
    private val bmiLbIn = BmiForLbIn()
    private var currentBmiCalc: Bmi? = null
    private var switchedUnitsForlbIn = false
    private lateinit var sharedPref: SharedPreference
    private var resultListEmpty = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chooseBmiCalculator(switchedUnitsForlbIn)
        sharedPref = SharedPreference(this)

        val recordsList = sharedPref.getValueList(RESULTS_LIST_KEY)
        if (recordsList.isEmpty()) {
            resultListEmpty = true
            invalidateOptionsMenu()
        }

    }

    //method saving state of activity (before rotation)
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(RESULT_KEY, resultBmiTV.text.toString())
        outState?.putString(CATEGORY_KEY, categoryTV.text.toString())
        outState?.putBoolean(SWITCHED_UNITS_KEY, switchedUnitsForlbIn)
    }

    //method restoring state of activity after rotation
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        if (savedInstanceState != null) {
            if (savedInstanceState.getString(RESULT_KEY) != "") {
                val savedBmiResult = savedInstanceState.getString(RESULT_KEY)!!
                updateResultView(chooseCategory(savedBmiResult.toDouble()), savedBmiResult)
            }
            switchedUnitsForlbIn = savedInstanceState.getBoolean(SWITCHED_UNITS_KEY)
            chooseBmiCalculator(switchedUnitsForlbIn)
            invalidateOptionsMenu()
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?) = consume {
        menu?.findItem(R.id.switchUnitsItem)?.isChecked = switchedUnitsForlbIn
        if (resultListEmpty) {
            menu!!.getItem(2).isEnabled = false
        }
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?) = consume {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.aboutMeItem -> consume { startActivity(Intent(this, AboutMeActivity::class.java)) }
        R.id.switchUnitsItem -> consume {
            this.switchedUnitsForlbIn = !switchedUnitsForlbIn
            item.isChecked = switchedUnitsForlbIn
            chooseBmiCalculator(switchedUnitsForlbIn)
            unitsChangedAction()
        }
        R.id.historyItem -> consume { startActivity(Intent(this, HistoryActivity::class.java)) }
        else -> super.onOptionsItemSelected(item)
    }

    //method called after clicking countBMI button
    fun onCountBmiClicked(view: View) {
        massET.error = null
        heightET.error = null

        if (checkUserInputs()) {
            val mass = massET.text.toString()  // catch text from textField
            val height = heightET.text.toString()
            val resultBmi = currentBmiCalc!!.countBMI(Integer.parseInt(mass), Integer.parseInt(height))
            val category = chooseCategory(resultBmi)

            val resultString = formatToStringWithTwoDecimalPlaces(resultBmi)
            updateResultView(category, resultString)
            updateResultsList(mass, height, resultString, category.getNameCategory(this.resources))
        }
    }

    //method called after clicking info button
    fun onInfoBmiClicked(view: View) {
        val infoIntent = Intent(this, InfoActivity::class.java)
        infoIntent.putExtra(RESULT_KEY, resultBmiTV.text.toString())
        infoIntent.putExtra(CATEGORY_KEY, categoryTV.text.toString())
        startActivity(infoIntent)
    }

    private fun updateResultsList(weight: String, height: String, result: String, category: String) {
        val currentDate = Calendar.getInstance().time
        val dateText = SimpleDateFormat("dd.MM.yyyy  HH:mm:ss", Locale.getDefault()).format(currentDate)
        val bmiRecord = DataBmi("${massTV.text} \t $weight", "${heightTV.text} \t $height", result, category, dateText)

        val recordsList = sharedPref.getValueList(RESULTS_LIST_KEY)
        if (recordsList.size < 10) {
            recordsList.add(bmiRecord)
            sharedPref.save(RESULTS_LIST_KEY, recordsList)
        } else {
            recordsList.removeAt(0)
            recordsList.add(bmiRecord)
            sharedPref.save(RESULTS_LIST_KEY, recordsList)
        }
    }

    private fun updateResultView(category: CategoriesBmi, result: String) {
        resultBmiTV.setTextColor(category.getColor(this.resources))
        categoryTV.setTextColor(category.getColor(this.resources))
        resultBmiTV.text = result
        categoryTV.text = category.getNameCategory(this.resources)
        infoBmiBUTTON.visibility = View.VISIBLE
    }

    private fun unitsChangedAction() {
        if (switchedUnitsForlbIn) {
            massTV.text = getString(R.string.main_tv_mass_lb)
            heightTV.text = getString(R.string.main_tv_height_in)
        } else {
            massTV.text = getString(R.string.main_tv_mass_kg)
            heightTV.text = getString(R.string.main_tv_height_cm)
        }
        massET.text.clear()
        heightET.text.clear()
        resultBmiTV.text = ""
        categoryTV.text = ""
        infoBmiBUTTON.visibility = View.INVISIBLE
    }

    private fun checkUserInputs(): Boolean {
        val validMass = validateInput(massET, currentBmiCalc!!.getMassRange())
        val validHeight = validateInput(heightET, currentBmiCalc!!.getHeightRange())
        return validMass && validHeight
    }

    private fun validateInput(editText: EditText, validRange: IntRange): Boolean {

        val input: String = editText.text.toString()
        if (input.isBlank()) {
            editText.error = getString(R.string.blank_input)
            return false
        }
        try {
            Integer.parseInt(input)
        } catch (exc: NumberFormatException) {
            editText.error = getString(R.string.invalid_input)
            return false
        }
        if (Integer.parseInt(input) !in validRange) {
            editText.error = getString(R.string.invalid_input)
            return false
        }
        return true
    }

    private fun chooseBmiCalculator(unitsSwitchedForlbIn: Boolean) {
        if (unitsSwitchedForlbIn) this.currentBmiCalc = bmiLbIn
        else this.currentBmiCalc = bmiKgCm
    }

    private fun chooseCategory(bmiValue: Double): CategoriesBmi {
        return when {
            bmiValue < 18.5 -> CategoriesBmi.UNDERWEIGHT
            bmiValue in 18.5..25.0 -> CategoriesBmi.NORMAL
            bmiValue in 25.0..30.0 -> CategoriesBmi.OVERWEIGHT
            bmiValue in 30.0..35.0 -> CategoriesBmi.OBESE
            else -> CategoriesBmi.EXTREMELY_OBESE
        }
    }

    private fun formatToStringWithTwoDecimalPlaces(number: Double): String {
        val df = DecimalFormat(
            "#.00",
            DecimalFormatSymbols.getInstance(Locale.US)
        )
        return df.format(number)
    }

    private inline fun consume(f: () -> Unit): Boolean {
        f()
        return true
    }
}
