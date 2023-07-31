package com.example.mytablayout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.w3c.dom.Text

class HomeFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * menangkap textview section label di HomeFragment
         */
        val tvLabel: TextView = view.findViewById(R.id.section_label)
        val tvName: TextView = view.findViewById(R.id.section_label2)

        /**
         *  mendapatkan data yang dikirimkan dari SectionPagerAdapter
         */
        val index = arguments?.getInt(ARG_SECTION_NUMBER)
        val name = arguments?.getString(ARG_NAME)

        /**
         * menampilkan data yang telah ditangkap ke tvLabel dan stringnya digabungkan dengan content_tab_text
         */
        tvLabel.text = getString(R.string.content_tab_text, index)
        /**
         * menampilkan data name pada tvName
         */
        tvName.text = name
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_NAME = "app_name"
    }
}