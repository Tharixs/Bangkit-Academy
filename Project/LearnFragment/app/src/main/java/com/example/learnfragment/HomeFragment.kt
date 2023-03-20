package com.example.learnfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.commit
import com.example.learnfragment.databinding.FragmentHomeBinding


class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCategory.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
         if (v?.id == R.id.btn_category) {
            val categoryFragment = CategoryFragment()
            val fragmentManager = parentFragmentManager
            fragmentManager.commit {
                replace(R.id.frame_container, categoryFragment, CategoryFragment::class.java.simpleName)
                addToBackStack(null)
            }
        }
    }
}