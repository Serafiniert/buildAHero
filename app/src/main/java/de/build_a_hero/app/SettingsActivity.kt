package de.build_a_hero.app

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.ninad.buildahero.ContactFragment
import com.example.ninad.buildahero.CreditFragment


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    fun changeFragment(view: View) {
        var fragment: Fragment

        if (view === findViewById<View>(R.id.contactButton)) {
            fragment = ContactFragment()
            val fm = supportFragmentManager
            val ft = fm.beginTransaction()
            ft.replace(R.id.settingFragment, fragment)
            ft.commit()
        }
        if (view === findViewById<View>(R.id.creditButton)) {
            fragment = CreditFragment()
            val fm = supportFragmentManager
            val ft = fm.beginTransaction()
            ft.replace(R.id.settingFragment, fragment)
            ft.commit()
        }
    }
}
