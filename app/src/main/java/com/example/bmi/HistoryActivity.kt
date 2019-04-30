package com.example.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bmi.MainActivity.Companion.RESULTS_LIST_KEY
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val sharedPref = SharedPreference(this)
        historyRV.layoutManager = LinearLayoutManager(this)
        val list = sharedPref.getValueList(RESULTS_LIST_KEY)
        list.reverse()
        historyRV.adapter = BmiResultAdapter(list, this.resources)
    }
}
