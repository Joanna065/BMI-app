package com.example.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_about_me.*


class AboutMeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_me)

        // make text view scrollable
        bmiDescriptionTV.movementMethod = ScrollingMovementMethod()
    }

}
