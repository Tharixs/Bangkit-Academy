package com.example.learnfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class CategoryFragment : Fragment(), View.OnClickListener {
    lateinit var btnDetileCategory : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnDetileCategory = view.findViewById(R.id.btn_detile_category)
        btnDetileCategory.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_detile_category){
            val mDetileCategoryF = DetileCategoryFragment()

            val mBundle = Bundle()
            mBundle.putString(DetileCategoryFragment.EXTRA_NAME, "Lifestyle")
            val description = "Kategori ini akan berisi tentang produk Lifestyle"

            mDetileCategoryF.arguments = mBundle
            mDetileCategoryF.description = description

            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frame_container, mDetileCategoryF, DetileCategoryFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
    }
}