package com.hackzurich2018.mealsh

data class Meal(
        val idmeal: Int,
        val `when`: String,
        val location_lat: Int,
        val location_lng: Int,
        val title: String,
        val description: String,
        val address: String,
        val city: String,
        val zip: String,
        val max_people: Int,
        val co2_score: String,
        val ingredients: String = "",
        val cooked_by: Int,
        val iduser: Int,
        val firstname: String,
        val lastname: String
)