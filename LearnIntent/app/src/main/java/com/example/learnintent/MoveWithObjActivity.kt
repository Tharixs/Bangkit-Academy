package com.example.learnintent

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MoveWithObjActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_PERSON = "extra_person"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move_with_obj)

        val TextWithObjRecive :TextView = findViewById(R.id.TextObjRecive)

        val person = if(Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra(EXTRA_PERSON,Person::class.java)
        }else{
            @Suppress ("DEPRECATION")
            intent.getParcelableExtra(EXTRA_PERSON)
        }
        if (person != null){
            val text =  """
                Name : ${person.name.toString()},
                Email : ${person.email},
                Age : ${person.age},
                Location : ${person.city},
            """.trimIndent()
            TextWithObjRecive.text = text
        }
    }
}