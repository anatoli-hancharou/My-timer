package ppo.timer.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
import ppo.timer.R
import ppo.timer.databinding.ActivityMainBinding
import ppo.timer.viewModels.EditTimerViewModel

class MainActivity : LocaleAwareCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MyTimer_NoActionBar)
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        setContentView(binding.root)
        setTitle(R.string.app_name)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
        finish()
        return super.onOptionsItemSelected(item)
    }
}