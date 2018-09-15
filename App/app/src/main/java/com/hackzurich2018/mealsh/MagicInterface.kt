package com.hackzurich2018.mealsh

class MagicInterface {
    data class Receipt(val name: String)

    fun getReceipts(ingredients: String): List<Receipt> {
        return ArrayList()
    }
}