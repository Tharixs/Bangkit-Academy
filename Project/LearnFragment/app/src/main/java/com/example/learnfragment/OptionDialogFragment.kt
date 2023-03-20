package com.example.learnfragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.learnfragment.databinding.FragmentOptionDialogBinding

class OptionDialogFragment : DialogFragment() {

    private var optionDialogListener: OnOptionDialogListener? = null

    private lateinit var binding: FragmentOptionDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOptionDialogBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnChoose.setOnClickListener {
            val checkRadiobuttonId = binding.rgOptions.checkedRadioButtonId
            if (checkRadiobuttonId != -1) {
                val coach: String? = when (checkRadiobuttonId) {
                    R.id.rb_saf -> binding.rbSaf.text.toString().trim()
                    R.id.rb_mou -> binding.rbMou.text.toString().trim()
                    R.id.rb_lvg -> binding.rbLvg.text.toString().trim()
                    R.id.rb_moyes -> binding.rbMoyes.text.toString().trim()
                    else -> null
                }
                optionDialogListener?.onOptionChosen(coach)
                dialog?.dismiss()
            }
        }
        binding.btnClose.setOnClickListener {
            dialog?.cancel()
        }
    }

    interface OnOptionDialogListener {
        fun onOptionChosen(text: String?)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val fragment = parentFragment

        if (fragment is DetailCategoryFragment) {
            this.optionDialogListener = fragment.optionDialogListener
        }
    }

    override fun onDetach() {
        super.onDetach()
        this.optionDialogListener = null
    }

}