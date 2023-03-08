package com.example.learnfragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class DetileCategoryFragment : Fragment() {

    lateinit var tvTitle : TextView
    lateinit var tvDesk : TextView
    lateinit var btnToProfile : Button
    lateinit var btnShowDialog : Button

    var description : String? = null

    companion object{
        var EXTRA_NAME = "extra_name"
        var EXTRA_DESCRIPTION = "extra_desk"
    }

     internal var optionDialogListener: OptionalDialogFragment.OnOptionDialogListener = object : OptionalDialogFragment.OnOptionDialogListener {
         override fun onOptionChoosen(text: String?) {
            Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
         }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detile_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTitle = view.findViewById(R.id.tv_title)
        tvDesk = view.findViewById(R.id.tv_desk)
        btnToProfile = view.findViewById(R.id.to_profile)
        btnShowDialog = view.findViewById(R.id.show_dialog)

        if (savedInstanceState != null) {
            val descFromBundle = savedInstanceState.getString(EXTRA_DESCRIPTION)
            description = descFromBundle
        }
        if (arguments != null){
            val categoryName = arguments?.getString(EXTRA_NAME)
            tvTitle.text = categoryName
            tvDesk.text = description
        }

      btnShowDialog.setOnClickListener{
          val mOptionDialogFragment = OptionalDialogFragment()

          val mFragmentManager = childFragmentManager
          mOptionDialogFragment.show(mFragmentManager, OptionalDialogFragment::class.java.simpleName)
      }
        btnToProfile.setOnClickListener {
            val intent = Intent(requireActivity(), ProfileActivity::class.java)
            startActivity(intent)
        }
    }

}