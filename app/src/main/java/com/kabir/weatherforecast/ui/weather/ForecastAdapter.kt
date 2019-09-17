package com.kabir.weatherforecast.ui.weather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kabir.weatherforecast.R
import com.kabir.weatherforecast.data.local.db.entity.ForecastData

class ForecastAdapter(private val context: Context) :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    private val forecastList = ArrayList<ForecastData>()
    private val degreeText = context.getString(R.string.degree)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        return ForecastViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_forecast, parent, false
            )
        )
    }

    override fun getItemCount(): Int = forecastList.size

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        forecastList[position].run {
            holder.dayTextView.text = day
            Glide.with(context)
                .load(groupIconUrl)
                .into(holder.iconImageView)
            holder.tempTextView.text =
                minTemperature.toString() + degreeText + "/" + maxTemperature.toString() + degreeText
        }
    }

    fun updateData(list: ArrayList<ForecastData>) {
        forecastList.clear()
        forecastList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ForecastViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dayTextView: TextView = view.findViewById(R.id.text_day)
        val iconImageView: ImageView = view.findViewById(R.id.image_icon)
        val tempTextView: TextView = view.findViewById(R.id.text_temp)
    }
}