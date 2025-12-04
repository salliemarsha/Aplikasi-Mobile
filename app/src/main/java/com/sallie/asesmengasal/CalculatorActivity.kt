package com.sallie.asesmengasal

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import net.objecthunter.exp4j.ExpressionBuilder
import java.text.DecimalFormat

class CalculatorActivity : AppCompatActivity() {

    private lateinit var tvSolution: TextView
    private lateinit var tvResult: TextView

    private lateinit var mb0: MaterialButton
    private lateinit var mb1: MaterialButton
    private lateinit var mb2: MaterialButton
    private lateinit var mb3: MaterialButton
    private lateinit var mb4: MaterialButton
    private lateinit var mb5: MaterialButton
    private lateinit var mb6: MaterialButton
    private lateinit var mb7: MaterialButton
    private lateinit var mb8: MaterialButton
    private lateinit var mb9: MaterialButton

    private lateinit var mbPlus: MaterialButton
    private lateinit var mbMinus: MaterialButton
    private lateinit var mbMultiply: MaterialButton
    private lateinit var mbDivision: MaterialButton
    private lateinit var mbEqualTo: MaterialButton
    private lateinit var mbComma: MaterialButton
    private lateinit var mbOpen: MaterialButton
    private lateinit var mbClose: MaterialButton
    private lateinit var mbC: MaterialButton
    private lateinit var mbAC: MaterialButton

    private var currentInput = StringBuilder()
    private var currentExpression = ""
    private var isResultDisplayed = false
    private var isLiveCalculationEnabled = true
    private var lastValidResult = "0"

    private val decimalFormat = DecimalFormat("#.##########")

    private lateinit var backPressedCallback: OnBackPressedCallback

    companion object {
        private const val TAG = "CalculatorActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        initViews()
        setupClickListeners()
        setupBackPressedHandler()

        Log.d(TAG, "CalculatorActivity created")
    }

