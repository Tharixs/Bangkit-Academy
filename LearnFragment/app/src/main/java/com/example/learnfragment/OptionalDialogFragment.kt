package com.example.learnfragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment

class OptionalDialogFragment : DialogFragment() {

    lateinit var rbSir: RadioButton
    lateinit var rbJos: RadioButton
    lateinit var rbLou: RadioButton
    lateinit var rbDav: RadioButton
    lateinit var bClose: Button
    lateinit var bChoose: Button
    lateinit var rbGroup: RadioGroup
    private var optionDialogListener: OnOptionDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_optional_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rbSir = view.findViewById(R.id.rb_sir)
        rbJos = view.findViewById(R.id.rb_jos)
        rbLou = view.findViewById(R.id.rb_lou)
        rbDav = view.findViewById(R.id.rb_dav)
        bClose = view.findViewById(R.id.btn_close)
        bChoose = view.findViewById(R.id.btn_choose)
        rbGroup = view.findViewById(R.id.rb_group)

        bChoose.setOnClickListener {
            val checkRbid = rbGroup.checkedRadioButtonId
            if (checkRbid != -1) {
                var coach: String? = when (checkRbid) {
                    R.id.rb_sir -> rbSir.text.toString().trim()
                    R.id.rb_lou -> rbLou.text.toString().trim()
                    R.id.rb_jos -> rbJos.text.toString().trim()
                    R.id.rb_dav -> rbDav.text.toString().trim()
                    else -> null
                }
                optionDialogListener?.onOptionChoosen(coach)
                dialog?.dismiss()
            }
        }
        bClose.setOnClickListener {
            dialog?.cancel()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val fragment = parentFragment

        if (fragment is DetileCategoryFragment) {
            this.optionDialogListener = fragment.optionDialogListener
        }
    }

    override fun onDetach() {
        super.onDetach()
        this.optionDialogListener = null
    }

    interface OnOptionDialogListener {
        fun onOptionChoosen(text: String?)
    }
}

