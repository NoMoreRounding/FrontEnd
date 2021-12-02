package com.example.nomorerounding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pmc1)

        val buttonSignIn: Button     = findViewById(R.id.button_signin)
        val textViewSignUp: TextView = findViewById(R.id.textView_signup)

        val intentPMC2 = Intent(this, PMC2Activity::class.java)
        val intentPMC4 = Intent(this, PMC4Activity::class.java)

        buttonSignIn.setOnClickListener {
            startActivity(intentPMC2)
        }

        textViewSignUp.setOnClickListener {
            startActivity(intentPMC4)
        }
    }
}