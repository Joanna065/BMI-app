package com.example.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.example.bmi.MainActivity.Companion.CATEGORY_KEY
import com.example.bmi.MainActivity.Companion.RESULT_KEY
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        // make text view scrollable
        categoryDescriptionTV.movementMethod = ScrollingMovementMethod()

        val bundle = this.intent.extras
        if (bundle != null) {
            val result = bundle.getString(RESULT_KEY)!!
            val category = bundle.getString(CATEGORY_KEY)!!
            val resultString = "$result\n$category"
            updateInfoView(resultString, category)
        }
    }

    private fun updateInfoView(result: String, category: String) {
        val categoryEnum = CategoriesBmi.valueOfCategoryBmi(this.resources, category)
        resultBmiTV.setTextColor(categoryEnum.getColor(this.resources))
        resultBmiTV.text = result
        categoryDescriptionTV.text = categoryEnum.getDescription(this.resources)
        bmiCatIV.setImageResource(categoryEnum.imageId)
    }
}
