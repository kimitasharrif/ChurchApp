package com.sherrif.churchapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.textview.MaterialTextView

class AboutChurchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_about_church, container, false)

        // Example texts for ministry and core messages
        val originalMinistryMessage = "To bring people to Christ and to the membership of His family, disciple and equip them (Ephesians 4:12)\r\n\r\nTo add value in people's lives, through teaching and biblical principles and being a role model,\r\n\r\nTo develop a Kingdom community that impacts other communities.\r\nExample Academic Community\r\n and Business Community"
        val originalCoreMessage = "We are a family.\r\nExcellence in Service.\r\nSound Biblical Teachings.\r\nLearning.\r\nGod Centered worship.\r\nChildren Ministry.\r\n"

        // Get the TextViews from the inflated layout
        val ministryMessageTextView = view.findViewById<MaterialTextView>(R.id.ministry_message)
        val coreMessageTextView = view.findViewById<MaterialTextView>(R.id.core_message)

        // Remove the unwanted characters and set the text
        val ministryMessage = originalMinistryMessage.replace("\r\n", "\n")
        ministryMessageTextView.text = ministryMessage

        val coreMessage = originalCoreMessage.replace("\r\n", "\n")
        coreMessageTextView.text = coreMessage

        return view
    }
}
