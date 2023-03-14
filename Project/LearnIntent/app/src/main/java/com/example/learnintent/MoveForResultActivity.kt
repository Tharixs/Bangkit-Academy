package com.example.learnintent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup

class MoveForResultActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_SELECTED_VALUE = "extra_selected_value"
        const val RESULT_CODE = 110
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move_for_result)

        val btnMoveResult : Button = findViewById(R.id.btn_choose)
        val rgNumber : RadioGroup = findViewById(R.id.rg_number)

        btnMoveResult.setOnClickListener {
            if (rgNumber.checkedRadioButtonId > 0 ){
                var value = 0
                when(rgNumber.checkedRadioButtonId){
                    R.id.rb_50 -> value = 50
                    R.id.rb_100 -> value = 100
                    R.id.rb_150 -> value = 150
                    R.id.rb_200 -> value = 200
                }
                val intent = Intent()
                intent.putExtra(EXTRA_SELECTED_VALUE,value)
                setResult(RESULT_CODE, intent)
                finish()
            }
        }
    }
}