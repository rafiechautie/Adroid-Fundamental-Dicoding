package com.example.myflexiblefragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.myflexiblefragment.databinding.FragmentDetailCategoryBinding


class DetailCategoryFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDetailCategoryBinding? = null
    private val binding get() = _binding!!

    var description: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (savedInstanceState != null){
            val descFromBundle = savedInstanceState.getString(EXTRA_DESCRIPTION)
            description = descFromBundle
        }

        /**
         * mengambil data dari sebuah bundle
         *
         * Kelas Bundle merupakan kelas map data string untuk obyek-obyek parcelable.
         * Di sini kita bisa menginput lebih dari satu parameter/variabel ke dalam obyek Bundle.
         */
        if (arguments != null){
            val categoryName = arguments?.getString(EXTRA_NAME)
            binding.tvCategoryName.text = categoryName
            binding.tvCategoryDesc.text = description
        }

        binding.btnShowDialog.setOnClickListener(this)
        binding.btnProfile.setOnClickListener{
            /**
             * mengarhkan ke halaman profile activity dari sebuah fragment
             */
            val mIntent = Intent(requireActivity(), ProfileActivity::class.java)
            startActivity(mIntent)
        }

    }

    companion object{
        var EXTRA_NAME = "extra_name"
        var EXTRA_DESCRIPTION = "extra_description"
    }

    override fun onClick(view: View) {

        if (view.id == R.id.btn_show_dialog){
            /**
             * kode dibawah untuk menampilkan sebuah dialog ketika button "btnShowDialog" ditekan
             */
            val mOptionDialogFragment =OptionDialogFragment()

            /**
             * Kita tidak menggunakan getParentFragmentManager() saat ini. Alih- alih,
             * getChildFragmentManager() merupakan pilihan yang tepat untuk kondisi saat ini,
             * yakni sebuah nested fragment / fragment bersarang.
             * Karena OptionDialogFragment dipanggil di dalam sebuah obyek fragment yang
             * telah ada sebelumnya yaitu DetailCategoryFragment, maka demi performa lebih baik,
             * gunakanlah getChildFragmentManager() sebagai pilihan yang lebih tepat.
             */
            val mFragmentManager = childFragmentManager
            /**
             * baris di bawah ini untuk nenampilkan obyek OptionDialogFragment ke layar
             */
            mOptionDialogFragment.show(mFragmentManager, OptionDialogFragment::class.java.simpleName)
        }
    }

    /**
     * variabel optionDialogListener implement dengan interface OnOptionDialogListener pada class OptionDialogFragment
     */
    internal var optionDialogListener: OptionDialogFragment.OnOptionDialogListener = object : OptionDialogFragment.OnOptionDialogListener{
        /**
         * karna dia implement dgn interface OnOptionDialogListener maka method di dalam interface harus di override
         * atau dituliskan ulang
         */
        override fun onOptionChosen(text: String?) {
            /**
             * disini data variable coach pada OptionDialogFragment dimasukkan ke variable text lalu ditampilkan
             * menggunakan Toast
             */
            Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show()
        }
    }

}