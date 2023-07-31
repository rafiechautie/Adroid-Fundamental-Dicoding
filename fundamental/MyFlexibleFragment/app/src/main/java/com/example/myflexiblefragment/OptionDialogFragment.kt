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

/**
 * membuat sebuah dialog yang nantinya akan tampil ketika button di fragment_detail_category ditekan
 *
 * disini class OptionDialogFragment inherit dengan class DialogFragment sehingga objek fragment-
 * akan tampil mengambang di layar
 */
class OptionDialogFragment : DialogFragment(), View.OnClickListener {

    private var _binding: FragmentOptionDialogBinding? = null
    private val binding get() = _binding!!

    /**
     * membuat sebuah variable yang implement dgn interface OnOptionDialogListener
     */
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
                /**
                 * value yang dipilih pada radio button dikirim ke method onOptionChoosen pada
                 * interface OnOptionDialogListener
                 */
                optionDialogListener?.onOptionChosen(coach)
                dialog?.dismiss()
            }
        }else if (view.id == R.id.btn_close){
            /**
             * kode ini akan menutup dialog ketika button "btn_close" ditekan
             */
            dialog?.cancel()
        }

    }

    interface OnOptionDialogListener{
        /**
         * method dibawah ini digunakan untuk menangkap value yang dipilih dari radio button
         */
        fun onOptionChosen(text: String?)
    }

    /**
     * function onAttach digunakan untuk mengelola optionDialogListener ketika fragment
     * dipanggil dan dimatikan
     *
     * attach fungsinya untuk re-attach sebuah fragment setelah sebelumnya di dettach dari UI
     * itu menyebebkan hirarki dibuat ulang, ditampilkan ke ui
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)

        /**
         * untuk menempelkan fragment dialog ke DetailCategoryFragment
         */
        val fragment = parentFragment

        if (fragment is DetailCategoryFragment){
            this.optionDialogListener = fragment.optionDialogListener
        }
    }

    /**
     * detach akan menghancurkan fragment dialog tapi state akan tetap disimpan di fragment manager
     */
    override fun onDetach() {
        super.onDetach()
        this.optionDialogListener = null
    }




}