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

        //mengambil data dari sebuah bundle
        if (arguments != null){
            val categoryName = arguments?.getString(EXTRA_NAME)
            binding.tvCategoryName.text = categoryName
            binding.tvCategoryDesc.text = description
        }

        binding.btnShowDialog.setOnClickListener(this)
        binding.btnProfile.setOnClickListener{
            val mIntent = Intent(requireActivity(), ProfileActivity::class.java)
            startActivity(mIntent)
        }

    }

    companion object{
        var EXTRA_NAME = "extra_name"
        var EXTRA_DESCRIPTION = "extra_description"
    }

    override fun onClick(view: View) {
        val mOptionDialogFragment =OptionDialogFragment()

        val mFragmentManager = childFragmentManager
        mOptionDialogFragment.show(mFragmentManager, OptionDialogFragment::class.java.simpleName)
    }

    internal var optionDialogListener: OptionDialogFragment.OnOptionDialogListener = object : OptionDialogFragment.OnOptionDialogListener{
        override fun onOptionChosen(text: String?) {
            Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show()
        }
    }

}