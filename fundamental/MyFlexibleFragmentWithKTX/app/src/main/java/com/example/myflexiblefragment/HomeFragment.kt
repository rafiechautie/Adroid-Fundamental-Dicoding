package com.example.myflexiblefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import com.example.myflexiblefragment.databinding.FragmentHomeBinding


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

        binding.btnCategory.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val mCategoryFragment = CategoryFragment()
        val mFragmentManager = parentFragmentManager
        mFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.frame_container, mCategoryFragment, CategoryFragment::class.java.simpleName)
        }
    }

}