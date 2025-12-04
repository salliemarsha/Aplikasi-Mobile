package com.sallie.asesmengasal

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class TemperatureConversionActivity : AppCompatActivity() {

    // View declarations
    private lateinit var etTemperature: EditText
    private lateinit var spTemperature: Spinner
    private lateinit var btChange: Button
    private lateinit var tvTemperatureResult: TextView

    // Constants
    private companion object {
        private const val DEFAULT_VALUE = 0.0
        private const val DECIMAL_PLACES = 2
        private const val EMPTY_INPUT_MESSAGE = "Masukkan suhu terlebih dahulu"
        private const val RESULT_FORMAT = "%.${DECIMAL_PLACES}f"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperature_conversion)

        initializeViews()
        setupSpinner()
        setupButtonClickListener()
    }

    /**
     * Initialize all views from layout
     */
    private fun initializeViews() {
        etTemperature = findViewById(R.id.etTemperature)
        spTemperature = findViewById(R.id.spTemperature)
        btChange = findViewById(R.id.btChange)
        tvTemperatureResult = findViewById(R.id.tvTemperatureResult)

        // Set default values
        tvTemperatureResult.text = ""
    }

    /**
     * Setup spinner with conversion options
     */
    private fun setupSpinner() {
        val conversionList = listOf(
            "Celsius → Fahrenheit",
            "Celsius → Kelvin",
            "Celsius → Reamur",

            "Fahrenheit → Celsius",
            "Fahrenheit → Kelvin",
            "Fahrenheit → Reamur",

            "Kelvin → Celsius",
            "Kelvin → Fahrenheit",
            "Kelvin → Reamur",

            "Reamur → Celsius",
            "Reamur → Fahrenheit",
            "Reamur → Kelvin"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            conversionList
        )

        spTemperature.adapter = adapter

        // Optional: Add spinner item selection listener
        spTemperature.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                // Clear result when selection changes
                tvTemperatureResult.text = ""
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    /**
     * Setup button click listener
     */
    private fun setupButtonClickListener() {
        btChange.setOnClickListener {
            performTemperatureConversion()
        }
    }

    /**
     * Perform temperature conversion based on user input
     */
    private fun performTemperatureConversion() {
        val inputText = etTemperature.text.toString().trim()

        // Validate input
        if (inputText.isEmpty()) {
            showToast(EMPTY_INPUT_MESSAGE)
            tvTemperatureResult.text = ""
            return
        }

        // Parse input value safely
        val value = try {
            inputText.toDouble()
        } catch (e: NumberFormatException) {
            showToast("Format angka tidak valid")
            tvTemperatureResult.text = ""
            return
        }

        // Get selected conversion
        val selectedConversion = spTemperature.selectedItem.toString()

        // Perform conversion
        val result = calculateTemperature(value, selectedConversion)

        // Display result
        displayResult(result, selectedConversion)
    }

    /**
     * Calculate temperature based on selected conversion
     */
    private fun calculateTemperature(value: Double, conversion: String): Double {
        return when (conversion) {
            // Celsius conversions
            "Celsius → Fahrenheit" -> (value * 9.0 / 5.0) + 32.0
            "Celsius → Kelvin" -> value + 273.15
            "Celsius → Reamur" -> value * 4.0 / 5.0

            // Fahrenheit conversions
            "Fahrenheit → Celsius" -> (value - 32.0) * 5.0 / 9.0
            "Fahrenheit → Kelvin" -> ((value - 32.0) * 5.0 / 9.0) + 273.15
            "Fahrenheit → Reamur" -> (value - 32.0) * 4.0 / 9.0

            // Kelvin conversions
            "Kelvin → Celsius" -> value - 273.15
            "Kelvin → Fahrenheit" -> (value - 273.15) * 9.0 / 5.0 + 32.0
            "Kelvin → Reamur" -> (value - 273.15) * 4.0 / 5.0

            // Reamur conversions
            "Reamur → Celsius" -> value * 5.0 / 4.0
            "Reamur → Fahrenheit" -> (value * 9.0 / 4.0) + 32.0
            "Reamur → Kelvin" -> (value * 5.0 / 4.0) + 273.15

            else -> DEFAULT_VALUE
        }
    }

    /**
     * Display formatted result
     */
    private fun displayResult(result: Double, conversion: String) {
        // Format result with 2 decimal places
        val formattedResult = String.format(RESULT_FORMAT, result)

        // Extract units for display
        val parts = conversion.split(" → ")
        if (parts.size == 2) {
            val fromUnit = parts[0]
            val toUnit = parts[1]
            tvTemperatureResult.text = "$formattedResult °$toUnit"
        } else {
            tvTemperatureResult.text = formattedResult
        }
    }

    /**
     * Get conversion formula for display (optional)
     */
    private fun getConversionFormula(conversion: String): String {
        return when (conversion) {
            "Celsius → Fahrenheit" -> "Formula: (°C × 9/5) + 32"
            "Celsius → Kelvin" -> "Formula: °C + 273.15"
            "Celsius → Reamur" -> "Formula: °C × 4/5"

            "Fahrenheit → Celsius" -> "Formula: (°F - 32) × 5/9"
            "Fahrenheit → Kelvin" -> "Formula: (°F - 32) × 5/9 + 273.15"
            "Fahrenheit → Reamur" -> "Formula: (°F - 32) × 4/9"

            "Kelvin → Celsius" -> "Formula: K - 273.15"
            "Kelvin → Fahrenheit" -> "Formula: (K - 273.15) × 9/5 + 32"
            "Kelvin → Reamur" -> "Formula: (K - 273.15) × 4/5"

            "Reamur → Celsius" -> "Formula: °R × 5/4"
            "Reamur → Fahrenheit" -> "Formula: (°R × 9/4) + 32"
            "Reamur → Kelvin" -> "Formula: °R × 5/4 + 273.15"

            else -> ""
        }
    }

    /**
     * Show toast message
     */
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Clear all inputs and results
     */
    private fun clearAll() {
        etTemperature.text.clear()
        tvTemperatureResult.text = ""
        spTemperature.setSelection(0)
    }
}