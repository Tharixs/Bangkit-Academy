package com.example.storyapp.view.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText

class MyEmailEdText : AppCompatEditText {

    private var errorMessage: String? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        inputType = android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
    }

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
        val email = text.toString()
        if (email.isNotEmpty() && !isValidEmail(email)) {
            error = errorMessage
        } else {
            error = null
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}