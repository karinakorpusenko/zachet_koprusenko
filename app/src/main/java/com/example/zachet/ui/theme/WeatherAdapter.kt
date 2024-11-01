package com.example.zachet.ui.theme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zachet.R

class WeatherAdapter (private val weatherList: List<Weather>) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    class WeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cityTextView: TextView = view.findViewById(R.id.city_written)
        val temperatureTextView: TextView = view.findViewById(R.id.temp_written)
        val pressureTextView: TextView = view.findViewById(R.id.airPres_written)
        val windSpeedTextView: TextView = view.findViewById(R.id.windSpeed_written)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycleview, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = weatherList[position]
        holder.cityTextView.text = weather.city
        holder.temperatureTextView.text = weather.temperature.toString()
        holder.pressureTextView.text = weather.pressure.toString()
        holder.windSpeedTextView.text = weather.windSpeed.toString()
    }

    override fun getItemCount() = weatherList.size}