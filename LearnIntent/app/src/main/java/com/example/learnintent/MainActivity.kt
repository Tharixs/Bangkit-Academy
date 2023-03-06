package com.example.learnintent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult : TextView
    private lateinit var buttonNextView: Button
    private lateinit var btnMoveData: Button
    private lateinit var btnMoveWithObj: Button
    private lateinit var btnDialNumber: Button
    private lateinit var btnForResult: Button

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if(result.resultCode == MoveForResultActivity.RESULT_CODE && result.data != null){
            val selectedValue =
                result.data?.getIntExtra(MoveForResultActivity.EXTRA_SELECTED_VALUE,0)
            tvResult.text = "Hasil : $selectedValue"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonNextView = findViewById(R.id.NextView)
        btnMoveData = findViewById(R.id.move_data)
        btnMoveWithObj = findViewById(R.id.move_obj)
        btnDialNumber = findViewById(R.id.btn_dial)
        btnForResult  = findViewById(R.id.btn_for_result)
        tvResult  = findViewById(R.id.tv_result)


        buttonNextView.setOnClickListener {
            startActivity(Intent(this@MainActivity, NextView::class.java))
        }

        btnMoveData.setOnClickListener {
            val intent = Intent(this@MainActivity, MoveActivity::class.java)
            intent.putExtra(MoveActivity.EXTRA_NAME, "Tharixs Akbar Ibnu Azis")
            intent.putExtra(MoveActivity.EXTRA_AGE, 21)
            startActivity(intent)
        }

        btnMoveWithObj.setOnClickListener {
            val person = Person(
                "Tharixs Akbar Ibnu Azis",
                20,
                "tharixsakbar@gmail.com",
                "Jember"
            )
            val intent = Intent(this@MainActivity, MoveWithObjActivity::class.java)
            intent.putExtra(MoveWithObjActivity.EXTRA_PERSON, person)
            startActivity(intent)
        }

        btnDialNumber.setOnClickListener {
            val phoneNumber = "081235686798"
             startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber")))
        }

        btnForResult.setOnClickListener {
            resultLauncher.launch(Intent(Intent(this@MainActivity, MoveForResultActivity::class.java)))
        }


    }
}