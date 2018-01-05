package de.build_a_hero.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class CharEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_char_edit)

        configureCancelButton()
    }

    private fun configureCancelButton() {

        val cancelButton = findViewById<Button>(R.id.cancelCreation)
        cancelButton.setOnClickListener { finish() }
    }
}
