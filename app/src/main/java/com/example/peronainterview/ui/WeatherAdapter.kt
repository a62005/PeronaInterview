package com.example.peronainterview.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.peronainterview.database.WeatherForecast
import com.example.peronainterview.databinding.ItemImageBinding
import com.example.peronainterview.databinding.ItemTextBinding

class WeatherAdapter(private val onWeatherListener: OnWeatherListener): ListAdapter<WeatherForecast, WeatherViewHolder>(WeatherCompare()) {

    companion object {
        const val TYPE_IMAGE = 0
        const val TYPE_TEXT = 1
    }

    class WeatherCompare: DiffUtil.ItemCallback<WeatherForecast>() {
        override fun areItemsTheSame(oldItem: WeatherForecast, newItem: WeatherForecast): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WeatherForecast, newItem: WeatherForecast): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return when (viewType) {
            TYPE_IMAGE -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemImageBinding.inflate(layoutInflater,parent,false)
                ImageViewHolder(binding)
            }
            else -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTextBinding.inflate(layoutInflater, parent, false)
                TextViewHolder(binding, onWeatherListener)
            }
        }
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        if (holder is TextViewHolder) {
            holder.bind(getItem(position))
        }
    }

    override fun submitList(list: MutableList<WeatherForecast>?) {
        if (list != null) {
            val quantity = (list.size / 3)
            for (i in 1 until quantity + 1) {
                val emptyItem = WeatherForecast(startTime = "",endTime = "",temperature = "")
                val position = i * 3 -1
                list.add(position, emptyItem)
            }
        }
        super.submitList(list)
    }

    override fun getItemViewType(position: Int): Int {
        val mode = (position + 1) % 3
        return if (mode == TYPE_IMAGE) {
            TYPE_IMAGE
        } else {
            TYPE_TEXT
        }
    }
}

interface OnWeatherListener {
    fun onWeatherClickListener(weatherForecast: WeatherForecast)
}