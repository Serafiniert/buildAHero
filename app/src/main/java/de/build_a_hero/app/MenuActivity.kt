package de.build_a_hero.app

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        configureSettingsButton()
        configureAddButton()
    }

    private fun configureSettingsButton() {

        val settingsButton = findViewById<Button>(R.id.settingsButton)
        settingsButton.setOnClickListener { startActivity(Intent(this@MenuActivity, SettingsActivity::class.java)) }
    }


    private fun configureAddButton() {

        val addButton = findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener { startActivity(Intent(this@MenuActivity, CharCreationActivity::class.java)) }
    }
}
