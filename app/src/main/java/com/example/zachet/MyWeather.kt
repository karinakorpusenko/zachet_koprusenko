package com.example.zachet

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zachet.ui.theme.Weather
import com.example.zachet.ui.theme.WeatherAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
lateinit var recyclerView:RecyclerView
private lateinit var weatherAdapter: WeatherAdapter
private lateinit var weathers: MutableList<Weather>
/**
 * A simple [Fragment] subclass.
 * Use the [MyWeather.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyWeather : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_weather, container, false)
        // Inflate the layout for this fragment
        recyclerView = view.findViewById(R.id.rv_tasks)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Получаем список рецептов из Intent
        val sharedPreferences = requireActivity().getSharedPreferences("MyWeather", AppCompatActivity.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("weathers", null)
        val type = object : TypeToken<List<Weather>>() {}.type
        val savedWeathers: List<Weather>? = gson.fromJson(json, type)

        // Use saved recipes or the intent extras
        weathers = savedWeathers?.toMutableList() ?: mutableListOf()
        weatherAdapter = WeatherAdapter(weathers)
        recyclerView.adapter = weatherAdapter
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyWeather.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyWeather().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}