package com.example.myflexiblefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myflexiblefragment.databinding.FragmentHomeBinding


/**
 * ini fragment yang akan ditempelkan pada mainActivity pertama kali
 */
class HomeFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * trigger ketika button "btnCategory ditekan"
         */
        binding.btnCategory.setOnClickListener(this)
    }

    override fun onClick(view: View) {

        /**
         * ketika button "btnCategory" ditekan maka dia akan menanamkan fragment_category
         */
        if (view.id == R.id.btn_category){
            val mCategoryFragment = CategoryFragment()

            /**
             * untuk menempelkan sebuah fragment_category pada fragment_home kita-
             * menggunakan parentFragmentManager
             */
            val mFragmentManager = parentFragmentManager

            mFragmentManager.beginTransaction().apply {
                //mereplace atau menimpa fragmen_home dgn fragment_category
                /**
                 * kita menggunakan method replace dikarenakan kita ingin mengganti fragment yangg sudah ada-
                 * dengan fragment baru. jika sebelumnya tidak ada fragment maka kita bisa menggunakan
                 * method add
                 */
                replace(R.id.frame_container, mCategoryFragment, CategoryFragment::class.java.simpleName)
                //membuat agar ketika ditekan tombol back dia mengarahnya ke halaman sebelumnya,
                /**
                 * jika tidak menambahkan addToBackStack maka nanti ketika tombol button ditekan-
                   dia akan menutup aplikasi
                 */
                addToBackStack(null)
                //untuk mengaplikasikan fragment_category harus menambahkan kode commit
                commit()
            }
        }
    }

}