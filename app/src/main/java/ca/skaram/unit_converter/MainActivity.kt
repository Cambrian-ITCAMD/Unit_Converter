// MID TERM - IOT1009
// Submitted by - Karam Singh

package ca.skaram.unit_converter

import android.R
import android.widget.*
import android.util.Log
import android.view.View
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import ca.skaram.unit_converter.databinding.ActivityMainBinding

// Define a MainActivity class which inherits from AppCompatActivity
class MainActivity : AppCompatActivity() {

    // Declare variables for binding and input/output views
    private lateinit var binding: ActivityMainBinding
    private lateinit var output: TextView
    private lateinit var input: EditText

    // Define conversion factors for each unit
    private val milesToKm : Double = 1.60934
    private val kmToM : Double = 1000.0
    private val mToCm : Double = 100.0
    private val cmToMm : Double = 10.0
    private val inToCm : Double = 2.54
    private val ftToIn : Double = 12.0
    private val ydToFt : Double = 3.0

    // The onCreate function is called when the activity is first created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout for the activity and set it as the content view
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create an ArrayAdapter with the unit options and set it as the adapter for both spinners
        val arrayAdapter = ArrayAdapter<String>(this, R.layout.simple_spinner_dropdown_item)
        arrayAdapter.add("Miles")
        arrayAdapter.add("Kilometer")
        arrayAdapter.add("Meter")
        arrayAdapter.add("Centimeter")
        arrayAdapter.add("Millimeter")
        arrayAdapter.add("Inches")
        arrayAdapter.add("Feet")
        arrayAdapter.add("Yard")

        val unitsSpinner1 = binding.unitsSpinner1
        val unitsSpinner2 = binding.unitsSpinner2

        unitsSpinner1.adapter = arrayAdapter
        unitsSpinner2.adapter = arrayAdapter

        // Set up the input and output views
        input = binding.inputValues
        output = binding.output

        // Set up the input listener to update the output when the input value changes
        input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updateOutput()
            }
        })

        // Set up the spinner listeners to update the output when the units are changed
        binding.unitsSpinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                updateOutput()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        binding.unitsSpinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                updateOutput()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun updateOutput() {
        // Get the input value and selected units
        val inputValue = input.text.toString().toDoubleOrNull() ?: 0.0
        val fromUnit = binding.unitsSpinner1.selectedItem.toString()
        val toUnit = binding.unitsSpinner2.selectedItem.toString()

        // Convert the units and update the output text view
        val outputValue = unitConversions(inputValue, fromUnit, toUnit)
        output.text = outputValue.toString()
        displayMessage(inputValue, fromUnit, toUnit)
    }

    // This function converts the input value from the "fromUnit" to the "toUnit" and returns the result
    private fun unitConversions(value: Double, fromUnit: String, toUnit: String): Double {

        // Convert the input value to meters
        val meters = when (fromUnit) {
            "Miles" -> value * milesToKm * kmToM
            "Kilometer" -> value * kmToM
            "Meter" -> value
            "Centimeter" -> value / mToCm
            "Millimeter" -> value / cmToMm / mToCm
            "Inches" -> value * inToCm / mToCm
            "Feet" -> value * ftToIn * inToCm / mToCm
            "Yard" -> value * ydToFt * ftToIn * inToCm / mToCm
            else -> 0.0
        }

        // Convert meters to the output unit
        return when (toUnit) {
            "Miles" -> meters / kmToM / milesToKm
            "Kilometer" -> meters / kmToM
            "Meter" -> meters
            "Centimeter" -> meters * mToCm
            "Millimeter" -> meters * cmToMm * mToCm
            "Inches" -> meters / inToCm * mToCm
            "Feet" -> meters / (ftToIn * inToCm) * mToCm
            "Yard" -> meters / (ydToFt * ftToIn * inToCm) * mToCm
            else -> 0.0
        }
    }

    // This function helps to display the message in the terminal
    private fun displayMessage(result: Double, fromUnit: String, toUnit: String) {
        val message = "$result $fromUnit has been converted to $toUnit\n Look for Output in App"
        Log.d("toUnit", message)
    }
}