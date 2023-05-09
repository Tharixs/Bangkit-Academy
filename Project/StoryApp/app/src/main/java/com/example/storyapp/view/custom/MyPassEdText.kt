package com.example.storyapp.view.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatEditText

class MyPassEdText : AppCompatEditText {
    private var errorMessage: String? = null
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun setErrorMessage(errorMessage: String) {
        this.errorMessage = errorMessage
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        val pass = text.toString()
        error = if (pass.isNotEmpty() && !isPassValid(pass)) {
            errorMessage
        } else {
            null
        }
    }

    private fun isPassValid(pass: String): Boolean {
        return pass.length >= 8
    }

}