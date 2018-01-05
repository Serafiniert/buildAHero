package de.build_a_hero.app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import de.build_a_hero.app.R

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        configureSettingsButton()
    }

    private fun configureSettingsButton() {

        val settingsButton = findViewById<Button>(R.id.settingsButton)
        settingsButton.setOnClickListener { startActivity(Intent(this@MenuActivity, SettingsActivity::class.java)) }
    }
}
