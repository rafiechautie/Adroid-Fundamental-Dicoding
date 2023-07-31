package com.example.myflexiblefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myflexiblefragment.databinding.FragmentCategoryBinding


class CategoryFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * trigger ketika button "btnDetailCategory" ditekan
         */
        binding.btnDetailCategory.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_detail_category){
            val mDetailCategoryFragment = DetailCategoryFragment()

            //mengirim data antar fragment menggunakan bundle
            val mBundle = Bundle()
            mBundle.putString(DetailCategoryFragment.EXTRA_NAME, "Lifestyle")

            val description = "Kategori Ini Akan Berisi Produk Produk Lifestyle"

            //mengirim data antar fragment menggunakan bundle
            mDetailCategoryFragment.arguments = mBundle
            //mengirim data antar fragment menggunakan setter getter
            mDetailCategoryFragment.description = description

            /**
             * untuk menempelkan sebuah fragment_detail_category pada fragment_categorykita-
             * menggunakan parentFragmentManager
             */
            val mFragmentManager = parentFragmentManager
            mFragmentManager?.beginTransaction()?.apply {
                //mereplace atau menimpa fragmen_home dgn fragment_category
                /**
                 * kita menggunakan method replace dikarenakan kita ingin mengganti fragment yangg sudah ada-
                 * dengan fragment baru. jika sebelumnya tidak ada fragment maka kita bisa menggunakan
                 * method add
                 */
                replace(R.id.frame_container, mDetailCategoryFragment, DetailCategoryFragment::class.java.simpleName)
                //membuat agar ketika ditekan tombol back dia mengarahnya ke halaman sebelumnya,
                /**
                 * jika tidak menambahkan addToBackStack maka nanti ketika tombol button ditekan-
                dia akan menutup aplikasi
                 */
                addToBackStack(null)
                //untuk mengaplikasikan fragment_detail_category harus menambahkan kode commit
                commit()
            }
        }
    }
}