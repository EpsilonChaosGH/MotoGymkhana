package com.example.motogymkhana.utils

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.example.motogymkhana.R

class HorizontalNumberPicker(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {
    private var et_number: EditText
    private var min: Int = 0
    private var max: Int = 20

    init {
        inflate(context, R.layout.numberpicker_horizontal, this)

        et_number = findViewById(R.id.et_number)

        val btn_less = findViewById<Button>(R.id.btn_less)
        btn_less.setOnClickListener(AddHandler(-1))

        val btn_more = findViewById<Button>(R.id.btn_more)
        btn_more.setOnClickListener(AddHandler(1))
    }

    /**
     * HANDLERS
     **/

    private inner class AddHandler(private val diff: Int) : View.OnClickListener {
        override fun onClick(v: View) {
            var newValue = getValue() + diff
            if (newValue < min) {
                newValue = min
            } else if (newValue > max) {
                newValue = max
            }
            et_number.setText(newValue.toString())
        }
    }

    /**
     * GETTERS & SETTERS
     */

    fun getValue(): Int {
        return try {
            et_number.text.toString().toInt()
        } catch (ex: NumberFormatException) {
            Log.e("HorizontalNumberPicker", ex.toString())
            0
        }
    }

    fun setValue(value: Int) {
        et_number.setText(value.toString())
    }
}
