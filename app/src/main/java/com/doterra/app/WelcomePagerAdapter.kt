package com.doterra.app

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView

class WelcomePagerAdapter(private val context: Context) : RecyclerView.Adapter<WelcomePagerAdapter.WelcomeViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val layouts = listOf(R.layout.fragment_welcome1, R.layout.fragment_welcome2)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WelcomeViewHolder {
        val view = layoutInflater.inflate(viewType, parent, false)
        return WelcomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: WelcomeViewHolder, position: Int) {
        if (position == 1) { // Fragment at position 1 is fragment_welcome2
            val loginButton = holder.itemView.findViewById<AppCompatButton>(R.id.loginButton)
            loginButton?.setOnClickListener {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = layouts.size

    override fun getItemViewType(position: Int): Int = layouts[position]

    inner class WelcomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
