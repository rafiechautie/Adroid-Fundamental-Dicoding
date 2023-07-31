package com.example.myflexiblefragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.myflexiblefragment.databinding.FragmentOptionDialogBinding


class OptionDialogFragment : DialogFragment(), View.OnClickListener {

    private var _binding: FragmentOptionDialogBinding? = null
    private val binding get() = _binding!!

    private var optionDialogListener: OnOptionDialogListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOptionDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnChoose.setOnClickListener(this)
        binding.btnClose.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_choose){
            //jika salah satu radio button ada yang di click
            if (binding.rgOptions.checkedRadioButtonId != -1){
                var coach: String? = when(binding.rgOptions.checkedRadioButtonId){
                    R.id.rb_saf -> binding.rbSaf.text.toString().trim()
                    R.id.rb_mou -> binding.rbMou.text.toString().trim()
                    R.id.rb_lvg -> binding.rbLvg.text.toString().trim()
                    R.id.rb_moyes -> binding.rbMoyes.text.toString().trim()
                    else -> null
                }
                optionDialogListener?.onOptionChosen(coach)
                dialog?.dismiss()
            }
        }else if (view.id == R.id.btn_close){
            dialog?.cancel()
        }

    }

    interface OnOptionDialogListener{
        fun onOptionChosen(text: String?)
    }

    /**
     * function onAttach digunakan untuk mengelola optionDialogListener ketika fragment
     * dipanggil dan dimatikan
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)

        val fragment = parentFragment

        if (fragment is DetailCategoryFragment){
            this.optionDialogListener = fragment.optionDialogListener
        }
    }

    override fun onDetach() {
        super.onDetach()
        this.optionDialogListener = null
    }




}