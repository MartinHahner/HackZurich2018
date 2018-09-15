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
import android.widget.EditText
import com.fourmob.datetimepicker.date.DatePickerDialog
import com.hackzurich2018.mealsh.MagicInterface.Meal
import com.sleepbot.datetimepicker.time.TimePickerDialog
import java.text.DateFormat
import java.text.ParseException
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var magicInterface: MagicInterface
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
        title = "${getString(R.string.app_name)} (${intent.getStringExtra("Email")})"

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { showOfferDialog() }
        userId = intent.getIntExtra("User-ID", -1)

        magicInterface = MagicInterface(userId)

        val mLocationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000,
                    10f, object : LocationListener {
                override fun onLocationChanged(loc: Location) {
                    location = loc
                    updateList()
                }

                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

                override fun onProviderEnabled(provider: String?) {}

                override fun onProviderDisabled(provider: String?) {}
            })
        } else
            throw AssertionError("Permission not granted")

        recyclerView = findViewById(R.id.recyclerView)
        val a = Adapter(listOf())
        adapter = a
        recyclerView.adapter = adapter
    }

    private fun updateList() {
        val location = location
        Thread {
            magicInterface.getMeals(location, userId) { meals ->
                runOnUiThread {
                    adapter = Adapter(meals)
                    adapter.listener = {
                        showMeal(it)
                    }
                    recyclerView.adapter = adapter
                }
            }
        }.start()
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
            val receipts = magicInterface.getReceipts(ingredients)
            showListDialog(receipts) { receipt ->
                etTitle.setText(receipt.name)
                etIngredients.setText(receipt.ingredients)
                etDescription.setText(receipt.description)
                etMaxPeople.setText(receipt.maxPeople.toString())
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
            magicInterface.offerMeal(
                    etTitle.text.toString(),
                    etIngredients.text.toString(),
                    etDescription.text.toString(),
                    etLocation.text.toString(),
                    adapter.df.parse(tvDateAndTime.text.toString()).time,
                    etMaxPeople.text.toString().toIntOrNull() ?: 1
            )
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

    private fun showMeal(meal: MagicInterface.Meal) {
        val view = layoutInflater.inflate(R.layout.show_meal, null)
        val dialog = AlertDialog.Builder(this)
                .setView(view)
                .show()

        val hosttext = """
                Host: ${meal.hostFirstName} ${meal.hostLastName}
                Rating: ${meal.hostRating}/10
            """.trimIndent()
        val mealtext = """
                Meal: ${meal.title}

                When: ${adapter.df.format(meal._when)}

                Address: ${meal.address}

                People participating: ${meal.people}/${meal.maxPeople}

                Ingredients:
                ${meal.ingredients}

                Description:
                ${meal.description}
        """.trimIndent()

        view.findViewById<AppCompatTextView>(R.id.tvHost).apply {
            text = hosttext
            setOnClickListener { showReviews(meal.hostId) }
        }
        view.findViewById<AppCompatTextView>(R.id.tvMeal).text = mealtext

        view.findViewById<View>(R.id.btnBack).setOnClickListener { dialog.dismiss() }
        view.findViewById<View>(R.id.btnParticipate).setOnClickListener {
            AlertDialog.Builder(this).setMessage(getString(R.string.after_participate)).show()
            magicInterface.applyForMeal(userId, meal.idMeal,
                    view.findViewById<EditText>(R.id.etNoteForHost).text.toString())
            dialog.dismiss()
        }
    }

    private fun showReviews(hostId: Long) {
        showListDialog(magicInterface.getReviews(hostId)) {}
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
        holder.primary.text = "${m.title} for ${m.maxPeople}"
        holder.secondary.text = m.address
        holder.datetime.text = df.format(Date(m._when))
        holder.card.setOnClickListener {
            listener?.invoke(m)
        }
        holder.card.setCardBackgroundColor(when (m.state) {
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