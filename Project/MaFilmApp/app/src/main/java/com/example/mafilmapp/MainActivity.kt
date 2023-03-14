package com.example.mafilmapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvHero: RecyclerView
    private val list = ArrayList<Hero>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvHero = findViewById(R.id.rv_hero)
        rvHero.setHasFixedSize(true)

        list.addAll(listHeroes)
        showRecyclerList()
    }

    private val listHeroes: ArrayList<Hero>
        get() {
            val dataTitle = resources.getStringArray(R.array.data_title)
            val dataDesk = resources.getStringArray(R.array.data_desk)
            val dataPhoto = resources.getStringArray(R.array.data_photo)
            val dataStar = resources.getStringArray(R.array.data_star)
            val dataTime = resources.getStringArray(R.array.data_time)
            val dataSutradara = resources.getStringArray(R.array.data_sutradara)
            val dataSinopsis = resources.getStringArray(R.array.data_sinopsis)
            val dataRilis = resources.getStringArray(R.array.data_tanggal_rilis)

            val listHero = ArrayList<Hero>()

            for (i in dataTitle.indices) {
                val hero = Hero(
                    dataTitle[i], dataDesk[i],
                    dataPhoto[i], dataStar[i],dataTime[i] ,dataSutradara[i],dataSinopsis[i],  dataRilis[i]
                )
                listHero.add(hero)
            }
            return listHero
        }

    private fun showRecyclerList() {
        rvHero.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = ListHeroAdapter(list)
        rvHero.adapter = listHeroAdapter

        listHeroAdapter.setOnItemClickCallback(object : ListHeroAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Hero) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("DATA", data)
                startActivity(intent)
                showSelectedHero(data)
            }
        })
    }

    private fun showSelectedHero(hero: Hero) {
        Toast.makeText(this,  hero.name, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_list -> rvHero.layoutManager = LinearLayoutManager(this)
            R.id.action_grid -> rvHero.layoutManager = GridLayoutManager(this, 2)
            R.id.about_page -> startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

}