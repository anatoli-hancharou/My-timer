package ppo.timer.ui.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
import com.zeugmasolutions.localehelper.Locales
import ppo.timer.R
import ppo.timer.utility.TimerApp

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}
class SettingsActivity : LocaleAwareCompatActivity() {
    private lateinit var settingsFragment: SettingsFragment
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        sharedPreferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        settingsFragment = SettingsFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.prefs_content, settingsFragment)
            .commit()
    }

    override fun onResume() {
        val theme = settingsFragment.findPreference<Preference>("dark_theme")!!
        theme.onPreferenceChangeListener = Preference.OnPreferenceChangeListener {
                _, newValue ->
            sharedPreferences.edit().putBoolean("dark_theme", newValue as Boolean).apply()
            TimerApp.updateTheme(newValue)
            true
        }

        val fsPreference = settingsFragment.findPreference<ListPreference>("text_size")!!
        fsPreference.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                var sizeCoefficient = 0f
                when (newValue as String) {
                    "small" -> sizeCoefficient = 0.85f
                    "medium" -> sizeCoefficient = 1.0f
                    "large" -> sizeCoefficient = 1.15f
                }
                resources.configuration.fontScale = sizeCoefficient
                resources.displayMetrics.scaledDensity =
                    resources.configuration.fontScale * resources.displayMetrics.density
                finish()
                //Restart settings screen
                startActivity(Intent(this, this::class.java))
                true
            }

        val locale = settingsFragment.findPreference<Preference>("lang")!!
        locale.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->

            if(newValue == "ru")
                updateLocale(Locales.Russian)
            else
                updateLocale(Locales.English)
            true
        }

        super.onResume()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}