package com.example.bmi

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import logic.DataBmi

class BmiResultAdapter(private val results: MutableList<DataBmi>, private val resources: Resources) : RecyclerView.Adapter<BmiResultAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.history_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = results.size

    override fun onBindViewHolder(holder: BmiResultAdapter.ViewHolder, position: Int) {
        val recordBmiData = results[position]
        val categoryEnum = CategoriesBmi.valueOfCategoryBmi(resources,recordBmiData.category)

        holder.result.setTextColor(categoryEnum.getColor(resources))
        holder.image.setImageResource(categoryEnum.imageId)
        holder.weight.text = recordBmiData.weight
        holder.height.text = recordBmiData.height
        holder.date.text = recordBmiData.date

        val resultString = "${recordBmiData.result}\n${recordBmiData.category}"
        holder.result.text = resultString
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val weight: TextView = itemView.findViewById(R.id.weightTV)
        val height: TextView = itemView.findViewById(R.id.heightTV)
        val result: TextView = itemView.findViewById(R.id.bmiResultTV)
        val image: ImageView = itemView.findViewById(R.id.icon_categoryIV)
        val date: TextView = itemView.findViewById(R.id.dateTV)
    }
}