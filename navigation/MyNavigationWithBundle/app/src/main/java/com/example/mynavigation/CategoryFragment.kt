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
         * ketika btnCategoryLifestyle di clik maka code di dalam akan dijalanlan
         */
        binding.btnCategoryLifestyle.setOnClickListener { view ->
            /**
             * melakukan pengiriman data ke detail category fragment sekaligus berpindah halaman ke detail category
             */
            /**
             * mengirim data menggunakan bundle
             */
            val mBundle = Bundle()
            mBundle.putString(EXTRA_NAME, "Lifestyle")
            mBundle.putLong(EXTRA_STOCK, 7)
            /**
             * berpindah ke halaman detail category fragment
             */
            view.findNavController().navigate(R.id.action_categoryFragment_to_detailCategoryFragment, mBundle)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        val EXTRA_NAME = "extra_name"
        val EXTRA_STOCK = "extra_stock"
    }

}