package com.cagudeloa.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.cagudeloa.memorygame.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var secondsSelected: Int = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,  R.layout.activity_main)

        spinnerFunction()
    }

    private fun spinnerFunction() {
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(adapterView: AdapterView<*>?) {}

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                secondsSelected = adapterView!!.getItemAtPosition(position).toString().toInt()
                Toast.makeText(this@MainActivity,   // Like this bc we're inside an anonymous function
                    "You selected $secondsSelected seconds",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
}