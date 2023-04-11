package com.example.mycustomview.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.mycustomview.R

class MyEdText : AppCompatEditText, View.OnTouchListener {

    private lateinit var clearBtnImg: Drawable

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

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        hint = "Masukkan nama Anda"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {
        clearBtnImg = ContextCompat.getDrawable(context, R.drawable.baseline_close_24) as Drawable
        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().isNotEmpty()) showClearBtn() else hidenClearButton()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (compoundDrawables[2] != null) {
            val clearBtnStart: Float
            val clearBtnEnd: Float
            var isClearBtnClicked = false

            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                clearBtnEnd = (clearBtnImg.intrinsicWidth + paddingStart).toFloat()
                when {
                    event?.x!! < clearBtnEnd -> isClearBtnClicked = true
                }
            } else {
                clearBtnStart = (width - paddingEnd - clearBtnImg.intrinsicWidth).toFloat()
                when {
                    event?.x!! > clearBtnStart -> isClearBtnClicked = true
                }
            }
            if (isClearBtnClicked) {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        clearBtnImg = ContextCompat.getDrawable(
                            context,
                            R.drawable.baseline_close_24
                        ) as Drawable
                        showClearBtn()
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        clearBtnImg = ContextCompat.getDrawable(
                            context,
                            R.drawable.baseline_close_24
                        ) as Drawable
                        when {
                            text != null -> text?.clear()
                        }
                        hidenClearButton()
                        return true
                    }
                    else -> return false
                }
            } else return false
        }
        return false
    }

    private fun showClearBtn() {
        setButtonDrawables(endOfTheText = clearBtnImg)
    }

    private fun hidenClearButton() {
        setButtonDrawables()
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null

    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }


}