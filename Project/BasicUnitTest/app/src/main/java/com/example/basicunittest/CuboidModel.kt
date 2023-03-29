package com.example.basicunittest

class CuboidModel {

    private var with = 0.0
    private var length = 0.0
    private var height = 0.0


    fun getVolume(): Double = with * height * length

    fun getSurfaceArea(): Double = 2 * (with * length + length * height + height * with)

    fun getCircumference(): Double = 4 * (with + length + height)

    fun save(with: Double, length: Double, height: Double) {
        this.with = with
        this.length = length
        this.height = height
    }

}