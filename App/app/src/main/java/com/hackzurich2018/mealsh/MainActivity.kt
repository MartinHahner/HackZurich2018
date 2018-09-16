package com.hackzurich2018.mealsh

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.fourmob.datetimepicker.date.DatePickerDialog
import com.sleepbot.datetimepicker.time.TimePickerDialog
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private var userId: Int = 0
    private var location: Location? = null
    private lateinit var adapter: Adapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar).apply {
            setOnClickListener { updateList() }
        })

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { showOfferDialog() }
        userId = intent.getIntExtra("User-ID", -1)

        updateList()

        recyclerView = findViewById(R.id.recyclerView)
        val a = Adapter(listOf())
        adapter = a
        recyclerView.adapter = adapter
    }

    private fun updateList() {
        adapter = Adapter(listOf(
                Meal(0, "17-09-2018T18:30:00.000Z", 235, 28, "Spaghetti",
                        "Grandma style", "Hardbrücke", "Zurich", "8001", 20, "",
                        "Pasta, Tomatosauce", 1, 1, "Martina", "Miller"),
                Meal(1, "17-09-2018T12:30:00.000Z", 235, 28, "Lasagne",
                        "Grandma style", "Centralstation", "Zurich", "8001", 20, "",
                        "Carrots, Pasta, Tomatosauce", 1, 1, "Hans", "Fenster")
        ))
        adapter.listener = {
            showMeal(it)
        }
        recyclerView.adapter = adapter

    }

    private fun showOfferDialog() {
        val view = layoutInflater.inflate(R.layout.new_meal, null)
        val dialog = AlertDialog.Builder(this)
                .setView(view)
                .show()
        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        val etTitle: AppCompatEditText = view.findViewById(R.id.etTitle)
        val etIngredients: AppCompatEditText = view.findViewById(R.id.etIngredients)
        val etDescription: AppCompatEditText = view.findViewById(R.id.etDescription)
        val etMaxPeople: AppCompatEditText = view.findViewById(R.id.etMaxPeople)
        val etLocation: AppCompatEditText = view.findViewById(R.id.etLocation)
        val tvDateAndTime: AppCompatTextView = view.findViewById(R.id.tvDateTime)

        view.findViewById<View>(R.id.btnCancel).setOnClickListener {
            dialog.dismiss()
        }
        view.findViewById<View>(R.id.imgBtnReceipts).setOnClickListener {
            val ingredients = etIngredients.text.toString()
            val receipts = listOf("Receipt 1", "Receipt 2", "Receipt 3", "Receipt 4", "Receipt 5")
            showListDialog(receipts) { receipt ->
                etTitle.setText(receipt)
                etIngredients.setText(receipt)
                etDescription.setText(receipt)
                etMaxPeople.setText(1.toString())
            }
        }
        view.findViewById<View>(R.id.cardDateTime).setOnClickListener {
            showDateTimeDialog(tvDateAndTime.text.toString()) {
                tvDateAndTime.text = it
            }
        }
        view.findViewById<View>(R.id.btnOffer).setOnClickListener {
            if (tvDateAndTime.text.toString() == "")
                return@setOnClickListener
            dialog.dismiss()
            updateList()
        }
    }

    private fun showDateTimeDialog(init: String, listener: (String) -> Unit) {
        val df = DateFormat.getDateTimeInstance()
        val dateTime = Calendar.getInstance()
        try {
            dateTime.time = df.parse(init)
        } catch (ignored: ParseException) {
        }
        val dpd = DatePickerDialog.newInstance({ _, year, month, day ->
            dateTime.set(Calendar.YEAR, year)
            dateTime.set(Calendar.MONTH, month)
            dateTime.set(Calendar.DATE, day)
            val tpd = TimePickerDialog.newInstance({ view, hourOfDay, minute ->
                dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                dateTime.set(Calendar.MINUTE, minute)
                listener(df.format(dateTime.time))
            }, dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), true, false)
            tpd.show(supportFragmentManager, "TimePickerDialog")
        }, dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH), false)
        dpd.show(supportFragmentManager, "DatePickerDialog")
    }

    private fun showMeal(meal: Meal) {
        val view = layoutInflater.inflate(R.layout.show_meal, null)
        val dialog = AlertDialog.Builder(this)
                .setView(view)
                .show()

        val hosttext = """
                Host: ${meal.firstname} ${meal.lastname}
            """.trimIndent()
        val mealtext = """
                Meal: ${meal.title}

                When: ${adapter.df.format(parseFromDB(meal.`when`))}

                Address: ${meal.location_lat}, ${meal.location_lng}

                People: ${meal.max_people}

                Ingredients:
                ${meal.ingredients}

                Description:
                ${meal.description}
        """.trimIndent()

        view.findViewById<AppCompatTextView>(R.id.tvHost).apply {
            text = hosttext
            setOnClickListener { showReviews(meal.iduser.toLong()) }
        }
        view.findViewById<AppCompatTextView>(R.id.tvMeal).text = mealtext

        view.findViewById<View>(R.id.btnBack).setOnClickListener { dialog.dismiss() }
        view.findViewById<View>(R.id.btnParticipate).setOnClickListener {
            AlertDialog.Builder(this).setMessage(getString(R.string.after_participate)).show()
            dialog.dismiss()
        }
    }

    private fun showReviews(hostId: Long) {
        showListDialog(listOf("<Review 1>", "<Review 2>", "<Review 3>", "<Review 4>")) {}
    }

    private fun <T> showListDialog(items: List<T>, onSelect: (T) -> Unit) {
        val dialog = AlertDialog.Builder(this)
                .setItems(items.map { "\n${it.toString()}\n" }.toTypedArray()) { _, which: Int ->
                    onSelect(items[which])
                }
                .setNegativeButton(R.string.cancel, null)
                .show()
        val listView = dialog.listView
        listView.divider = ColorDrawable(Color.parseColor("#88000000"))
        listView.dividerHeight = 2
    }
}

class Adapter(private val meals: List<Meal>) : RecyclerView.Adapter<MealHolder>() {
    val df: DateFormat = DateFormat.getDateTimeInstance()
    var listener: ((Meal) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealHolder {
        return MealHolder(LayoutInflater.from(parent.context).inflate(R.layout.meal, parent, false))
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MealHolder, position: Int) {
        val m = meals[position]
        holder.primary.text = "${m.title} for ${m.max_people}"
        holder.secondary.text = m.location_lat.toString() + ", " + m.location_lng
        holder.datetime.text = df.format(parseFromDB(m.`when`))
        holder.card.setOnClickListener {
            listener?.invoke(m)
        }
        holder.card.setCardBackgroundColor(when (null) {
            0 -> Color.parseColor("#22FFFF00")
            1 -> Color.parseColor("#22FF0000")
            2 -> Color.parseColor("#2200FF00")
            else -> Color.WHITE
        })
    }
}

class MealHolder(view: View) : RecyclerView.ViewHolder(view) {
    val primary: AppCompatTextView = view.findViewById(R.id.primary)
    val secondary: AppCompatTextView = view.findViewById(R.id.secondary)
    val datetime: AppCompatTextView = view.findViewById(R.id.datetime)
    val card: CardView = view.findViewById(R.id.card)
}

fun parseFromDB(s: String): Date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",
        Locale.getDefault()).parse(s.replace("[TZ]".toRegex(), " "))