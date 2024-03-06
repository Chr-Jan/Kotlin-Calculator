package com.example.calculator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.databinding.ActivityMainBinding // Generated binding class

// MainActivity class, the entry point for the app
class MainActivity : AppCompatActivity() {

    // Late-initialized binding variable for accessing UI elements
    private lateinit var binding: ActivityMainBinding

    // Called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge mode for a more immersive experience
        enableEdgeToEdge()

        // Inflate the layout and set it as the content view
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle system bars (status bar and navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Apply padding to avoid content overlapping with system bars
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set click listeners for operation buttons
        binding.addButton.setOnClickListener { calculateLogic(Operation.ADD) }
        binding.subtractButton.setOnClickListener { calculateLogic(Operation.SUBTRACT) }
        binding.multiplyButton.setOnClickListener { calculateLogic(Operation.MULTIPLY) }
        binding.divideButton.setOnClickListener { calculateLogic(Operation.DIVIDE) }
    }

    // Function to handle calculations based on the selected operation
    fun calculateLogic(operation: Operation) {
        // Get text values from EditText views
        val firstNumberText = binding.firstNumber.text.toString()
        val secondNumberText = binding.secondNumber.text.toString()

        // Check for empty inputs
        if (firstNumberText.isEmpty() || secondNumberText.isEmpty()) {
            Toast.makeText(this, "Fill both number fields, please", Toast.LENGTH_SHORT).show()
            return
        }

        // Attempt to parse inputs as doubles
        val firstNumber = firstNumberText.toDoubleOrNull()
        val secondNumber = secondNumberText.toDoubleOrNull()

        // If both numbers are valid
        if (firstNumber != null && secondNumber != null) {
            var result = when (operation) {
                Operation.ADD -> firstNumber + secondNumber
                Operation.SUBTRACT -> firstNumber - secondNumber
                Operation.MULTIPLY -> firstNumber * secondNumber
                Operation.DIVIDE -> {
                    if (secondNumber != 0.0) {
                        firstNumber / secondNumber
                    } else {
                        Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show()
                        return
                    }
                }
                else -> 0.0 // Handle invalid operations
            }
            // Update the result TextView
            binding.resultText.text = result.toString()
        } else {
            Toast.makeText(this, "Please enter only numbers", Toast.LENGTH_SHORT).show()
        }
    }

    // Enum for defining supported operations
    enum class Operation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE
    }
}