    private fun setupBackPressedHandler() {
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val hasActiveInput = currentInput.isNotEmpty() || currentExpression.isNotEmpty()

                if (hasActiveInput) {
                    clearCalculatorInput()
                } else {
                    isEnabled = false
                    finish()
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    private fun clearCalculatorInput() {
        currentInput.clear()
        currentExpression = ""
        isResultDisplayed = false
        isLiveCalculationEnabled = true
        lastValidResult = "0"
        tvSolution.text = "0"
        tvResult.text = "0"
    }

    private fun initViews() {
        tvSolution = findViewById(R.id.tvSolution)
        tvResult = findViewById(R.id.tvResult)

        mb0 = findViewById(R.id.mb0)
        mb1 = findViewById(R.id.mb1)
        mb2 = findViewById(R.id.mb2)
        mb3 = findViewById(R.id.mb3)
        mb4 = findViewById(R.id.mb4)
        mb5 = findViewById(R.id.mb5)
        mb6 = findViewById(R.id.mb6)
        mb7 = findViewById(R.id.mb7)
        mb8 = findViewById(R.id.mb8)
        mb9 = findViewById(R.id.mb9)

        mbPlus = findViewById(R.id.mbPlus)
        mbMinus = findViewById(R.id.mbMinus)
        mbMultiply = findViewById(R.id.mbMultiply)
        mbDivision = findViewById(R.id.mbDivision)
        mbEqualTo = findViewById(R.id.mbEqualTo)
        mbComma = findViewById(R.id.mbComma)
        mbOpen = findViewById(R.id.mbOpen)
        mbClose = findViewById(R.id.mbClose)
        mbC = findViewById(R.id.mbC)
        mbAC = findViewById(R.id.mbAC)

        tvSolution.text = "0"
        tvResult.text = "0"
    }

    private fun setupClickListeners() {
        // Number buttons
        mb0.setOnClickListener { appendNumber("0") }
        mb1.setOnClickListener { appendNumber("1") }
        mb2.setOnClickListener { appendNumber("2") }
        mb3.setOnClickListener { appendNumber("3") }
        mb4.setOnClickListener { appendNumber("4") }
        mb5.setOnClickListener { appendNumber("5") }
        mb6.setOnClickListener { appendNumber("6") }
        mb7.setOnClickListener { appendNumber("7") }
        mb8.setOnClickListener { appendNumber("8") }
        mb9.setOnClickListener { appendNumber("9") }

        // Operation buttons
        mbPlus.setOnClickListener { appendOperator("+") }
        mbMinus.setOnClickListener { appendOperator("-") }
        mbMultiply.setOnClickListener { appendOperator("*") }
        mbDivision.setOnClickListener { appendOperator("/") }

        // Parentheses
        mbOpen.setOnClickListener { appendParenthesis("(") }
        mbClose.setOnClickListener { appendParenthesis(")") }

        // Decimal point
        mbComma.setOnClickListener { appendDecimal() }

        // Function buttons
        mbAC.setOnClickListener { clearAll() }
        mbC.setOnClickListener { clearLast() }
        mbEqualTo.setOnClickListener { calculateResult() }
    }

    private fun appendNumber(number: String) {
        if (isResultDisplayed) {
            currentInput.clear()
            currentExpression = ""
            isResultDisplayed = false
        }

        currentInput.append(number)
        currentExpression = currentInput.toString()

        updateDisplay()
        performLiveCalculation()
    }

    private fun appendOperator(operator: String) {
        if (currentInput.isEmpty()) {
            // If no input yet, use last result or start with 0
            if (lastValidResult != "0" && !isResultDisplayed) {
                currentInput.append(lastValidResult)
                currentExpression = currentInput.toString()
            } else {
                currentInput.append("0")
                currentExpression = currentInput.toString()
            }
        }

        val lastChar = if (currentInput.isNotEmpty()) currentInput.last() else ' '

        // Check if last character is an operator
        if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/') {
            // Replace the last operator
            currentInput.deleteCharAt(currentInput.length - 1)
        }

        currentInput.append(operator)
        currentExpression = currentInput.toString()
        isResultDisplayed = false

        updateDisplay()
    }

    private fun appendParenthesis(parenthesis: String) {
        currentInput.append(parenthesis)
        currentExpression = currentInput.toString()
        isResultDisplayed = false

        updateDisplay()
        performLiveCalculation()
    }

    private fun appendDecimal() {
        if (isResultDisplayed) {
            currentInput.clear()
            currentExpression = ""
            isResultDisplayed = false
        }

        // Check if current number already has a decimal point
        val lastNumber = getLastNumber()
        if (!lastNumber.contains(".")) {
            if (lastNumber.isEmpty()) {
                currentInput.append("0.")
            } else {
                currentInput.append(".")
            }
            currentExpression = currentInput.toString()

            updateDisplay()
        }
    }

    private fun getLastNumber(): String {
        val expression = currentInput.toString()
        var i = expression.length - 1

        while (i >= 0) {
            val c = expression[i]
            if (c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')') {
                break
            }
            i--
        }

        return expression.substring(i + 1)
    }

    private fun clearAll() {
        currentInput.clear()
        currentExpression = ""
        isResultDisplayed = false
        isLiveCalculationEnabled = true
        lastValidResult = "0"
        tvSolution.text = "0"
        tvResult.text = "0"
    }

    private fun clearLast() {
        if (currentInput.isNotEmpty()) {
            currentInput.deleteCharAt(currentInput.length - 1)
            currentExpression = currentInput.toString()

            if (currentInput.isEmpty()) {
                tvSolution.text = "0"
                tvResult.text = "0"
            } else {
                updateDisplay()
                performLiveCalculation()
            }
        } else {
            tvSolution.text = "0"
            tvResult.text = "0"
        }

        isResultDisplayed = false
    }

    private fun calculateResult() {
        if (currentExpression.isEmpty()) {
            return
        }

        try {
            // Validate expression before calculation
            val validatedExpression = validateExpression(currentExpression)

            val expression = ExpressionBuilder(validatedExpression).build()
            val result = expression.evaluate()

            // Handle division by zero
            if (result.isInfinite() || result.isNaN()) {
                tvResult.text = "Error"
                isLiveCalculationEnabled = false
                return
            }

            // Format the result
            val formattedResult = if (result % 1 == 0.0) {
                result.toLong().toString()
            } else {
                decimalFormat.format(result)
            }

            // Store the result
            lastValidResult = formattedResult

            // Update displays
            tvSolution.text = currentExpression
            tvResult.text = formattedResult

            // Reset input with the result for continued calculations
            currentInput.clear()
            currentInput.append(formattedResult)
            currentExpression = formattedResult
            isResultDisplayed = true

        } catch (e: Exception) {
            Log.e(TAG, "Calculation error: ${e.message}")
            tvResult.text = "Error"
            isLiveCalculationEnabled = false
        }
    }

    private fun validateExpression(expression: String): String {
        var validated = expression

        // Remove trailing operators
        while (validated.isNotEmpty()) {
            val lastChar = validated.last()
            if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/') {
                validated = validated.dropLast(1)
            } else {
                break
            }
        }

        // Balance parentheses
        var openCount = validated.count { it == '(' }
        var closeCount = validated.count { it == ')' }

        while (openCount > closeCount) {
            validated += ")"
            closeCount++
        }

        // Handle empty expression
        if (validated.isEmpty()) {
            validated = "0"
        }

        return validated
    }

    private fun performLiveCalculation() {
        if (!isLiveCalculationEnabled || currentExpression.isEmpty()) {
            return
        }

        try {
            val validatedExpression = validateExpression(currentExpression)

            // Don't calculate if expression ends with operator or open parenthesis
            val lastChar = if (validatedExpression.isNotEmpty()) validatedExpression.last() else ' '
            if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/' || lastChar == '(') {
                tvResult.text = "0"
                return
            }

            val expression = ExpressionBuilder(validatedExpression).build()
            val result = expression.evaluate()

            if (result.isInfinite() || result.isNaN()) {
                tvResult.text = "Error"
                return
            }

            val formattedResult = if (result % 1 == 0.0) {
                result.toLong().toString()
            } else {
                decimalFormat.format(result)
            }

            tvResult.text = formattedResult
            lastValidResult = formattedResult

        } catch (e: Exception) {
            // Don't show error in live calculation, just keep previous result
            Log.d(TAG, "Live calculation failed: ${e.message}")
        }
    }

    private fun updateDisplay() {
        if (currentExpression.isEmpty()) {
            tvSolution.text = "0"
        } else {
            tvSolution.text = currentExpression
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::backPressedCallback.isInitialized) {
            backPressedCallback.remove()
        }
    }
}