package com.example.calculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.layar = findViewById<TextView>(R.id.layar)
        setNumericOnClickListener()
        setOperatorOnClickListener()
    }

    private val numericButtons = intArrayOf(
        R.id.b0, R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7, R.id.b8, R.id.b9
    )

    private val operatorButtons = intArrayOf(R.id.tambah, R.id.kurang, R.id.kali, R.id.bagi)

    private var layar: TextView? = null

    private var lastNumeric: Boolean = false

    private var stateError: Boolean = false

    private var lastDot: Boolean = false

    /**
     *NUMERIC BUTTON
     */

    private fun setNumericOnClickListener() {
        val listener = View.OnClickListener { v ->
            val button = v as Button
            if (stateError) {
                layar!!.text = button.text
                stateError = false
            } else {
                layar!!.append(button.text)
            }
            lastNumeric = true
        }
        for (id in numericButtons) {
            findViewById<View>(id).setOnClickListener(listener)
        }
    }

    /**
     * OPERATOR BUTTON
     */
    private fun setOperatorOnClickListener() {
        val listener = View.OnClickListener { v ->
            if (lastNumeric && !stateError) {
                val button = v as Button
                layar!!.append(button.text)
                lastNumeric = false
                lastDot = false
            }
        }
        for (id in operatorButtons) {
            findViewById<View>(id).setOnClickListener(listener)
        }
        findViewById<TextView>(R.id.titik).setOnClickListener(View.OnClickListener {
            if (lastNumeric && !stateError && !lastDot) {
                layar!!.append(".")
                lastNumeric = false
                lastDot = true
            }
        })

        findViewById<TextView>(R.id.hapus).setOnClickListener(View.OnClickListener {
            layar!!.text = ""
            lastNumeric = false
            stateError = false
            lastDot = false
        })

        findViewById<TextView>(R.id.samadengan).setOnClickListener(View.OnClickListener { onEqual() })
    }


    /**
     * CALCULATION
     */
    private fun onEqual() {
        if (lastNumeric && !stateError) {
            val txt = layar!!.text.toString()
            val expression = ExpressionBuilder(txt).build()
            try {
                val result = expression.evaluate()
                layar!!.text = java.lang.Double.toString(result)
                lastDot = true
            } catch (ex: ArithmeticException) {
                layar!!.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }


}