package com.example.colossustex.homePage


import android.app.Dialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.example.colossustex.R
import com.google.android.material.textfield.TextInputLayout


class HomePage : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private lateinit var mDialog1: Dialog
    private lateinit var mDialog2: Dialog

    private lateinit var viewModel: HomePageViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val lay = inflater.inflate(R.layout.home_page_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(HomePageViewModel::class.java)

        mDialog1 = Dialog(context!!)             //Used for showing Dialog
        mDialog2 = Dialog(context!!)             //Used for showing Dialog


        recyclerView = lay.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        val options = FirebaseRecyclerOptions.Builder<Item>()
            .setQuery(FirebaseDatabase.getInstance().reference.child("Item"), Item::class.java)
            .build()
        adapter = ItemAdapter(options)
        recyclerView.adapter = adapter

        val toolbar = lay.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.main_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.notification_Settings -> notificationSetting()
                R.id.edit_profile -> modifyProfile()
                R.id.change_password -> Toast.makeText(
                    context,
                    "Change Password",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.spinning_mill -> Toast.makeText(
                    context,
                    "Spinning mill?",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.support -> Toast.makeText(context, "Support", Toast.LENGTH_SHORT).show()
                R.id.advertise_with_us -> Toast.makeText(
                    context,
                    "Advertise With Us",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.rate_this_app -> Toast.makeText(
                    context,
                    "Rate This App",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.share_app -> Toast.makeText(context, "Share App", Toast.LENGTH_SHORT).show()
            }
            true
        }



        return lay
    }


    private fun notificationSetting() {                     //Add on click handlers to all switches
        mDialog1.setContentView(R.layout.home_page_notification_setting)
        val close = mDialog1.findViewById<TextView>(R.id.closeButtonNotificationSetting)
        val switch1 = mDialog1.findViewById<Switch>(R.id.switch1)
        val switch2 = mDialog1.findViewById<Switch>(R.id.switch2)
        val switch3 = mDialog1.findViewById<Switch>(R.id.switch3)
        val switch4 = mDialog1.findViewById<Switch>(R.id.switch4)
        val switch5 = mDialog1.findViewById<Switch>(R.id.switch5)
        val switch6 = mDialog1.findViewById<Switch>(R.id.switch6)
        close.setOnClickListener {
            mDialog1.dismiss()
        }
        switch1.setOnCheckedChangeListener { compoundButton, state ->
            Toast.makeText(
                context,
                "Notifications for Indian cotton prices: $state",
                Toast.LENGTH_SHORT
            ).show()
        }
        switch2.setOnCheckedChangeListener { compoundButton, state ->
            Toast.makeText(
                context,
                "Notifications for Indian domestic prices: $state",
                Toast.LENGTH_SHORT
            ).show()
        }
        switch3.setOnCheckedChangeListener { compoundButton, state ->
            Toast.makeText(
                context,
                "Notifications for Indian export yarn prices: $state",
                Toast.LENGTH_SHORT
            ).show()
        }
        switch4.setOnCheckedChangeListener { compoundButton, state ->
            Toast.makeText(
                context,
                "Notifications for Bangladeshi yarn prices: $state",
                Toast.LENGTH_SHORT
            ).show()
        }
        switch5.setOnCheckedChangeListener { compoundButton, state ->
            Toast.makeText(context, "Notifications with sound: $state", Toast.LENGTH_SHORT).show()
        }
        switch6.setOnCheckedChangeListener { compoundButton, state ->
            Toast.makeText(context, "Notifications with vibration: $state", Toast.LENGTH_SHORT)
                .show()
        }

        mDialog1.show()
    }    // code for notification Settings option in menu


    private fun modifyProfile() {//store values in temp variable on click and validate each value and navigate to next page
        mDialog1.setContentView(R.layout.home_page_modify_profile_1)
        var tempCountry: String
        var tempMobile: String
        var tempName: String
        var tempEmail: String
        var tempCity: String
        var count = 0

        val editTextCountry = mDialog1.findViewById<TextInputLayout>(R.id.editText_country)
        val editTextMobile = mDialog1.findViewById<TextInputLayout>(R.id.editText_mobile)
        val editTextName = mDialog1.findViewById<TextInputLayout>(R.id.editText_name)
        val editTextEmail = mDialog1.findViewById<TextInputLayout>(R.id.editText_Email)
        val editTextCity = mDialog1.findViewById<TextInputLayout>(R.id.editText_city)
        val buttonNext = mDialog1.findViewById<Button>(R.id.button_next)

        //code for next button click:-
        buttonNext.setOnClickListener {

            tempCountry = editTextCountry.editText?.text.toString()
            tempMobile = editTextMobile.editText?.text.toString()
            tempName = editTextName.editText?.text.toString()
            tempEmail = editTextEmail.editText?.text.toString()
            tempCity = editTextCity.editText?.text.toString()

            if (tempCountry == "") {
                editTextCountry.error = "Field can't be empty"
            } else {
                editTextCountry.error = null
                count++
            }
            if (tempMobile == "") {
                editTextMobile.error = "Field can't be empty"
            } else {
                editTextMobile.error = null
                count++
            }
            if (tempName == "") {
                editTextName.error = "Field can't be empty"
            } else {
                editTextName.error = null
                count++
            }
            if (tempEmail == "") {
                editTextEmail.error = "Field can't be empty"
            } else {
                editTextEmail.error = null
                count++
            }
            if (tempCity == "") {
                editTextCity.error = "Field can't be empty"
            } else {
                editTextCity.error = null
                count++
            }

            if (count == 5) {    //all necessary fields filled
                mDialog1.hide()
                mDialog2.setContentView(R.layout.home_page_modify_profile_2)
                mDialog2.setOnKeyListener { dialogInterface, keyCode, keyEvent ->
                    //for back Key
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        mDialog1.show()
                        mDialog2.dismiss()
                    }
                    true
                }


                //code for mDialog2 button click:-
                val editTextCompanyName =
                    mDialog2.findViewById<TextInputLayout>(R.id.editText_company_name)
                val editTextGSTNumber =
                    mDialog2.findViewById<TextInputLayout>(R.id.editText_GST_number)
                val editTextCompanyAdderss =
                    mDialog2.findViewById<TextInputLayout>(R.id.editText_company_address)
                val editTextCompanyState =
                    mDialog2.findViewById<TextInputLayout>(R.id.editText_company_state)
                val editTextPincode = mDialog2.findViewById<TextInputLayout>(R.id.editText_pin_code)
                val buttonModify = mDialog2.findViewById<Button>(R.id.button_modify)
                var count1 = 0

                buttonModify.setOnClickListener {

                    if (editTextCompanyName.editText?.text.toString().trim() == "") {
                        editTextCompanyName.error = "Field can't be empty"
                    } else {
                        editTextCompanyName.error = null
                        count1++
                    }

                    if (editTextCompanyAdderss.editText?.text.toString().trim() == "") {
                        editTextCompanyAdderss.error = "Field can't be empty"
                    } else {
                        editTextCompanyAdderss.error = null
                        count1++
                    }

                    if (editTextCompanyState.editText?.text.toString().trim() == "") {
                        editTextCompanyState.error = "Field can't be empty"
                    } else {
                        editTextCompanyState.error = null
                        count1++
                    }

                    if (editTextPincode.editText?.text.toString().trim() == "") {
                        editTextPincode.error = "Field can't be empty"
                    } else {
                        editTextPincode.error = null
                        count1++
                    }

                    if (count1 == 4) {     //all necessary fields filled
                        Toast.makeText(context, "Saved Successfully", Toast.LENGTH_SHORT).show()
                        mDialog1.dismiss()
                        mDialog2.dismiss()
                    } else {
                        count1 = 0
                    }
                }

                mDialog2.show()
            } else {
                count = 0
            }

        }




        mDialog1.show()
    }          //code for modify Profile option in menu


    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }


}