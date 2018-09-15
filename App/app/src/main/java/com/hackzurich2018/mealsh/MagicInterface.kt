package com.hackzurich2018.mealsh

import android.location.Location
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*

class MagicInterface(val userId: Int) {
    data class Receipt(val name: String, val ingredients: String, val description: String, val maxPeople: Int) {
        override fun toString(): String {
            return "$name for $maxPeople people\n\n$ingredients\n\n$description"
        }
    }

    data class Review(val rating: Long, val text: String)

    data class Meal(val idMeal: Long, val _when: Long, val title: String,
                    val description: String, val address: String, val maxPeople: Int,
                    val co2score: String, val ingredients: String, val hostRating: Double,
                    val hostFirstName: String, val hostLastName: String, val people: Int,
                    val hostId: Long, val state: Int?)


    fun getMeals(location: Location?, userId: Int, callback: (List<Meal>) -> Unit) {
        val urlString = "http://melsh.scapp.io/meal"
        val url = URL(urlString)
        val conn = url.openConnection()
        val inputStream = conn.getInputStream()
        val br = BufferedReader(InputStreamReader(inputStream))
        System.out.println(br.readLine())
    }

    fun getReceipts(ingredients: String): List<Receipt> {
        return listOf(
                Receipt("Spaghetti", "500g Spaghetti\n5 Tomato\nSalt\nPepper\nOlive oil", "Cook the Spaghetti\nMake the sauce\neat it", 6),
                Receipt("Spaghetti", "500g Spaghetti\n5 Tomato\nSalt\nPepper\nOlive oil", "Cook the Spaghetti\nMake the sauce\neat it", 4)
        )
    }

    fun offerMeal(title: String, ingredients: String, description: String,
                  location: String, dateTime: Long, maxPeople: Int) {
        Thread {
            val `when` = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date(dateTime))

            val urlParameters: String = """
            when=$`when`
            title=$title&
            description=$description$
            address=$location&
            city=&
            zip=&
            max_people=$maxPeople&
            co2_score=${(Math.random() * 10)}&
            ingredients=$ingredients
        """.trimIndent().replace("\n", "")

            val postData = urlParameters.toByteArray(Charsets.UTF_8)
            val postDataLength = postData.size
            val request = "http://example.com/index.php"
            val url = URL(request)
            val conn = url.openConnection() as HttpURLConnection
            conn.doOutput = true
            conn.instanceFollowRedirects = false
            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            conn.setRequestProperty("charset", "utf-8")
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength))
            conn.useCaches = false
            DataOutputStream(conn.outputStream).use { wr -> wr.write(postData) }
        }.start()
    }

    fun getReviews(hostId: Long): List<Review> {
        return listOf(
                Review(9, "High quality food and good mood"),
                Review(6, "Okay since it was for free"),
                Review(10, "Best pizza in country"),
                Review(8, "Good and simple")
        )
    }

    fun applyForMeal(userId: Int, idMeal: Long, message: String) {
    }
}