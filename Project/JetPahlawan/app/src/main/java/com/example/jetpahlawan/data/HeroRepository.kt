package com.example.jetpahlawan.data

import com.example.jetpahlawan.model.Hero
import com.example.jetpahlawan.model.HeroesData

class HeroRepository {
    fun getHeroes() : List<Hero>{
        return HeroesData.heroes
    }

    fun searchHeroes(query: String): List<Hero>{
        return HeroesData.heroes.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

}