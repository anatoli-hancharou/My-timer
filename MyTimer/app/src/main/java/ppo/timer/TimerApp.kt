package ppo.timer.utility

import androidx.appcompat.app.AppCompatDelegate
import com.zeugmasolutions.localehelper.LocaleAwareApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import ppo.timer.db.TimerDatabase
import ppo.timer.db.TimerRepository
import java.util.*

class TimerApp : LocaleAwareApplication() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { TimerDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { TimerRepository(database.timerDao()) }

    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = getSharedPreferences("Preferences", MODE_PRIVATE);

        if (sharedPreferences.getBoolean("first", true))
            Locale.setDefault(Locale("eng"))
        sharedPreferences.edit().putBoolean("first", false).apply()

        val darkTheme: Boolean = sharedPreferences.getBoolean("dark_theme", false)
        updateTheme(darkTheme)
    }

    companion object {
        fun updateTheme(darkTheme: Boolean) {
            if (darkTheme) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
