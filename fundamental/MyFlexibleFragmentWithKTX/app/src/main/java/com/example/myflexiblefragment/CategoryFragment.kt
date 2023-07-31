package com.example.myflexiblefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
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

            val mFragmentManager = parentFragmentManager
            mFragmentManager.commit {
                addToBackStack(null)
                replace(R.id.frame_container, mDetailCategoryFragment, DetailCategoryFragment::class.java.simpleName)
            }
        }
    }
}