package com.doterra.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.doterra.app.databinding.FragmentHomeBinding
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var isLampOn: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using View Binding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance().reference

        // Listen to the value of the lamp control in the database
        database.child("Control").child("lampu").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                isLampOn = snapshot.getValue(Boolean::class.java) ?: false
                updateLampBackground()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors
            }
        })

        // Set click listener to toggle lamp status
        binding.bgLamp.setOnClickListener {
            toggleLamp()
        }

        // Get and update sensor values from Firebase Realtime Database
        database.child("Sensor").child("temperature").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val temperature = (snapshot.getValue(Int::class.java)?.toString() ?: "N/A") + "\u00B0C"
                binding.temperatureValue.text = temperature
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors
            }
        })

        database.child("Sensor").child("humidity").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val humidity = (snapshot.getValue(Int::class.java)?.toString() ?: "N/A") + "%"
                binding.humidityValue.text = humidity
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors
            }
        })

        database.child("Sensor").child("soilMoisture").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val soilMoisture = (snapshot.getValue(Int::class.java)?.toString() ?: "N/A") + "%"
                binding.soilmoistureValue.text = soilMoisture
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors
            }
        })
    }

    private fun updateLampBackground() {
        if (isLampOn) {
            binding.bgLamp.setBackgroundResource(R.drawable.lamp_on)
        } else {
            binding.bgLamp.setBackgroundResource(R.drawable.lamp_off_black)
        }
    }

    private fun toggleLamp() {
        isLampOn = !isLampOn
        database.child("Control").child("lampu").setValue(isLampOn)
        updateLampBackground()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
