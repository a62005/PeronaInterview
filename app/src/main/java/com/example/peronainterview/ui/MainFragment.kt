package com.example.peronainterview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.peronainterview.database.MainApplication
import com.example.peronainterview.database.WeatherForecast
import com.example.peronainterview.databinding.FragmentMainBinding
import com.example.peronainterview.viewmodel.MainRepository
import com.example.peronainterview.viewmodel.MainViewModel
import com.example.peronainterview.viewmodel.MainViewModelFactory

class MainFragment: Fragment(), OnWeatherListener {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(getRepository())
    }
    private lateinit var weatherAdapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getLaunchTimes().let { times ->
            if (times > 1) {
                Toast.makeText(requireContext(), "歡迎回來", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater)
        weatherAdapter = WeatherAdapter(this)
        binding.rvList.adapter = weatherAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.weatherList.observe(viewLifecycleOwner) {
            weatherAdapter.submitList(it.toMutableList())
        }
    }

    override fun onWeatherClickListener(weatherForecast: WeatherForecast) {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToSecondFragment(weatherForecast))
    }
}

fun Fragment.getRepository(): MainRepository {
    return (requireActivity().application as MainApplication).repository
}