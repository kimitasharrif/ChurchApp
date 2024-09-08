package com.sherrif.churchapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.radiobutton.MaterialRadioButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.google.gson.GsonBuilder
import com.sherrif.churchapp.constants.Constants
import com.sherrif.churchapp.helpers.ApiHelper
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/// declare global
private lateinit var buttonDatePicker: MaterialButton
private lateinit var editTextDate: EditText
private lateinit var selectedLocation: TextView
private lateinit var login: MaterialTextView

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // fetch the date
        editTextDate = findViewById(R.id.dateedittext)

        //FETCH THE BUTTON FOR DATE PICKER
        buttonDatePicker = findViewById(R.id.dob)
        //set on click listener for the button
        buttonDatePicker.setOnClickListener {
            //create a function to show date picker dialog
            showDatePickerDialog()
        }// end of date picker
        // fetch the spinner and textview
        login = findViewById(R.id.linktologin)

        login.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }
        val surname = findViewById<TextInputEditText>(R.id.surname)
        val others = findViewById<TextInputEditText>(R.id.others)
        val phone = findViewById<TextInputEditText>(R.id.phone)
        val password = findViewById<TextInputEditText>(R.id.password)
        val confirm = findViewById<TextInputEditText>(R.id.confirm)
        //outline red find layout id
        val surnameInputLayout = findViewById<TextInputLayout>(R.id.surname_input_layout)
        val othersInputLayout = findViewById<TextInputLayout>(R.id.others_input_layout)
        val phoneInputLayout = findViewById<TextInputLayout>(R.id.phone_input_layout)
        val passwordInputLayout = findViewById<TextInputLayout>(R.id.password_input_layout)
        val confirmInputLayout = findViewById<TextInputLayout>(R.id.confirm_input_layout)
        val choosegender = findViewById<RadioGroup>(R.id.gendergroup)
        val choosedob = findViewById<EditText>(R.id.dateedittext)




        //signup member
        val btncreate = findViewById<MaterialButton>(R.id.create)
        btncreate.setOnClickListener {
            var isValid = true

            if (surname.text.isNullOrEmpty()) {
                surnameInputLayout.error = "Please insert your surname"
                surnameInputLayout.setBoxStrokeColorStateList(
                    resources.getColorStateList(R.color.error_red, theme)
                )
                isValid = false
            } else {
                surnameInputLayout.error = null
                surnameInputLayout.setBoxStrokeColorStateList(
                    resources.getColorStateList(R.color.default_stroke, theme)
                )
            }
            if (others.text.isNullOrEmpty()) {
                othersInputLayout.error = "Please enter your other name"
                othersInputLayout.setBoxStrokeColorStateList(
                    resources.getColorStateList(R.color.error_red, theme)
                )
                isValid = false
            } else {
                othersInputLayout.error = null
                othersInputLayout.setBoxStrokeColorStateList(
                    resources.getColorStateList(R.color.default_stroke, theme)
                )
            }

            if (phone.text.isNullOrEmpty()) {
                phoneInputLayout.error = "Please insert your phone number"
                phoneInputLayout.setBoxStrokeColorStateList(
                    resources.getColorStateList(R.color.error_red, theme)
                )
                isValid = false
            } else {
                phoneInputLayout.error = null
                phoneInputLayout.setBoxStrokeColorStateList(
                    resources.getColorStateList(R.color.default_stroke, theme)
                )
            }

            if (password.text.isNullOrEmpty()) {
                passwordInputLayout.error = "Please insert your password"
                passwordInputLayout.setBoxStrokeColorStateList(
                    resources.getColorStateList(R.color.error_red, theme)
                )
                isValid = false
            } else {
                passwordInputLayout.error = null
                passwordInputLayout.setBoxStrokeColorStateList(
                    resources.getColorStateList(R.color.default_stroke, theme)
                )
            }
            if (confirm.text.isNullOrEmpty()) {
                confirmInputLayout.error = "Please insert your confirm password"
                confirmInputLayout.setBoxStrokeColorStateList(
                    resources.getColorStateList(R.color.error_red, theme)
                )
                isValid = false
            } else {
                confirmInputLayout.error = null
                confirmInputLayout.setBoxStrokeColorStateList(
                    resources.getColorStateList(R.color.default_stroke, theme)
                )
            }
            if (choosegender.checkedRadioButtonId == -1) {
                choosegender.setBackgroundResource(R.drawable.error_border)
                isValid = false
            } else {
                choosegender.setBackgroundResource(R.drawable.default_border)
            }
            if (choosedob.text.isNullOrEmpty()) {
                choosedob.backgroundTintList = resources.getColorStateList(R.color.error_red, theme)
                isValid = false
            } else {
                choosedob.backgroundTintList = resources.getColorStateList(R.color.default_stroke, theme)
            }


            if (isValid) {
                val surname = findViewById<TextInputEditText>(R.id.surname)
                val others = findViewById<TextInputEditText>(R.id.others)
                val phone = findViewById<TextInputEditText>(R.id.phone)
                val password = findViewById<TextInputEditText>(R.id.password)
                val confirm = findViewById<TextInputEditText>(R.id.confirm)
                val female = findViewById<MaterialRadioButton>(R.id.radiofemale)
                val male = findViewById<MaterialRadioButton>(R.id.radiomale)


//            check selected gender
                var gender = "N/A"
                if (female.isChecked) {
                    gender = "Female"
                } else {
                    gender = "Male"
                }
                val uppercase = Regex(".*[A-Z].*")
                val lowercase = Regex("[a-z]")
                val digits = Regex(".*\\d.*")
                val specialcharacter = Regex(".*[!@#\$%^&*(),.?\":{}|<>].*")


                // check if password match
                if (password.text.toString() != confirm.text.toString()) {
                    // password don't match
                    Toast.makeText(applicationContext, "Password don`t match", Toast.LENGTH_SHORT)
                        .show()
                } else if (password.length() < 8) {
                    // password do
                    Toast.makeText(
                        applicationContext,
                        "Password must be atleast eight characters",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (!uppercase.containsMatchIn(password.text.toString())) {
                    Toast.makeText(
                        applicationContext,
                        "Password must contain capital letter",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (!lowercase.containsMatchIn(password.text.toString())) {
                    Toast.makeText(
                        applicationContext,
                        "Password must contain a small letter",
                        Toast.LENGTH_SHORT
                    ).show()

                } else if (!digits.containsMatchIn(password.text.toString())) {
                    Toast.makeText(
                        applicationContext,
                        "Password must contain a digit/number",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (!specialcharacter.containsMatchIn(password.text.toString())) {
                    Toast.makeText(
                        applicationContext,
                        "Password must contain a special character/symbol",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    val helper = ApiHelper(applicationContext)
                    val api = Constants.BASE_URL + "/membersignup"
//                create a json object
                    val body = JSONObject()
                    body.put("surname", surname.text.toString())
                    body.put("others", others.text.toString())
                    body.put("phone", phone.text.toString())
                    body.put("dob", editTextDate.text.toString())
                    body.put("password", password.text.toString())
                    body.put("gender", gender)
                    body.put("status", "TRUE")


                    helper.post(api, body, object : ApiHelper.CallBack {
                        override fun onSuccess(result: JSONArray?) {

                        }

                        override fun onSuccess(result: JSONObject?) {
                            // posted successifully
                            Toast.makeText(
                                applicationContext,
                                result.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onFailure(result: String?) {
                            // failed to post
                            Toast.makeText(
                                applicationContext,
                                result.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })// end of on success


                }


            }
        }
    }
    private fun showDatePickerDialog() {
        // create an object of calendar
        val calendar = Calendar.getInstance()
        // create a date picker dialog and set the current date as the default selection
        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, year: Int, month: Int, day: Int ->
                val selectedDate = formatDate(year, month, day)
//                Toast.makeText(applicationContext, "$selectedDate", Toast.LENGTH_SHORT).show()
                editTextDate.setText(selectedDate)

            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        //PREVENT under i8 from registering
//        show date picker dialog, -2007
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - 567648000000
        datePickerDialog.show()
    }

    private fun formatDate(year: Int, month: Int, day: Int): String {
        //date conversion
        //get an instance/object of a calendar
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
//        format date
        val dateformat = SimpleDateFormat("YYYY-MM-dd", Locale.getDefault())
        return dateformat.format(calendar.time)
    }
}