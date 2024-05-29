package com.doterra.app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.AppCompatButton

class WelcomeFragment2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome2, container, false)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        // Any other setup for your view can go here
//        val loginButton = view.findViewById<AppCompatButton>(R.id.loginButton)
//        loginButton.setOnClickListener {
//            // Navigate to the next activity
//            val intent = Intent(requireContext(), MainActivity::class.java)
//            startActivity(intent)
//        }
//
//    }
}
