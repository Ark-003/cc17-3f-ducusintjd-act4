package com.example.cc17_3f_ducusintjd_act4

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var billValue: EditText
    private lateinit var tipOptions: RadioGroup
    private lateinit var switchRoundUp: Switch
    private lateinit var calcButton: Button
    private lateinit var tipAmt: TextView
    private lateinit var tipVal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize UI components
        billValue = findViewById(R.id.billValue)
        tipOptions = findViewById(R.id.tipOptions)
        switchRoundUp = findViewById(R.id.switch1)
        calcButton = findViewById(R.id.calcButton)
        tipAmt = findViewById(R.id.tipAmt)
        tipVal = findViewById(R.id.tipVal)

        // Request focus and show the keyboard when the EditText is clicked
        billValue.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showKeyboard(billValue)
            }
        }

        // Set up the click listener for the calculate button
        calcButton.setOnClickListener {
            calculateTip()
        }
    }

    private fun showKeyboard(editText: EditText) {
        editText.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun calculateTip() {
        val billAmountString = billValue.text.toString()
        if (billAmountString.isEmpty()) {
            tipAmt.text = "Tip Amount"
            tipVal.text = "$00.00"
            return
        }

        val billAmount = billAmountString.toDouble()

        val selectedTipId = tipOptions.checkedRadioButtonId
        val tipPercentage = when (selectedTipId) {
            R.id.option1 -> 0.20
            R.id.option2 -> 0.18
            R.id.option3 -> 0.15
            else -> 0.0
        }

        var tipAmount = billAmount * tipPercentage

        if (switchRoundUp.isChecked) {
            tipAmount = kotlin.math.ceil(tipAmount)
        }

        tipVal.text = String.format("$%.2f", tipAmount)
        tipAmt.text = "Tip Amount"
    }
}
