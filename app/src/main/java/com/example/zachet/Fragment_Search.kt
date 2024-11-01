package com.example.zachet

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.zachet.ui.theme.Weather
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment_Search.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment_Search : Fragment() {
    lateinit var city1: EditText

    lateinit var city_wr: TextView
    lateinit var temp_wr: TextView
    lateinit var air_wr: TextView
    lateinit var speed_wr: TextView
    lateinit var button:Button
    lateinit var button2:Button

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
    var weaList = mutableListOf<Weather>()
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment__search, container, false)
        city1 = view.findViewById(R.id.users_city)
        button=view.findViewById(R.id.search)
        button2=view.findViewById(R.id.my)
        city_wr = view.findViewById(R.id.city_written)
        temp_wr = view.findViewById(R.id.temp_written)
        air_wr = view.findViewById(R.id.airPres_written)
        speed_wr = view.findViewById(R.id.windSpeed_written)
        button.setOnClickListener {

                if (city1.text.toString().isNotEmpty()) {
            var key = "6ecd2b8f7c4faf4e80efe6296b01e089"
            var url =
                "https://api.openweathermap.org/data/2.5/weather?q=" + city1.text.toString() + "&appid=" + key
            val queue = Volley.newRequestQueue(requireContext())

            val stringRequest = StringRequest(
                Request.Method.GET,
                url,
                { response ->
                    val obj = JSONObject(response)

                    val cityName = obj.getString("name")
                    city_wr.text = cityName

                    val tempOBJ = obj.getJSONObject("main")
                    val temp = tempOBJ.getDouble("temp")
                    temp_wr.text = "$temp °C"

                    val airOBJ = obj.getJSONObject("main")
                    val pressure = airOBJ.getDouble("pressure")
                    air_wr.text = pressure.toString()

                    val windObject = obj.getJSONObject("wind")
                    val windSpeed = windObject.getDouble("speed")
                    speed_wr.text = "$windSpeed м/с"

                    weaList.add(Weather(cityName, temp, pressure,windSpeed))// Don't forget to apply changes
                },
                {
                    Log.d("MyLog", "Volley error: $it")
                }
            )
            queue.add(stringRequest)}
         else {
            Snackbar.make(view, "Введите город", Snackbar.LENGTH_LONG).show()
        }}
        button2.setOnClickListener {
            val sharedPreferences = requireActivity().getSharedPreferences("MyWeayher",
                AppCompatActivity.MODE_PRIVATE
            )
            val editor = sharedPreferences.edit()
            val gson = Gson() // Make sure to include Gson dependency in your build.gradle
            val json = gson.toJson(weaList) // Convert the list to JSON
            editor.putString("recipes", json)
            editor.apply()

            val detailFragment = MyWeather() // Замените на ваш следующий фрагмент
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, detailFragment)
                .addToBackStack(null)
                .commit()
        }
        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment_Search.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment_Search().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}