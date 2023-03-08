package com.example.learnfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView


class HomeFragment : Fragment(), View.OnClickListener {
    lateinit var btnCategory : Button
    lateinit var tvTitle : TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnCategory = view.findViewById(R.id.btn_category)
        btnCategory.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        if(v?.id == R.id.btn_category){
            val mCategoryFragment = CategoryFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frame_container, mCategoryFragment, CategoryFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
    }
}