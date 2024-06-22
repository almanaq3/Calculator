package com.almanaque.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import  android.widget.TextView

class MainActivity : AppCompatActivity(){

    private lateinit var textViewExpression: TextView
    private lateinit var textViewResult: TextView
    private var input: String = ""
    private var operator: String = ""
    private var firstOperand: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewExpression = findViewById(R.id.textViewExpression)
        textViewResult = findViewById(R.id.textViewResult)

        // Set onClickListeners for buttons
        setNumberClickListeners()
        setOperatorClickListeners()
    }

    private fun setNumberClickListeners(){
        val numberButtons = listOf<Button>(
            findViewById(R.id.button0), findViewById(R.id.button1),
            findViewById(R.id.button2), findViewById(R.id.button3),
            findViewById(R.id.button4), findViewById(R.id.button5),
            findViewById(R.id.button6), findViewById(R.id.button7),
            findViewById(R.id.button8), findViewById(R.id.button9),
            findViewById(R.id.buttonDecimal)
        )

        for (button in numberButtons){
            button.setOnClickListener {
                val number = (it as Button).text.toString()
                input += number
                textViewExpression.text = input
                textViewResult.text = input
            }
        }
    }

    private fun setOperatorClickListeners(){
        val operatorButtons = listOf<Button>(
            findViewById(R.id.buttonAdd), findViewById(R.id.buttonSubtract),
            findViewById(R.id.buttonMultiply), findViewById(R.id.buttonDivide),
            findViewById(R.id.buttonEquals), findViewById(R.id.buttonClear),
            findViewById(R.id.buttonDelete),
        )

        for (button in operatorButtons){
            button.setOnClickListener {
                val operator = (it as Button).text.toString()
                handleOperator(operator)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleOperator(op: String){
        when (op){
            "C" -> {
                input = ""
                operator = ""
                firstOperand = 0.0
                textViewExpression.text = ""
                textViewResult.text = "0"
            }
            "=" -> {
                val secondOperand = input.toDoubleOrNull()
                if (secondOperand != null){
                    val result = when (operator){
                        "+" -> firstOperand + secondOperand
                        "-" -> firstOperand - secondOperand
                        "×" -> firstOperand * secondOperand
                        "÷" -> if (secondOperand != 0.0) firstOperand / secondOperand else "Error"
                        else -> "?"
                    }
                    textViewExpression.text = "$firstOperand $operator $secondOperand ="
                    textViewResult.text = result.toString()
                    input = result.toString()
                } else {
                    textViewResult.text = "?"
                }
            }
            "⌫" -> {
                if (input.isNotEmpty()) {
                    input = input.dropLast(1)
                    textViewExpression.text = input
                    textViewResult.text = input
                }
            }
            else -> {
                firstOperand = input.toDoubleOrNull() ?: 0.0
                operator = op
                input = ""
                textViewExpression.text = "$firstOperand $operator"
                textViewResult.text = ""
            }
        }
    }
}