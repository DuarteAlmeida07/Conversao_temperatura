package com.example.conversao

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var etTemperature: EditText
    private lateinit var btnCelsiusToFahrenheit: Button
    private lateinit var btnCelsiusToKelvin: Button
    private lateinit var btnFahrenheitToCelsius: Button
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etTemperature = findViewById(R.id.etTemperature)
        btnCelsiusToFahrenheit = findViewById(R.id.btnCelsiusToFahrenheit)
        btnCelsiusToKelvin = findViewById(R.id.btnCelsiusToKelvin)
        btnFahrenheitToCelsius = findViewById(R.id.btnFahrenheitToCelsius)
        tvResult = findViewById(R.id.tvResult)

        btnCelsiusToFahrenheit.setOnClickListener {
            convertTemperature { celsius ->
                val fahrenheit = celsius * 1.8 + 32
                String.format(Locale.getDefault(), "%.2f °F", fahrenheit)
            }
        }

        btnCelsiusToKelvin.setOnClickListener {
            convertTemperature { celsius ->
                val kelvin = celsius + 273.15
                String.format(Locale.getDefault(), "%.2f K", kelvin)
            }
        }

        btnFahrenheitToCelsius.setOnClickListener {
            convertTemperature { fahrenheit ->
                val celsius = (fahrenheit - 32) / 1.8
                String.format(Locale.getDefault(), "%.2f °C", celsius)
            }
        }
    }

    private fun convertTemperature(conversion: (Double) -> String) {
        val inputStr = etTemperature.text.toString().trim()
        if (inputStr.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_invalid_input), Toast.LENGTH_SHORT).show()
            return
        }
        val value = inputStr.toDoubleOrNull()
        if (value == null) {
            Toast.makeText(this, getString(R.string.error_invalid_input), Toast.LENGTH_SHORT).show()
            return
        }
        val resultText = getString(R.string.result_prefix) + conversion(value)
        tvResult.text = resultText
    }
}
