package com.hackzurich2018.mealsh

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatEditText
import android.view.View
import android.view.WindowManager

class MainActivity : AppCompatActivity() {
    lateinit var magicInterface: MagicInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { showIngredientsDialog() }

        magicInterface = MagicInterface()
    }

    private fun showIngredientsDialog() {
        val view = layoutInflater.inflate(R.layout.new_meal, null)
        val dialog = AlertDialog.Builder(this)
                .setView(view)
                .show()
        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        val etIngredients: AppCompatEditText = view.findViewById(R.id.etIngredients)
        view.findViewById<View>(R.id.btnCancel).setOnClickListener {
            dialog.dismiss()
        }
        view.findViewById<View>(R.id.btnFindReceipts).setOnClickListener {
            val ingredients = etIngredients.text.toString()
            magicInterface.getReceipts(ingredients)
        }
    }
}