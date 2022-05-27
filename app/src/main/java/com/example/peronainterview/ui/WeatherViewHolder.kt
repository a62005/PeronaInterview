package com.example.peronainterview.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.peronainterview.database.WeatherForecast
import com.example.peronainterview.databinding.ItemImageBinding
import com.example.peronainterview.databinding.ItemTextBinding

abstract class WeatherViewHolder(view: View): RecyclerView.ViewHolder(view)

class TextViewHolder(private val binding: ItemTextBinding, private val onWeatherListener: OnWeatherListener): WeatherViewHolder(binding.root) {

    fun bind(item: WeatherForecast) {
        binding.tvStartTime.text = item.startTime
        binding.tvEndTime.text = item.endTime
        this.itemView.setOnClickListener {
            onWeatherListener.onWeatherClickListener(item)
        }
    }
}

class ImageViewHolder(binding: ItemImageBinding): WeatherViewHolder(binding.root)