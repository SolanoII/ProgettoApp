package it.uninsubria.progetto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_visualize.*


class visualize : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualize)
        val data = intent.getStringExtra("main_activity_data")
        Description.text = "$data"


    }

    fun closeActivity(v: View) {

        finish()
    }
}