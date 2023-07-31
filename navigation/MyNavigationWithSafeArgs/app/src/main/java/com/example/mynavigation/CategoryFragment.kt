package com.example.mynavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.mynavigation.databinding.FragmentCategoryBinding


class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * ketika btnCategoryLifestyle di click maka code di dalamnya akan dijalankan
         */
        binding.btnCategoryLifestyle.setOnClickListener { view ->
            /**
             * melakukan pengiriman data menggunakan safeArgs
             */
            val toDetailCategoryFragment = CategoryFragmentDirections.actionCategoryFragmentToDetailCategoryFragment()
            toDetailCategoryFragment.name = "Lifestyle"
            toDetailCategoryFragment.stock = 7
            /**
             * mengarahkan ke halaman detail category fragment
             */
            view.findNavController().navigate(toDetailCategoryFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}